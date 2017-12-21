package DTO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class SingleTuneReq {
	private TransHd trans_hd;
	private final byte[] cmd = Commands.SINGLE_TUNE_REQ;
	private byte[] tune_id = new byte[2];
	private MsgID msg_id;
	private byte[] mpb_hd = new byte[2];
	private byte app_rec_len;
	private byte rec_id;

	public SingleTuneReq(TransHd trans_hd, byte[] tune_id, MsgID msg_id, byte[] mpb_hd, byte app_rec_len, byte rec_id) {
		super();
		this.trans_hd = trans_hd;
		this.tune_id = tune_id;
		this.msg_id = msg_id;
		this.mpb_hd = mpb_hd;
		this.app_rec_len = app_rec_len;
		this.rec_id = rec_id;
	}

	public byte[] toSend() throws IOException {
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		outputStream.write(trans_hd.getTrans_len());
		outputStream.write(trans_hd.getDest_id());
		outputStream.write(trans_hd.getSource_id());
		outputStream.write(cmd);
		outputStream.write(tune_id);
		outputStream.write(Utility.toByteArray(msg_id.toHexString()));
		outputStream.write(mpb_hd);
		outputStream.write(app_rec_len);
		outputStream.write(rec_id);
		outputStream.write(new byte[] { 0, 0, 0, 0 });
		byte out[] = outputStream.toByteArray();

		System.out.println("\n testing\n");
		System.out.println(msg_id.toString());

		out = Utility.littleEndianSwap(out);
		out = Utility.escape(out);
		out = Utility.addHeader(out);

		return out;

	}

	public TransHd getTrans_hd() {
		return trans_hd;
	}

	public void setTrans_hd(TransHd trans_hd) {
		this.trans_hd = trans_hd;
	}

	public byte[] getTune_id() {
		return tune_id;
	}

	public void setTune_id(byte[] tune_id) {
		this.tune_id = tune_id;
	}

	public MsgID getMsg_id() {
		return msg_id;
	}

	public void setMsg_id(MsgID msg_id) {
		this.msg_id = msg_id;
	}

	public byte[] getMpb_hd() {
		return mpb_hd;
	}

	public void setMpb_hd(byte[] mpb_hd) {
		this.mpb_hd = mpb_hd;
	}

	public byte getApp_rec_len() {
		return app_rec_len;
	}

	public void setApp_rec_len(byte app_rec_len) {
		this.app_rec_len = app_rec_len;
	}

	public byte getRec_id() {
		return rec_id;
	}

	public void setRec_id(byte rec_id) {
		this.rec_id = rec_id;
	}

	public byte[] getCmd() {
		return cmd;
	}

}
