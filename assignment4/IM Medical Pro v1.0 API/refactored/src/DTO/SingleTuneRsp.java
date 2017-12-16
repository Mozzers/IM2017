package DTO;

public class SingleTuneRsp {
	private TransHd trans_hd;
	private final byte[] cmd = Commands.SINGLE_TUNE_RSP;
	private MsgID msg_id;
	private byte[] mpb_hd = new byte[2];
	private byte[] app_rec_len = new byte[1];
	private byte[] rec_id= new byte[1];
	
	public SingleTuneRsp(TransHd trans_hd, MsgID msg_id, byte[] mpb_hd, byte[] app_rec_len, byte[] rec_id) {
		super();
		this.trans_hd = trans_hd;
		this.msg_id = msg_id;
		this.mpb_hd = mpb_hd;
		this.app_rec_len = app_rec_len;
		this.rec_id = rec_id;
	}

	public TransHd getTrans_hd() {
		return trans_hd;
	}

	public void setTrans_hd(TransHd trans_hd) {
		this.trans_hd = trans_hd;
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

	public byte[] getApp_rec_len() {
		return app_rec_len;
	}

	public void setApp_rec_len(byte[] app_rec_len) {
		this.app_rec_len = app_rec_len;
	}

	public byte[] getRec_id() {
		return rec_id;
	}

	public void setRec_id(byte[] rec_id) {
		this.rec_id = rec_id;
	}

	public byte[] getCmd() {
		return cmd;
	}
	
}
