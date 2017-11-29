import javax.imageio.*;
import javax.imageio.stream.*;
import java.io.*;
import java.util.*;

class Dump {
    
  public static void main(String[] s) {
    
    if (s.length != 1) {
      System.err.println("Usage:\njava Dump file");
      System.exit(1);
    }
    try {
      Iterator readers = ImageIO.getImageReadersByFormatName("dicom");
      ImageReader reader = (ImageReader)readers.next();
   
      FileImageInputStream iis = new FileImageInputStream(new File(s[0]));
      reader.addIIOReadWarningListener(new WarningListener());
      reader.setInput(iis);
      System.out.println(reader.getStreamMetadata());
      iis.close();
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
