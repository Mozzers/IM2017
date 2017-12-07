import java.math.BigInteger;

import javax.xml.bind.DatatypeConverter;

/**
 * Simple program to open communications ports and connect to Agilent Monitor
 * Agilent Communication Interface - TO BE IMPLEMENTED
 * @version 1.2 - 30 Set 2003
 * @author Francisco Cardoso (fmcc@student.dei.uc.pt)
 * @author Ricardo Sal (ricsal@student.dei.uc.pt)
 */


public class CMSInterface {

    public static boolean connect(ComInterface port) {
    	String stringConnReq = "1B0A0061800A0001000000";
    	byte[] connReq = toByteArray(stringConnReq);
    	port.writeBytes(connReq);
    	try {
			Thread.sleep(300);
		} catch (Exception e) {
		}
    	byte[] rsp = port.readBytes();
    	String stringConnRsp = "1B0E000A0061800200\\w{8}0000";
    	return toHexString(rsp).matches(stringConnRsp);
    }

    public static void disconnect(ComInterface port) {
    	String stringDisconnReq = "1B0A0061800A0007000000";
    	byte[] disconnReq= toByteArray(stringDisconnReq);
    	port.writeBytes(disconnReq);
    }
    
    public static void getParList(ComInterface port) {
    	String stringParListReq = "1B0A0061800A000B000000";
    	byte[] parListReq = toByteArray(stringParListReq);
    	port.writeBytes(parListReq);
    	try {
			Thread.sleep(300);
		} catch (Exception e) {
		}
    	byte[] rsp = port.readBytes();
    	String stringParListRsp = "1B\\w{2}010A0061800C00\\w*";
    	if (toHexString(rsp).matches(stringParListRsp)) {
    		String stringRsp = toHexString(rsp);
    		stringRsp = removeHeader(stringRsp);
    		int total = Integer.decode("0x" + stringRsp.substring(0, 2));
    		int actual = Integer.decode("0x" + stringRsp.substring(2, 4));
    		stringRsp = stringRsp.substring(4);
    		int tmpTotal = total;
    		for (int i = 1; i < tmpTotal; i++) {
    			try {
    				Thread.sleep(300);
    			} catch (Exception e) {
    			}
    	    	byte[] tmpRsp = port.readBytes();
    	    	String tmpStringRsp = toHexString(tmpRsp);
    	    	tmpStringRsp = removeHeader(tmpStringRsp);
    	    	total = Integer.decode("0x" + stringRsp.substring(0, 2));
        		actual = Integer.decode("0x" + stringRsp.substring(2, 4));
    	    	tmpStringRsp = tmpStringRsp.substring(4);
        		stringRsp = stringRsp + tmpStringRsp;
    		}
    		for (int i = 0; i < stringRsp.length(); i += 52) {
    			System.out.println(parseASCII(stringRsp.substring(i + 20, i + 52)));
    		}
    	}
    }

    public static void singleTuneRequest(int id) {
    	// TODO
    }
    
    public static String parseASCII(String hex) {
    	StringBuilder output = new StringBuilder();
    	for (int i = 0; i < hex.length(); i += 2) {
    		String tmpString = hex.substring(i, i+2);
    		output.append((char)Integer.parseInt(tmpString, 16));
    	}
    	return output.toString();
    }
    
    public static String removeHeader(String message) {
    	message = message.substring(2); // remove synchronisation
    	message = message.replaceAll("1BFF", "1B");
    	message = message.substring(16);
    	return message;
    }
    
    public static String toHexString(byte[] array) {
        return DatatypeConverter.printHexBinary(array);
    }

    public static byte[] toByteArray(String s) {
        return DatatypeConverter.parseHexBinary(s);
    }

}