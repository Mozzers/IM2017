package logic;

import DTO.Commands;
import DTO.ConnectReq;
import DTO.ConnectRsp;
import DTO.DisconnectReq;
import DTO.DisconnectRsp;
import DTO.ListInfo;
import DTO.MsgID;
import DTO.ParListReq;
import DTO.ParListRsp;
import DTO.SingleTuneReq;
import DTO.SingleTuneRsp;
import DTO.TransHd;
import DTO.Utility;
import proxy.ComInterface;
import proxy.DataEncapsulationInterface;
import proxy.Utils;

import java.io.IOException;
import java.util.ArrayList;

public class BLInterface {

	private static ComInterface machine; // port to machine
	private static ArrayList<ListInfo> currentParList = new ArrayList<ListInfo>();

	public static void openPort(String port) {
		machine = new ComInterface(port);
	}

	public static void closePort() {
		machine.closeComInterface();
	}

	public static boolean connect() {
		byte[] destReq = Commands.CMS;
		byte[] srcReq = Commands.CLIENT;
		byte[] byteLengthReq = { 0, 10 };
		ConnectReq connectReq = new ConnectReq(new TransHd(byteLengthReq, destReq, srcReq));
		try {
			machine.writeBytes(connectReq.toSend());
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] rsp = DataEncapsulationInterface.getRsp(machine);

		boolean retVal = false;
		if (rsp.length > Utils.connectRspLength) {
			rsp = Utility.removeHeader(rsp);
			rsp = Utility.unescape(rsp);
			rsp = Utility.littleEndianSwap(rsp);

			byte[] byteLengthRsp = { rsp[0], rsp[1] };
			byte[] destRsp = { rsp[2], rsp[3] };
			byte[] srcRsp = { rsp[4], rsp[5] };
			// byte[] cmdRsp = { rsp[6], rsp[7] };
			byte[] window_sizeRsp = { rsp[8], rsp[9] };
			byte[] compat_highRsp = { rsp[10] };
			byte[] compat_lowRsp = { rsp[11] };
			byte[] errorRsp = { rsp[12] };
			byte[] return_Rsp = { rsp[13] };
			ConnectRsp connectRsp = new ConnectRsp(new TransHd(byteLengthRsp, destRsp, srcRsp), window_sizeRsp,
					compat_highRsp, compat_lowRsp, errorRsp, return_Rsp);
			retVal = connectRsp.getReturn_()[0] == 1;
		}

		return retVal;
	}

	public static boolean disconnect() {
		byte[] destReq = Commands.CMS;
		byte[] srcReq = Commands.CLIENT;
		byte[] byteLengthReq = { 0, 8 };
		DisconnectReq disconnectReq = new DisconnectReq(new TransHd(byteLengthReq, destReq, srcReq));
		try {
			machine.writeBytes(disconnectReq.toSend());
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] rsp = DataEncapsulationInterface.getRsp(machine);

		boolean retVal = false;
		if (rsp.length > Utils.disconnectRspLength) {
			rsp = Utility.removeHeader(rsp);
			rsp = Utility.unescape(rsp);
			rsp = Utility.littleEndianSwap(rsp);

			byte[] byteLengthRsp = { rsp[0], rsp[1] };
			byte[] destRsp = { rsp[2], rsp[3] };
			byte[] srcRsp = { rsp[4], rsp[5] };
			// byte[] cmdRsp = { rsp[6], rsp[7] };
			byte[] errorRsp = { rsp[8] };
			byte[] return_Rsp = { rsp[9] };
			DisconnectRsp disconnectRsp = new DisconnectRsp(new TransHd(byteLengthRsp, destRsp, srcRsp), errorRsp,
					return_Rsp);
			retVal = disconnectRsp.getReturn_()[0] == 1;
		}

		return retVal;
	}

