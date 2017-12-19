package DTO;

public class DisconnectRsp {

	private TransHd trans_hd;
	private final byte[] cmd = Commands.DISCONNECT_RSP;
	private byte[] error; // 1 byte
	private byte[] return_; // 1 byte

	public DisconnectRsp(TransHd trans_hd, byte[] error, byte[] return_) {
		super();
		this.trans_hd = trans_hd;
		this.error = error;
		this.return_ = return_;
	}

	public TransHd getTrans_hd() {
		return trans_hd;
	}

	public void setTrans_hd(TransHd trans_hd) {
		this.trans_hd = trans_hd;
	}

	public byte[] getError() {
		return error;
	}

	public void setError(byte[] error) {
		this.error = error;
	}

	public byte[] getReturn_() {
		return return_;
	}

//	public void setReturn_(byte[] return_) {
//		this.return_ = return_;
//	}

	public byte[] getCmd() {
		return cmd;
	}

}
