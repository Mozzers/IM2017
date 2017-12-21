package DTO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class DisconnectReq {

	private TransHd trans_hd;
	private final byte[] cmd = Commands.DISCONNECT_REQ;

	public DisconnectReq(TransHd trans_hd) {
		super();
		this.trans_hd = trans_hd;
	}

	public byte[] toSend() throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		outputStream.write(trans_hd.getTrans_len());
		outputStream.write(trans_hd.getDest_id());
		outputStream.write(trans_hd.getSource_id());
		outputStream.write(cmd);
		byte out[] = outputStream.toByteArray();

		out = Utility.littleEndianSwap(out);
		out = Utility.escape(out);
		out = Utility.addHeader(out);

		return out;
	}

	public byte[] getCmd() {
		return cmd;
	}

	public TransHd getTrans_hd() {
		return trans_hd;
	}

	public void setTrans_hd(TransHd trans_hd) {
		this.trans_hd = trans_hd;
	}

}
