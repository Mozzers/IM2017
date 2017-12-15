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

	private static int countEscape(byte[] msg) {
		int count = 0;
		for (int i = 0; i < msg.length - 1; i++)
			if (msg[i + 1] == 27 && msg[i] == -128)
				count++;
		//if (msg[msg.length - 1] == -128) // case when buffer ends with 1B -> swap FF ??
		//	count++;
		return count;
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
}
