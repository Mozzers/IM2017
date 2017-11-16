import java.util.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.metadata.*;
import javax.imageio.stream.*;

import java.lang.reflect.Array;

import java.net.*;

import fr.apteryx.imageio.dicom.*;;

/**
 * This class acts as a query/retrieve SCP, serving a single object, 
 * contained in the file supplied in argument.
 * It accepts queries following the Patient-root or study-root model,
 * and retrieval using MOVE or GET.
 * Only single-value matching is supported (no wildcard, no range).
 */
class TestQueryRetrieveSCP implements QueryRetrieveSCPListener {

  static final private HashSet SUPPORTED_SYNTAXES = new HashSet();
  static {
    SUPPORTED_SYNTAXES.add(UID.PatientRootQueryRetrieveInformationModelFind);
    SUPPORTED_SYNTAXES.add(UID.PatientRootQueryRetrieveInformationModelMove);
    SUPPORTED_SYNTAXES.add(UID.PatientRootQueryRetrieveInformationModelGet);

    SUPPORTED_SYNTAXES.add(UID.StudyRootQueryRetrieveInformationModelFind);
    SUPPORTED_SYNTAXES.add(UID.StudyRootQueryRetrieveInformationModelMove);
    SUPPORTED_SYNTAXES.add(UID.StudyRootQueryRetrieveInformationModelGet);
  }

  static final private HashSet SUPPORTED_ATTRIBUTES = new HashSet();
  static {
    SUPPORTED_ATTRIBUTES.add(new Integer(Tag.PatientsName));
    SUPPORTED_ATTRIBUTES.add(new Integer(Tag.PatientID));
    SUPPORTED_ATTRIBUTES.add(new Integer(Tag.PatientsSex));
    SUPPORTED_ATTRIBUTES.add(new Integer(Tag.StudyDate));
    SUPPORTED_ATTRIBUTES.add(new Integer(Tag.StudyTime));
    SUPPORTED_ATTRIBUTES.add(new Integer(Tag.AccessionNumber));
    SUPPORTED_ATTRIBUTES.add(new Integer(Tag.StudyID));
    SUPPORTED_ATTRIBUTES.add(new Integer(Tag.StudyInstanceUID));
    SUPPORTED_ATTRIBUTES.add(new Integer(Tag.ReferringPhysiciansName));
    SUPPORTED_ATTRIBUTES.add(new Integer(Tag.PatientsAge));
    SUPPORTED_ATTRIBUTES.add(new Integer(Tag.PatientsSize));
    SUPPORTED_ATTRIBUTES.add(new Integer(Tag.PatientsWeight));
    SUPPORTED_ATTRIBUTES.add(new Integer(Tag.Modality));
    SUPPORTED_ATTRIBUTES.add(new Integer(Tag.SeriesNumber));
    SUPPORTED_ATTRIBUTES.add(new Integer(Tag.SeriesInstanceUID));
    SUPPORTED_ATTRIBUTES.add(new Integer(Tag.InstanceNumber));
    SUPPORTED_ATTRIBUTES.add(new Integer(Tag.SOPInstanceUID));
    SUPPORTED_ATTRIBUTES.add(new Integer(Tag.SOPClassUID));
  }

  static DicomReader reader;
  static DicomMetadata dmd;
  static DicomWriter writer;

  public static void main(String[] s) {
    try {
      ImageIO.scanForPlugins();

      if (s.length != 1) {
	System.err.println("Please supply a dicom file to serve");
	System.exit(1);
      }

      WarningListener listener = new WarningListener();

      Iterator readers = ImageIO.getImageReadersByFormatName("dicom");
      reader = (DicomReader)readers.next();

      reader.setInput(new FileImageInputStream(new File(s[0])));
      dmd = reader.getDicomMetadata();
      dmd.removeUnwritableElements();

      Iterator writers = ImageIO.getImageWritersByFormatName("dicom");
      writer = (DicomWriter)writers.next();

      QueryRetrieveSCP scp = new QueryRetrieveSCP(new TestQueryRetrieveSCP());
      AERegistry reg = scp.server.getAERegistry();
      reg.registerAE("REMOTE", InetAddress.getByName("10.0.0.2"), 104);

      // Ensure that the JVM does not end when this thread ends.
      Plugin.setServersAreDaemons(false);

      scp.start();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  public boolean supportsSyntax(String syn) {
    return SUPPORTED_SYNTAXES.contains(syn);
  }

  /**
   * returns true iif the value of an attribute received in a query matches
   * value "has".
   * If the received value is multiple, returns true if one of the value
   * matches. 
   * If the received value is empty, returns true (universal matching).
   */
  private boolean attribute_matches(Object received, Object has) {
    if (received == null || "".equals(received) 
	|| "*".equals(received.toString())) 
      return true;
    if (received.getClass().isArray()) {
      final int len = Array.getLength(received);
      for (int i=0; i<len; i++)
	if (attribute_matches(Array.get(received, i), has)) return true;
      return false;
    }
    return received.equals(has);
  }


  /**
   * If the supplied query identifier matches dmd, returns the response
   * identifier, else returns null.
   */
  private Identifier matches(Identifier id) {
    boolean match = true;
    DataSet ds = id.getDataSet();
    Identifier response = new Identifier(id.getModel(), id.getLevel());
    for (Iterator it = ds.values().iterator(); it.hasNext(); ) {
      DataElement de = (DataElement) it.next();
      if (!SUPPORTED_ATTRIBUTES.contains(new Integer(de.tag))) continue;
      if (attribute_matches(de.value, dmd.getAttribute(de.tag))) {
	response.setAttribute(de.tag, dmd.getAttribute(de.tag));
      } else {
	match = false;
	break;
      }
    }
    return match ? response : null;
  }

  /** Called by the plugin when a query is received. */
  public void queryReceived(QueryRetrieveSCP.Operation op) {
    Identifier id = op.identifier;
    try {
      if ((id = matches(id)) != null) {

	// Notify the SCU that the object may be retrieved from us
	// if we are at the last level.
	id.setAttribute(Tag.RetrieveAETitle, 
	    Identifier.LEVEL_IMAGE.equals(id.getLevel()) ?
	    Plugin.getApplicationTitle() : null);

	op.sendQueryResponsePending(id);
      }
      op.sendQueryResponseFinal();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /** Called by the plugin when the SCU sends a retrieve command. */
  public void retrieveReceived(QueryRetrieveSCP.Operation op) {
    Identifier id = op.identifier;
    boolean ok = true;
    try {
      if (matches(id) != null) {
	writer.setOutput(op);
	writer.prepareWriteSequence(dmd);
	for (int i=0; i<reader.getNumImages(true); i++) {
	  writer.writeToSequence(new IIOImage(reader.read(i, null), null, null),
	      null);
	}
	writer.endWriteSequence();
      } 
      op.sendRetrieveResponseFinal();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