	public static String getParList() {
		byte[] destReq = Commands.CMS;
		byte[] srcReq = Commands.CLIENT;
		byte[] byteLengthReq = { 0, 10 };
		ParListReq parListReq = new ParListReq(new TransHd(byteLengthReq, destReq, srcReq));
		try {
			machine.writeBytes(parListReq.toSend());
		} catch (IOException e) {
			e.printStackTrace();
		}

		int total = 0;
		int actual = -1;
		while (actual != total) {
			byte[] rsp = DataEncapsulationInterface.getRsp(machine);
			if (rsp.length > Utils.parListRspLength) {
				rsp = Utility.removeHeader(rsp);
				rsp = Utility.unescape(rsp);
				rsp = Utility.littleEndianSwap(rsp);

				byte[] byteLengthRsp = { rsp[0], rsp[1] };
				byte[] destRsp = { rsp[2], rsp[3] };
				byte[] srcRsp = { rsp[4], rsp[5] };
				// byte[] cmdRsp = { rsp[6], rsp[7] };
				byte[] actualRsp = { rsp[8] };
				byte[] totalRsp = { rsp[9] };

				ListInfo[] list_idRsp = new ListInfo[(int) Math
						.floor((rsp.length - Utils.parListRspLength) / Utils.listInfoRspLength)];
				int index = 0;
				for (int i = Utils.parListRspLength; i < rsp.length - Utils.listInfoRspLength
						+ 1; i += Utils.listInfoRspLength) {
					byte[] sourceIdRsp = { rsp[i], rsp[i + 1] };
					byte[] channelIdRsp = { rsp[i + 2], rsp[i + 3] };
					byte[] msgTypeRsp = { rsp[i + 4], rsp[i + 5] };
					byte[] channelNoRsp = { rsp[i + 6] };
					byte[] sourceNoRsp = { rsp[i + 7] };
					// unused field 1 byte
					byte[] layerRsp = { rsp[i + 9] };

					MsgID msgIdRsp = new MsgID(sourceIdRsp, channelIdRsp, msgTypeRsp, channelNoRsp, sourceNoRsp,
							layerRsp);
					byte[] asciiIdRsp = new byte[Utils.asciiIdRspLength];
					for (int j = 0; j < Utils.asciiIdRspLength; j++) {
						asciiIdRsp[j] = rsp[i + Utils.msgIdRspLength + j];
					}
					String asciiIdStringRsp = Utility.parseASCII(Utility.toHexString(asciiIdRsp));
					list_idRsp[index] = new ListInfo(msgIdRsp, asciiIdStringRsp);
					index++;
				}

				ParListRsp parListRsp = new ParListRsp(new TransHd(byteLengthRsp, destRsp, srcRsp), actualRsp, totalRsp,
						list_idRsp);
				total = Integer.parseInt(Utility.toDecimalString(parListRsp.getTotal()));
				actual = Integer.parseInt(Utility.toDecimalString(parListRsp.getActual()));
				for (ListInfo listInfo : parListRsp.getList_id()) {
					currentParList.add(listInfo);
				}
			}
		}

		String ret = "";
		for (ListInfo entry : currentParList) {
			ret = ret + entry.toString() + "\n";
		}
		return ret;
	}

	public static String getSingleTune(int id) {
		byte[] destReq = Commands.CMS;
		byte[] srcReq = Commands.CLIENT;
		byte[] byteLengthReq = { 0, 22 };
		byte[] tune_idReq = { 0, Utility.intToByteArray(id)[0] };
		MsgID msg_idReq = currentParList.get(id).getMsgIdTyp(); // TODO like
																// this ? index iut of bunds?
		byte[] mpb_hdReq = { 0, 0 };
		byte[] app_rec_lenReq = { 0 };
		byte[] rec_idReq = { 0 };
		SingleTuneReq singleTuneReq = new SingleTuneReq(new TransHd(byteLengthReq, destReq, srcReq), tune_idReq,
				msg_idReq, mpb_hdReq, app_rec_lenReq, rec_idReq);
		try {
			machine.writeBytes(singleTuneReq.toSend());
		} catch (IOException e) {
			e.printStackTrace();
		}

		byte[] rsp = DataEncapsulationInterface.getRsp(machine);

		if (rsp.length > Utils.singleTuneRspLength) {
			rsp = Utility.removeHeader(rsp);
			rsp = Utility.unescape(rsp);
			rsp = Utility.littleEndianSwap(rsp);

			byte[] byteLengthRsp = { rsp[0], rsp[1] };
			byte[] destRsp = { rsp[2], rsp[3] };
			byte[] srcRsp = { rsp[4], rsp[5] };
			// byte[] cmdRsp = { rsp[6], rsp[7] };
			byte[] mpb_hdRsp = { rsp[18], rsp[19] };
			byte[] app_rec_lenRsp = { rsp[20] };
			byte[] rec_idRsp = { rsp[21] };

			byte[] sourceIdRsp = { rsp[8], rsp[9] };
			byte[] channelIdRsp = { rsp[10], rsp[11] };
			byte[] msgTypeRsp = { rsp[12], rsp[13] };
			byte[] channelNoRsp = { rsp[14] };
			byte[] sourceNoRsp = { rsp[15] };
			// unused field 1 byte
			byte[] layerRsp = { rsp[17] };
			MsgID msg_idRsp = new MsgID(sourceIdRsp, channelIdRsp, msgTypeRsp, channelNoRsp, sourceNoRsp, layerRsp);

			SingleTuneRsp singleTuneRsp = new SingleTuneRsp(new TransHd(byteLengthRsp, destRsp, srcRsp), msg_idRsp,
					mpb_hdRsp, app_rec_lenRsp, rec_idRsp);
		}

		String ret = ""; // TODO what to return ?
		return ret;
	}

}
