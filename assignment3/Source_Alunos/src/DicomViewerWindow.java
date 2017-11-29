import java.util.Vector;

import javax.swing.event.ListSelectionListener;

import fr.apteryx.imageio.dicom.Plugin;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.imageio.ImageIO;
import java.util.*;
import java.io.*;

public class DicomViewerWindow implements Runnable {
	private JFrame jf;
	private Rectangle bounds;
	private myJPanel panel;
    private JFrame parent;
    private BufferedImage []images;
    private int displayCount;
    private int displayInterval;
    private static Semaphore sem_data;
    private Thread paintThread;
    private int sizex,sizey;

	public DicomViewerWindow(JFrame parent) {
        super();
        panel = null;
        jf = null;
        this.parent = parent;
        this.images = null;
        displayCount = 0;
        displayInterval=100;
        sem_data = new Semaphore(1);
        sizex=sizey=700;
        createWindow();
        paintThread = new Thread(this);
        paintThread.start();

	}

    public void setImages(final BufferedImage []bi, int interval)
    {
        Plugin.setLicenseKey("NM73KIZUPKHLFLAQM5L0V9U");

        ImageIO.scanForPlugins();
		/*
        JFrame jf = new JFrame();
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		final Rectangle bounds = new Rectangle(0, 0, bi[0].getWidth(), bi[0].getHeight());
		JPanel panel = new JPanel() {
			public void paintComponent(Graphics g) {
				Rectangle r = g.getClipBounds();
				((Graphics2D) g).fill(r);
				if (bounds.intersects(r))
					try {
						g.drawImage(bi[0], 0, 0, null);
					} catch (Exception e) {
						e.printStackTrace();
					}
			}
		};
		jf.getContentPane().add(panel);
		panel.setPreferredSize(new Dimension(bi[0].getWidth(), bi[0].getHeight()));
		jf.pack();
		jf.setVisible(true);*/

        sem_data.espera();
        if (bi!=null && bi.length>0) images = bi;
        if (interval<=0) displayInterval=100;
        else displayInterval = interval;

        if (bi==null || bi.length == 0)
        {
            sem_data.notifica();
            return;
        }
        if (interval<=0) interval = 100;
        if (images!=null)
         {
             sizex=images[0].getWidth();
             sizey=images[0].getHeight();
         }
        jf.setPreferredSize(new Dimension(sizex, sizey));
        panel.setPreferredSize(new Dimension(sizex, sizey));

        sem_data.notifica();
        
        
        
        
  }

   public void run()
   {
       while(true)
       {
           sem_data.espera();
          if (jf!=null && panel != null && images !=null)
          {
              displayCount = (displayCount+1) % images.length;
              panel.setImage(images[displayCount]);
              System.out.println("Showing image number = "+displayCount);
          }
           sem_data.notifica();
           try{
               paintThread.sleep(displayInterval);
           }catch (Exception e)
           {

           }

        }
   }

  private void createWindow()
  {
	     jf = new JFrame();
	     jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	     panel = new myJPanel();

	     jf.getContentPane().add(panel);
         int sizex,sizey;

	     panel.setPreferredSize(new Dimension(this.sizex, this.sizey));
	     jf.pack();
	     jf.setVisible(true);
      	 }
	}

class myJPanel extends JPanel
{
    private BufferedImage image = null;

    public void setImage(BufferedImage i)
    {
        image = i;
        repaint();
    }

    public void paint(Graphics g) {
	   		  Rectangle r = g.getClipBounds();
	   		  ((Graphics2D)g).fill(r);
              Rectangle bounds;
              if (image==null)
                  bounds = new Rectangle(0, 0, 200, 200);
              else
                 bounds = new Rectangle(0, 0, image.getWidth(), image.getHeight());
	   		  if (bounds.intersects(r))
	   		    try {
	   		      g.drawImage(image, 0, 0, null);
	   		    } catch (Exception e) {
	   		      e.printStackTrace();
	   		    }
    }

}
