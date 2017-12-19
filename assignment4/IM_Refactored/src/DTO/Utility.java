package DTO;

import java.nio.ByteBuffer;

import javax.xml.bind.DatatypeConverter;

public class Utility {

	public static String toHexString(byte[] array) {
		return DatatypeConverter.printHexBinary(array);
	}

	public static String toDecimalString(byte[] array) {
		ByteBuffer wrapped = ByteBuffer.wrap(array);
		int decimal;
		if (array.length == 1) {
			Byte b = array[0];
			decimal = b.intValue() & 0xFF;
		} else {
			decimal = (wrapped.getShort() & (-1 >>> 16));
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
		// if (msg[msg.length - 1] == -128) // case when buffer ends with 1B ->
		// swap FF ??
		// count++;
		return count;
	}

	private static String intToHexString(int i) {
		byte[] byteInt = ByteBuffer.allocate(4).putInt(i).array();
		byte[] byteIntShort = { byteInt[2], byteInt[3] };
		return toHexString(byteIntShort);
	}
	
	public static byte[] intToByteArray(int i) {
		return toByteArray(intToHexString(i));
	}

	public static String parseASCII(String hex) {
		StringBuilder output = new StringBuilder();
		for (int i = 0; i < hex.length() - 3; i += 4) {
			String str = hex.substring(i, i + 4);
			output.append((char) Integer.parseInt(str, 16));
		}
		return output.toString();
	}

	public static byte[] escape(byte[] b) {
		int count = 0;
		for (byte aB : b) {
			if (aB == 27) {
				count++;
			}
		}
		byte[] out = new byte[b.length + count];
		int shift = 0;
		for (int i = 0; i < b.length; i++) {
			if (b[i] == 27) {
				out[i + shift] = -128;
				shift++;
			}
			out[i + shift] = b[i];
		}
		return out;
	}

	public static byte[] unescape(byte[] b) {
		int count = 0;
		for (int i = 0; i < b.length - 1; i++) {
			if (b[i] == -128 && b[i + 1] == 27) {
				count++;
			}
		}
		byte[] out = new byte[b.length - count];
		int shift = 0;
		for (int i = 0; i < b.length - 1; i++) {
			if (b[i] == -128 && b[i + 1] == 27) {
				out[i - shift] = b[i + 1];
				shift++;
				i++;
			} else {
				out[i - shift] = b[i];
			}
		}
		return out;
	}

	public static byte[] addHeader(byte[] b) {
		byte[] out = new byte[b.length + 1];
		out[0] = 27; // header byte
		System.arraycopy(b, 0, out, 1, b.length - 1);
		return out;
	}

	public static byte[] littleEndianSwap(byte[] b) {
		byte[] out = new byte[b.length];
		for (int i = 0; i < b.length - 1; i += 2) {
			out[i] = b[i + 1];
			out[i + 1] = b[i];
		}
		return out;
	}

	public static byte[] removeHeader(byte[] b) {
		byte[] out = new byte[b.length - 1];
		System.arraycopy(b, 1, out, 0, b.length - 1);
		return out;
	}

}
