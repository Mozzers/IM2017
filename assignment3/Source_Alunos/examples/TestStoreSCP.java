import java.util.Iterator;
import java.io.*;
import java.awt.image.*;

import javax.imageio.*;
import javax.imageio.stream.*;
import javax.imageio.metadata.*;

import fr.apteryx.imageio.dicom.*;

class TestStoreSCP {
  public static void main(String[] s) {
    try {

      boolean secure = s.length > 0 && "-secure".equals(s[0]);

      WarningListener listener = new WarningListener();

      StorageSCP scp = secure ? new StorageSCP(SecureTransport.JAVA_DEFAULT_AUTH_ACCEPTOR) : new StorageSCP();
      
      Iterator readers = ImageIO.getImageReadersByFormatName("dicom");
      ImageReader reader = (ImageReader)readers.next();
      reader.addIIOReadWarningListener(listener);

      Iterator writers = ImageIO.getImageWritersByFormatName("dicom");
      ImageWriter writer = (ImageWriter) writers.next();
      writer.addIIOWriteWarningListener(listener);
   
      reader.setInput(scp);

      File out = new File("testscp.dcm");
      out.delete();
      writer.setOutput(new FileImageOutputStream(out));

      DicomMetadata dmd = (DicomMetadata) reader.getStreamMetadata();

      dmd.removeUnwritableElements();
      
      writer.prepareWriteSequence(dmd);
      
      for (int i=0; i<reader.getNumImages(true); i++) {
	final BufferedImage bi = reader.read(i);
	writer.writeToSequence(new IIOImage(bi, null, null), null);
      }
      
      writer.endWriteSequence();
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
