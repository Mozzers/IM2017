import java.io.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import javax.imageio.metadata.*;
import javax.imageio.stream.*;
import java.util.Iterator;

import java.net.*;

import fr.apteryx.imageio.dicom.*;

class TestStoreSCU {
  public static void main(String[] s) {
    try {
      String filename = null;
      boolean secure = false;
      for (int i=0; i<s.length; i++) {
	if ("-secure".equals(s[i])) secure = true;
	else filename = s[i];
      }

      if (filename == null) {
	System.err.println("Please supply a file to send");
	System.exit(1);
      }

      ImageIO.scanForPlugins();

      WarningListener listener = new WarningListener();

      File f = new File(filename);

      Iterator readers = ImageIO.getImageReadersByFormatName("dicom");
      ImageReader reader = (ImageReader)readers.next();
      reader.addIIOReadWarningListener(listener);
      reader.setInput(new FileImageInputStream(f));

      Iterator writers = ImageIO.getImageWritersByFormatName("dicom");
      ImageWriter writer = (ImageWriter)writers.next();
      writer.addIIOWriteWarningListener(listener);
      Plugin.setApplicationTitle("PLUGIN_TEST");

      // Change this to the target IP address, port and AE title
      InetAddress addr = InetAddress.getByName("localhost");
      writer.setOutput(secure ? 
	  new PeerAE(addr, "TEST", 
	    SecureTransport.JAVA_DEFAULT_AUTH_ACCEPTOR) :
	  new PeerAE(addr, "TEST"));

      DicomMetadata dmd = (DicomMetadata) reader.getStreamMetadata();

      dmd.removeUnwritableElements();

      writer.prepareWriteSequence(dmd);
      for (int i=0; i<reader.getNumImages(true); i++) 
	writer.writeToSequence(new IIOImage(reader.read(i), null, null), null);
      writer.endWriteSequence();
 
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
