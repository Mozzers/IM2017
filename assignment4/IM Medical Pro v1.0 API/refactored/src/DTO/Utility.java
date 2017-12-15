package DTO;
import java.nio.ByteBuffer;

import javax.xml.bind.DatatypeConverter;

public class Utility {


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

	public static int countEscape(byte[] msg) {
		int count = 0;
		for (int i = 0; i < msg.length - 1; i++)
			if (msg[i + 1] == 27 && msg[i] == -128)
				count++;
		//if (msg[msg.length - 1] == -128) // case when buffer ends with 1B -> swap FF ??
		//	count++;
		return count;
	}
	
	public static String intToHexString(int i) {
		byte[] byteInt= ByteBuffer.allocate(4).putInt(i).array();
		byte[] byteIntShort = { byteInt[2], byteInt[3] };
		return toHexString(byteIntShort);
	}

	public static String parseASCII(String hex) {
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < hex.length() - 3; i += 4) {
			String str = hex.substring(i, i + 4);
			output.append((char) Integer.parseInt(str, 16));
		}
		return output.toString();
	}

	public static byte[] escape(byte[] b) { // TODO check right
		int count = 0;
		for (int i = 0; i < b.length; i++) {
			if (b[i] == 27) {
				count++;
			}
		}
		byte[] out = new byte[b.length+count];
		int shift = 0;
		for (int i=0; i<b.length;i++) {
			if (b[i] == 27) {
				out[i + shift] = -128;
				shift++;
			}
			out[i + shift] = b[i];
		}
		return out;
	}

	public static byte[] unescape(byte[] b) { // TODO check right
		int count = 0;
		for (int i = 0; i < b.length-1; i++) {
			if (b[i] == -128 && b[i+1] == 27) {
				count++;
			}
		}
		byte[] out = new byte[b.length-count];
		int shift = 0;
		for (int i=0; i<b.length-1;i++) {
			if (b[i] == -128 && b[i+1] == 27) {
				out[i-shift] = b[i+1];
				shift++;
				i++;
			}
		}
		return out;
	}
	
	public static byte[] addHeader(byte[] b){
		byte[] out = new byte[b.length+1];
		out[0]=27;	//header byte
		for (int i=1; i<b.length+1;i++)
			out[i]=b[i-1];
		return out;
	}

	public static byte[] littleEndianSwap(byte[] b){
		byte[] out=new byte[b.length];
		for (int i = 0; i < b.length-1; i += 2) {
			out[i] = b[i + 1];
			out[i+1] = b[i];
		}
		return out;
	}
	
	public static byte[] removeHeader(byte[] b){
		byte[] out = new byte[b.length-1];
		for (int i=0; i<b.length-1; i++)
			out[i]=b[i+1];
		return out;
	}
	
	public static String littleEndianSwap(String hex) {
		String hexSwapped = "";
		for (int i = 0; i < hex.length() - 3; i += 4) {
			hexSwapped = hexSwapped + hex.substring(i + 2, i + 4);
			hexSwapped = hexSwapped + hex.substring(i, i + 2);
		}
		return hexSwapped;
	}

	public static String removeHeader(String message) {
		message = message.substring(2); // remove synchronisation
		message = message.replaceAll("1BFF", "1B");
		message = message.substring(16);
		return message;
	}
}
