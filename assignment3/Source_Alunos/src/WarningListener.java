import javax.imageio.*;
import javax.imageio.event.*;

class WarningListener implements IIOReadWarningListener,
  IIOWriteWarningListener {
    public void warningOccurred(ImageReader source,
	String warning) {
      System.err.println("READ WARNING: "+warning);
    }
    public void warningOccurred(ImageWriter source,
       	int idx,
	String warning) {
      System.err.println("WRITE WARNING (image "+idx+"): "+warning);
    }
}
