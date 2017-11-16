import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.metadata.*;
import javax.imageio.stream.*;
import java.util.*;

class TestWrite {
   public static void main(String[] s) {
     try {
       ImageIO.scanForPlugins();
       if (s.length != 2) {
  	 System.err.println("Please supply an input file and an output file");
   	 System.exit(1);
       }

       WarningListener listener = new WarningListener();

       File f = new File(s[0]);
       FileImageInputStream fiis = new FileImageInputStream(f);

       Iterator readers = ImageIO.getImageReaders(fiis);
       ImageReader reader = (ImageReader)readers.next();
       reader.addIIOReadWarningListener(listener);
       reader.setInput(fiis);
       IIOMetadata md =  reader.getStreamMetadata();

       File f2 = new File(s[1]);
       f2.delete();

       Iterator writers = ImageIO.getImageWritersByFormatName("dicom");
       ImageWriter writer = (ImageWriter)writers.next();
       writer.addIIOWriteWarningListener(listener);

       writer.setOutput(new FileImageOutputStream(f2));
       IIOMetadata dmd = writer.convertStreamMetadata(md, null);
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
