import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import javax.xml.bind.DatatypeConverter;

/**
 * Simple program to open communications ports and connect to Agilent Monitor
 * Agilent Communication Interface - TO BE IMPLEMENTED
 * 
 * @version 1.2 - 30 Set 2003
 * @author Francisco Cardoso (fmcc@student.dei.uc.pt)
 * @author Ricardo Sal (ricsal@student.dei.uc.pt)
 */

public class CMSInterface {

	public static boolean connect(ComInterface port) {
		String stringConnReq = "1B0A0061800A0001000000";
		byte[] connReq = Utility.toByteArray(stringConnReq);
		port.writeBytes(connReq);
		byte[] rsp = getRsp(port);
		String stringConnRsp = "1B0E000A0061800200\\w{8}0100";
		return toHexString(rsp).matches(stringConnRsp);
	}

	public static boolean disconnect(ComInterface port) {
		String stringDisconnReq = "1B0A0061800A0007000000";
		byte[] disconnReq = Utility.toByteArray(stringDisconnReq);
		port.writeBytes(disconnReq);
		byte[] rsp = getRsp(port);
		String stringDisconnRsp = "1B0A000A00618008000100";
		return Utility.toHexString(rsp).matches(stringDisconnRsp);
	}

	public static ArrayList<ListInfo> getParList(ComInterface port) {
		String stringParListReq = "1B0A0061800A000B000000";
		byte[] parListReq = toByteArray(stringParListReq);
		port.writeBytes(parListReq);
		String stringParListRsp = "1B\\w{4}0A0061800C00\\w*";
		int total = 0;
		int actual = -2;
		ArrayList<String> messages = new ArrayList<String>();
		while (actual != total) {
			byte[] rsp = getRsp(port);
			if (toHexString(rsp).matches(stringParListRsp)) {
				String stringRsp = toHexString(rsp);
				stringRsp = removeHeader(stringRsp);
				total = Integer.decode("0x" + stringRsp.substring(0, 2));
				actual = Integer.decode("0x" + stringRsp.substring(2, 4));
				stringRsp = stringRsp.substring(4);
				messages.add(stringRsp);
			}
		}
		ArrayList<ListInfo> decodedMsgs = new ArrayList<ListInfo>();
		for (int i = 0; i < messages.size(); i++)
			messages.set(i, littleEndianSwap(messages.get(i)));
		for (String message : messages) {
			for (int i = 0; i < message.length() - 4 * 13 + 1; i += 4 * 13) {
				MsgID msgIdString = new MsgID(message.substring(i, i + 4 * 5));
				String asciiId = parseASCII(message.substring(i + 4 * 5, i + 4 * 13));
				decodedMsgs.add(new ListInfo(msgIdString, asciiId));
			}
		}
		return decodedMsgs;
	}

	public static String singleTuneRequest(ComInterface port, String tuneID, MsgID msgID) {
		String outString= "";
		String hexId = intToHexString(Integer.parseInt(tuneID));
		String data = hexId + msgID.toHexString();
		int length = data.length() / 2 + 12;
		String hexLength = littleEndianSwap(intToHexString(length)); // length without escape sequence
		data = littleEndianSwap(data);
		data = data.replaceAll("1B", "1BFF");
		String stringSingleTuneReq = "1B" + hexLength + "61800A000F00" + data + "00000000";
		byte[] singleTuneReq = toByteArray(stringSingleTuneReq);
		port.writeBytes(singleTuneReq);
		byte[] rsp = getRsp(port);
		String stringSingleTuneRsp = "1B\\w{4}0A0061801000" + littleEndianSwap(hexId) + "\\w*";
		if (toHexString(rsp).matches(stringSingleTuneRsp)) {
			String stringRsp = toHexString(rsp);
			stringRsp = removeHeader(stringRsp);
			stringRsp = littleEndianSwap(stringRsp);
			MsgID msgId = new MsgID(stringRsp.substring(4, 4+ 20));
			outString=msgId.toString();
			byte [] outData=getRsp(port);
			outString=outString + "\n Data received:\n"+toHexString(outData);
		}
		else{
			outString= "Failed to get single tune output";
		}
		return outString;
	}
	
	private static String intToHexString(int i) {
		byte[] byteInt= ByteBuffer.allocate(4).putInt(i).array();
		byte[] byteIntShort = { byteInt[2], byteInt[3] };
		return toHexString(byteIntShort);
	}

	private static String parseASCII(String hex) {
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < hex.length() - 3; i += 4) {
			String str = hex.substring(i, i + 4);
			output.append((char) Integer.parseInt(str, 16));
		}
		return output.toString();
	}

	private static String littleEndianSwap(String hex) {
		String hexSwapped = "";
		for (int i = 0; i < hex.length() - 3; i += 4) {
			hexSwapped = hexSwapped + hex.substring(i + 2, i + 4);
			hexSwapped = hexSwapped + hex.substring(i, i + 2);
		}
		return hexSwapped;
	}

	private static String removeHeader(String message) {
		message = message.substring(2); // remove synchronisation
		message = message.replaceAll("1BFF", "1B");
		message = message.substring(16);
		return message;
	}

	public static String toHexString(byte[] array) {
		return DatatypeConverter.printHexBinary(array);
	}
	
	public static String toDecimalString(byte[] array){
		ByteBuffer wrapped = ByteBuffer.wrap(array);
		int decimal;
		if(array.length==1){
			Byte b= new Byte(array[0]);
			decimal=b.intValue()&0xFF;
			}
		else {
			decimal = (wrapped.getShort()&(-1 >>> 16));
		}
		return Integer.toString(decimal);
	}

	public static byte[] toByteArray(String s) {
		return DatatypeConverter.parseHexBinary(s);
	}

	private static int countEscape(byte[] msg) {
		int count = 0;
		for (int i = 0; i < msg.length - 1; i++)
			if (msg[i + 1] == 27 && msg[i] == -128)
				count++;
		//if (msg[msg.length - 1] == -128) // case when buffer ends with 1B -> swap FF ??
		//	count++;
		return count;
	}

	private static byte[] getRsp(ComInterface port) {
		ArrayList<byte[]> rsps = new ArrayList<byte[]>();
		int intLength = -2;
		int rspsByteLength = 0;
		while (rspsByteLength - 1 != intLength) {
			try {
				Thread.sleep(100);
				int readingCount = intLength - rspsByteLength + 1;
				if (readingCount < 0) {
					readingCount = 3; // min number of bytes
				}
				byte[] actualRsp = port.readBytes(readingCount);
				if (actualRsp.length != 0) {
					intLength += countEscape(actualRsp);
					rsps.add(actualRsp);
					rspsByteLength += actualRsp.length;
					if (actualRsp[0] == 0x1B && actualRsp[1] != 0xFF) {
						byte[] byteLength = { 0, 0, actualRsp[2], actualRsp[1] };
						intLength = ByteBuffer.wrap(byteLength).getInt();
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		byte[] retVal = new byte[rspsByteLength];
		int index = 0;
		for (byte[] rsp : rsps) {
			for (int i = 0; i < rsp.length; i++) {
				retVal[index] = rsp[i];
				index++;
			}
		}
		return retVal;
	}

}