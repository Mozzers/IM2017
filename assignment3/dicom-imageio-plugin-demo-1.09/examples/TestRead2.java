import javax.swing.*;
import java.io.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;
import javax.imageio.stream.*;
import java.util.Iterator;

import fr.apteryx.imageio.dicom.*;

/**
 * This class displays the first frame of a DICOM file.
 * 
 * Modality, Value of Interest and Presentation LUTs
 * are applied, leading to an image as close as possible
 * as what would have been shown on a DICOM workstation.
 */
class TestRead2 {
  public static void main(String[] s) {
    try {
      if (s.length != 1) {
        System.err.println("Please supply an input file");
        System.exit(1);
      }

      ImageIO.scanForPlugins();

      File f = new File(s[0]);
      Iterator readers = ImageIO.getImageReadersByFormatName("dicom");
      DicomReader reader = (DicomReader)readers.next();
      reader.addIIOReadWarningListener(new WarningListener());
      reader.setInput(new FileImageInputStream(f));

      DicomMetadata dmd = reader.getDicomMetadata();

      if (reader.getNumImages(true) < 1) {
	System.err.println("No pixel data");
	System.exit(1);
      }

      BufferedImage bi_stored = reader.read(0);
      if (bi_stored == null) {
	System.err.println("read error");
	System.exit(1);
      }

      final BufferedImage bi = dmd.applyGrayscaleTransformations(bi_stored, 0);
      
      JFrame jf = new JFrame();
      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      final Rectangle bounds = new Rectangle(0, 0, bi.getWidth(), bi.getHeight());
      JPanel panel = new JPanel() {
	public void paintComponent(Graphics g) {
	  Rectangle r = g.getClipBounds();
	  ((Graphics2D)g).fill(r);
	  if (bounds.intersects(r))
	    try {
	      g.drawImage(bi, 0, 0, null);
	    } catch (Exception e) {
	      e.printStackTrace();
	    }
	}
      };
      jf.getContentPane().add(panel);
      panel.setPreferredSize(new Dimension(bi.getWidth(), bi.getHeight()));
      jf.pack();
      jf.setVisible(true);
      
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
