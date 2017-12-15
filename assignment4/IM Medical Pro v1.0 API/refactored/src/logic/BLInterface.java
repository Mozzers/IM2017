package logic;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

import DTO.*;
import proxy.*;

public class BLInterface {

	private static ComInterface machine; // port to machine
	private static ArrayList<ListInfo> currentParList = null;

	public static void openPort(String port) {
		machine = new ComInterface(port);
		// Handling code?
	}

	public static void closePort() {
		machine.closeComInterface();
	}

	public static boolean connect() {
		String stringConnReq = "1B 0A00 6180 0A00 0100 0000";
		
		byte[] dest= Commands.CMS;
		byte[] src = Commands.CLIENT;
		byte[] byteLength= {0,10};
				
		ConnectReq c = new ConnectReq(new TransHd(byteLength,dest,src));
		try {
			machine.writeBytes(c.toSend());
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] rsp = getRsp(machine);
		String stringConnRsp = "1B0E000A0061800200\\w{8}0100";
		return Utility.toHexString(rsp).matches(stringConnRsp);

	}

	public static boolean disconnect() {
		return CMSInterface.disconnect(machine);
	}

	public static String getParList() {
		currentParList = CMSInterface.getParList(machine);
		String ret = "";
		for (ListInfo entry : currentParList) {
			ret = ret + entry + "\n";
		}
		return ret;
	}

	public static String getTune(int id) {
		String ret = "";
		String out = CMSInterface.singleTuneRequest(machine, "15", currentParList.get(id).getMsgIdTyp());
		return out;
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
