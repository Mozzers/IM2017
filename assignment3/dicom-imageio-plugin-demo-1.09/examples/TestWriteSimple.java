import java.io.*;
import java.awt.image.*;
import javax.imageio.*;

class TestWriteSimple {

   public static void main(String[] s) {
     try {
       ImageIO.scanForPlugins();
       if (s.length != 2) {
  	 System.err.println("Please supply an input file and an output file");
   	 System.exit(1);
       }

       File f = new File(s[0]);
       BufferedImage bi = ImageIO.read(f);

       File f2 = new File(s[1]);
       ImageIO.write(bi, "dicom", f2);

     } catch (Exception e) {
       e.printStackTrace();
     }
   }
}
