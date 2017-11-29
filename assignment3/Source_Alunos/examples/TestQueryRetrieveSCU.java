import java.util.*;
import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.metadata.*;
import javax.imageio.stream.*;

import java.net.*;

import fr.apteryx.imageio.dicom.*;;

class TestQueryRetrieveSCU {

  public static void main(String[] s) {
    try {
      
      ImageIO.scanForPlugins();
      WarningListener listener = new WarningListener();
      Iterator readers = ImageIO.getImageReadersByFormatName("dicom");
      DicomReader reader = (DicomReader)readers.next();
      reader.addIIOReadWarningListener(listener);
      Iterator writers = ImageIO.getImageWritersByFormatName("dicom");
      DicomWriter writer = (DicomWriter)writers.next();
      writer.addIIOWriteWarningListener(listener);
      
      PeerAE peer = new PeerAE(InetAddress.getByName("127.0.0.1"), "TEST");
      QueryRetrieveSCU scu = new QueryRetrieveSCU(peer, reader);

      BufferedReader linereader = new BufferedReader
	    (new InputStreamReader(System.in));

      try {
	// QUERYING PATIENT LEVEL
	Identifier query = new Identifier(Identifier.MODEL_QR_PATIENT_ROOT,
	    Identifier.LEVEL_PATIENT);
	query.setAttribute(Tag.PatientsName, new PersonName("*"));
	query.requestAttribute(Tag.PatientID);
	Iterator list = scu.find(query);
	
	if (!list.hasNext()) {
	  System.out.println("No patient");
	  return;
	}
	System.out.println("NAME\tPATIENT ID");
	System.out.println("----\t----------");
	while (list.hasNext()) {
      	  Identifier id = (Identifier) list.next();
	  System.out.println(id.getAttribute(Tag.PatientsName)+"\t"+id.getAttribute(Tag.PatientID));
	}
	System.out.print("Patient ID: ");
	String key1 = linereader.readLine();
	System.out.println();
	
	// QUERYING STUDY LEVEL
        query = new Identifier(Identifier.MODEL_QR_PATIENT_ROOT,
	  Identifier.LEVEL_STUDY);
	query.setAttribute(Tag.PatientID, key1);
	query.requestAttribute(Tag.StudyInstanceUID);
	query.requestAttribute(Tag.StudyDate);
	list = scu.find(query);
	
	if (!list.hasNext()) {
	  System.out.println("No study");
	  return;
	}
	System.out.println("DATE        \tSTUDY INSTANCE UID");
	System.out.println("------------\t------------------");
	while (list.hasNext()) {
    	  Identifier id = (Identifier) list.next();
	  System.out.println(id.getAttribute(Tag.StudyDate)+"\t"+
	      id.getAttribute(Tag.StudyInstanceUID));
	}
	System.out.print("Study instance UID: ");
	String key2 = linereader.readLine();
	System.out.println();

	// QUERYING SERIES LEVEL
        query = new Identifier(Identifier.MODEL_QR_PATIENT_ROOT,
	  Identifier.LEVEL_SERIES);
	query.setAttribute(Tag.PatientID, key1);
	query.setAttribute(Tag.StudyInstanceUID, key2);
	query.requestAttribute(Tag.SeriesInstanceUID);
	query.requestAttribute(Tag.Modality);
	list = scu.find(query);
	
	if (!list.hasNext()) {
	  System.out.println("No series");
	  return;
	}
	System.out.println("MOD.\tSERIES UID");
	System.out.println("----\t----------");
	while (list.hasNext()) {
  	  Identifier id = (Identifier) list.next();
	  System.out.println(id.getAttribute(Tag.Modality)+"\t"+
	      id.getAttribute(Tag.SeriesInstanceUID));
	}
	System.out.print("Series instance UID: ");
	String key3 = linereader.readLine();
	System.out.println();
	
	// QUERYING IMAGE LEVEL
        query = new Identifier(Identifier.MODEL_QR_PATIENT_ROOT,
	  Identifier.LEVEL_IMAGE);
	query.setAttribute(Tag.PatientID, key1);
	query.setAttribute(Tag.StudyInstanceUID, key2);
	query.setAttribute(Tag.SeriesInstanceUID, key3);
	query.requestAttribute(Tag.SOPInstanceUID);
	list = scu.find(query);
	
	if (!list.hasNext()) {
	  System.out.println("No image");
	  return;
	}
	System.out.println("SOP INSTANCE UID");
	System.out.println("----------------");
	while (list.hasNext()) {
	  Identifier id = (Identifier) list.next();
	  System.out.println(id.getAttribute(Tag.SOPInstanceUID));
	}
	System.out.print("SOP instance UID: ");
	String key4 = linereader.readLine();
	System.out.println();
	
	// RETRIEVING
	query.setAttribute(Tag.SOPInstanceUID, key4);
	
	if (scu.getSupportedBySCP(Identifier.MODEL_QR_PATIENT_ROOT)) {
	  System.out.println("Getting...");
	  list = scu.get(query);
	  while (list.hasNext()) {
	    ReceivedObject ro = (ReceivedObject) list.next();
	    reader.setInput(ro);
	    DicomMetadata dmd = reader.getDicomMetadata();
	    dmd.removeUnwritableElements();
	    String filename = dmd.getAttributeString(Tag.SOPInstanceUID);
	    FileImageOutputStream fios = new FileImageOutputStream
	      (new File(filename+".dcm"));
	    writer.setOutput(fios);
	    writer.prepareWriteSequence(dmd);
	    int nImages = reader.getNumImages(true);
	    for (int i=0; i<nImages; i++) 
	      writer.writeToSequence(new IIOImage(reader.read(i),
		    null, null), null);
	    writer.endWriteSequence();
	    System.out.println("Saved as "+filename+".dcm");
	  }
	} else {
	  System.out.println("GET not supported by SCP");
	}
      } finally {
	scu.close();
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
