package proxy;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public class DataEncapsulationInterface {
	
	public static byte[] getRsp(ComInterface port) {
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
