/**
 * Simple program to open communications ports and connect to Agilent Monitor
 * Agilent Communication Interface - TO BE IMPLEMENTED
 * @version 1.2 - 30 Set 2003
 * @author Francisco Cardoso (fmcc@student.dei.uc.pt)
 * @author Ricardo Sal (ricsal@student.dei.uc.pt)
 */

package src;

public class CMSInterface {

    public static boolean connect(ComInterface port) {
    	
    	byte [] connMsg= {0x1b, 0x0a, 0x00, 0x61, (byte) 0x80, 0x0a, 0x00, 0x01, 0x00,0x00,0x00};    	
    	
    	byte [] ackMsg={0x1b, 0x0a, 0x00, 0x0a, 0x00, 0x61, (byte) 0x80, 0x02,0x00, (byte)0xff,(byte)0xff, (byte)0xff, (byte)0xff,0x00,0x01};
    	port.writeBytes(connMsg);
    	
    	byte[] ack = port.readBytes();
    	boolean result = true;
    	for (int i=13;i<15;i++)
    		if ((ack[i]==ackMsg[i]))
    			result=false;
    	return result;
    }

    public static void disconnect() {

    }

    public static void getParList() {

    }

    public static void singleTuneRequest(int id)
    {

    }

}