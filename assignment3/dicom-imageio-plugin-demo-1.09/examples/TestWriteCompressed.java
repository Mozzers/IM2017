import java.io.*;
import java.awt.image.*;
import javax.imageio.*;
import javax.imageio.metadata.*;
import javax.imageio.stream.*;
import java.util.*;
import fr.apteryx.imageio.dicom.*;

class TestWriteCompressed {
  
  static String[][] ts = new String [][] {
    { UID.JPEGBaseline_1TS, "jpg1" },
    { UID.JPEGExtended_2_4TS, "jpg24" },
    { UID.JPEGLossless_14SV1TS, "jpl" },
    { UID.JPEGLS_LosslessTS, "jpls" },
    { UID.JPEG2000TS, "j2k" },
    { UID.JPEG2000_LosslessTS, "j2kl" }
  };

  
  public static void main(String[] s) {
    try {
      ImageIO.scanForPlugins();
      if (s.length != 2) {
	System.err.println("Please supply an input file a file prefix for output file names");
	System.exit(1);
      }
      
      FileImageInputStream fiis = new FileImageInputStream(new File(s[0]));
      Iterator readers = ImageIO.getImageReaders(fiis);
      ImageReader reader = (ImageReader)readers.next();
      reader.setInput(fiis);
      IIOMetadata md =  reader.getStreamMetadata();

      
      Iterator writers = ImageIO.getImageWritersByFormatName("dicom");
      DicomWriter writer = (DicomWriter)writers.next();
      
      DicomMetadata dmd = (DicomMetadata) 
	writer.convertStreamMetadata(md, null);
      if (dmd == null) dmd = new DicomMetadata();

      for (int t=0; t<ts.length; t++) {
	System.err.println("WRITING "+ts[t][1]);
	File f = new File(s[1]+'.'+ts[t][1]);
	f.delete();
	writer.setTransferSyntax(ts[t][0]);
	FileImageOutputStream fios = new FileImageOutputStream(f);
	writer.setOutput(fios);
	try {
	  writer.prepareWriteSequence(dmd);
	  for (int i=0; i<reader.getNumImages(true); i++) {
	    final BufferedImage bi = reader.read(i);
	    writer.writeToSequence(new IIOImage(bi, null, null), null);
	  }
	  writer.endWriteSequence();
	  fios.close();
	} catch (DicomException e) {
	  System.err.println(e);
	  f.delete();
	}
      }
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
