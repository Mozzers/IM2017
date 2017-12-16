package logic;

import com.sun.javafx.tools.ant.Utils;
import com.sun.org.apache.bcel.internal.classfile.Utility;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class BLInterface {

	private static ComInterface machine; // port to machine
	private static ArrayList<ListInfo> currentParList = null;

	public static void openPort(String port) {
		machine = new ComInterface(port);
		// TODO Handling code?
	}

	public static void closePort() {
		machine.closeComInterface();
	}

	public static boolean connect() {
		byte[] destReq = Commands.CMS;
		byte[] srcReq = Commands.CLIENT;
		byte[] byteLengthReq = {0, 10}; //TODO set automatic
		ConnectReq connectReq = new ConnectReq(new TransHd(byteLengthReq, destReq, srcReq));
		try {
			machine.writeBytes(connectReq.toSend());
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] rsp = getRsp(machine); //TODO

		boolean retVal = false;
		if (rsp.length > Utils.connectRspLength) {
			rsp = Utility.removeHeader(rsp);
			rsp = Utility.unescape(rsp);
			rsp = Utility.littleEndianSwap(rsp);

			byte[] byteLengthRsp = { rsp[0], rsp[1] };
			byte[] destRsp = { rsp[2], rsp[3] };
			byte[] srcRsp = { rsp[4], rsp[5] };
			byte[] cmdRsp = { rsp[6], rsp[7] };
			byte[] window_sizeRsp = { rsp[8], rsp[9] };
			byte[] compat_highRsp = { rsp[10] };
			byte[] compat_lowRsp = { rsp[11] };
			byte[] errorRsp = { rsp[12] };
			byte[] return_Rsp = { rsp[13] };
			ConnectRsp connectRsp = new ConnnectRsp(new TransHd(byteLengthRsp, destRsp, srcRsp), window_sizeRsp, compat_highRsp, compat_lowRsp, errorRsp, return_Rsp);
			retVal = connectRsp.getReturn_()[0] == 1; // TODO
		}

		return retVal;
	}

	public static boolean disconnect() {
		byte[] destReq = Commands.CMS;
		byte[] srcReq = Commands.CLIENT;
		byte[] byteLengthReq = {0, 10}; //TODO set automatic
		DisconnectReq disconnectReq = new DisconnectReq(new TransHd(byteLengthReq, destReq, srcReq));
		try {
			machine.writeBytes(disconnectReq.toSend());
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] rsp = getRsp(machine); //TODO

		boolean retVal = false;
		if (rsp.length > Utils.disconnectRspLength) {
			rsp = Utility.removeHeader(rsp);
			rsp = Utility.unescape(rsp);
			rsp = Utility.littleEndianSwap(rsp);

			byte[] byteLengthRsp = { rsp[0], rsp[1] };
			byte[] destRsp = { rsp[2], rsp[3] };
			byte[] srcRsp = { rsp[4], rsp[5] };
			byte[] cmdRsp = { rsp[6], rsp[7] };
			byte[] errorRsp = { rsp[8] };
			byte[] return_Rsp = { rsp[9] };
			DisconnectRsp disconnectRsp = new DisconnnectRsp(new TransHd(byteLengthRsp, destRsp, srcRsp), errorRsp, return_Rsp);
			retVal = disconnectRsp.getReturn_()[0] == 1; // TODO
		}

		return retVal;
	}

	public static String getParList() {
		byte[] destReq = Commands.CMS;
		byte[] srcReq = Commands.CLIENT;
		byte[] byteLengthReq = {0, 10}; //TODO set automatic
		ParListReq parListReq = new ParListReq(new TransHd(byteLengthReq, destReq, srcReq));
		try {
			machine.writeBytes(parListReq.toSend());
		} catch (IOException e) {
			e.printStackTrace();
		}

		int total = 0;
		int actual = -1;
		while (actual != total) {
			byte[] rsp = getRsp(machine);
			if (rsp.length > Utils.parListRspLength) {
				rsp = Utility.removeHeader(rsp);
				rsp = Utility.unescape(rsp);
				rsp = Utility.littleEndianSwap(rsp);

				byte[] byteLengthRsp = { rsp[0], rsp[1] };
				byte[] destRsp = { rsp[2], rsp[3] };
				byte[] srcRsp = { rsp[4], rsp[5] };
				byte[] cmdRsp = { rsp[6], rsp[7] };
				byte[] actualRsp = { rsp[8] };
				byte[] totalRsp = { rsp[9] };

				ListInfo[] list_idRsp = new ListInfo[floor((rsp.length-Utils.parListRspLength)/Utils.listInfoRspLength)];
				int index = 0;
				for (int i = Utils.parListRspLength; i < rsp.length - Utils.listInfoRspLength + 1; i += Utils.listInfoRspLength) {
					byte[] sourceIdRsp = { rsp[i], rsp[i+1] };
					byte[] channelIdRsp = { rsp[i+2], rsp[i+3] };
					byte[] msgTypeRsp = { rsp[i+4], rsp[i+5] };
					byte[] channelNoRsp = { rsp[i+6] };
					byte[] sourceNoRsp = { rsp[i+7] };
					//unused field 1 byte
					byte[] layerRsp = { rsp[i+9] };

					MsgID msgIdRsp = new MsgID(sourceIdRsp, channelIdRsp, msgTypeRsp, channelNoRsp, sourceNoRsp, layerRsp);
					byte[] asciiIdRsp = new byte[Utils.asciiIdRspLength];
					for (int j = 0; j < Utils.asciiIdRspLength; j++) {
						asciiIdRsp[j] = rsp[i+Utils.msgIdRspLength+j];
					}
					String asciiIdStringRsp = Utility.parseASCII(Utility.toHexString(asciiIdRsp));
					list_idRsp[index] = new ListInfo(msgIdRsp, asciiIdStringRsp);
					index++;
				}

				ParListRsp parListRsp = new ParListRsp(new TransHd(byteLengthRsp, destRsp, srcRsp), actualRsp, totalRsp, list_idRsp);
				total = parListRsp.getTotal();
				actual = parListRsp.getActual();
				currentParList.add(parListRsp.getList_id());
			}
		}

		String ret = "";
		for (ListInfo entry : currentParList) {
			ret = ret + entry.toString() + "\n";
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
