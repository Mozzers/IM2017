import java.io.*;
import javax.imageio.*;
import java.util.*;

import fr.apteryx.imageio.dicom.*;

class TestFileSet {

 public static void main(String[] s) {
   try {
      if (s.length != 1) {
        System.err.println("Please supply an input DICOMDIR file");
        System.exit(1);
      }

     ImageIO.scanForPlugins();
     
     Iterator readers = ImageIO.getImageReadersByFormatName("dicom");
     DicomReader reader = (DicomReader)readers.next();
     reader.addIIOReadWarningListener(new WarningListener());
     
     FileSet fs = new FileSet(new File(s[0]), reader);
     
     System.out.print(fs);

   } catch (Exception e) {
     e.printStackTrace();
   }
 }
}
