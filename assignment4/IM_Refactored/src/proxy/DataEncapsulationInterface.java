package proxy;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import DTO.Utility;

public class DataEncapsulationInterface {

	public static byte[] getRsp(ComInterface port) {
		ArrayList<byte[]> rsps = new ArrayList<byte[]>();
		int intLength = -2;
		int rspsByteLength = 0;
		int count = 0; // count for timeouts
		while (rspsByteLength - 1 != intLength && count < 10) {
			try {
				Thread.sleep(100);
				int readingCount = intLength - rspsByteLength + 1;
				if (readingCount < 0) {
					readingCount = 3; // min number of bytes
				}
				byte[] actualRsp = port.readBytes(readingCount);
				if (actualRsp.length != 0) {
					intLength += Utility.countEscape(actualRsp);
					rsps.add(actualRsp);
					rspsByteLength += actualRsp.length;
					if (actualRsp[0] == 0x1B && actualRsp[1] != 0xFF) {
						byte[] byteLength = { 0, 0, actualRsp[2], actualRsp[1] };
						intLength = ByteBuffer.wrap(byteLength).getInt();
					}
				} else
					count++;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if (count != 10) {
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
		return null;

	}

	public static byte[] getRawData(ComInterface port) {
		ArrayList<byte[]> out = new ArrayList<byte[]>();

		try {
			Thread.sleep(100);
			while (port.bytesAvailable() > 0) {
				out.add(port.readBytes());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		byte[] ret = new byte[out.size()];
		int index = 0;
		for (byte[] b : out) {
			for (int i = 0; i < out.size(); i++) {
				ret[index] = b[i];
				index++;
			}
		}
		return ret;
	}

}
