import javax.swing.*;
import java.io.*;
import java.awt.image.*;
import java.awt.*;
import javax.imageio.*;

/**
 * This class displays the first frame of a file from any format
 * supported by ImageIO. 
 * In the case of DICOM files, stored values are displayed as
 * is, without using Value of Interest or Presentation LUTs.
 * See TestRead2.
 */
class TestRead1 {
  public static void main(String[] s) {
    try {
      if (s.length != 1) {
        System.err.println("Please supply an input file");
        System.exit(1);
      }

      ImageIO.scanForPlugins();
      final BufferedImage bi = ImageIO.read(new File(s[0]));
      if (bi == null) {
	System.err.println("read error");
	System.exit(1);
      }
      
      JFrame jf = new JFrame();
      jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

      final Rectangle bounds = new Rectangle(0, 0, bi.getWidth(), bi.getHeight());
      JPanel panel = new JPanel() {
	public void paintComponent(Graphics g) {
	  Rectangle r = g.getClipBounds();
	  ((Graphics2D)g).fill(r);
	  if (bounds.intersects(r))
	    g.drawImage(bi, 0, 0, null);
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
