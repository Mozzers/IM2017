package DTO;

public class ConnectRsp {

	private TransHd trans_hd;
	private final byte[] cmd = Commands.CONNECT_RSP;
	private byte[] window_size = new byte[2];
	private byte compat_high;
	private byte compat_low;
	private byte error;
	private byte return_;

	public ConnectRsp(TransHd trans_hd, byte[] window_size, byte compat_high, byte compat_low, byte error,
			byte return_) {
		super();
		this.trans_hd = trans_hd;
		this.window_size = window_size;
		this.compat_high = compat_high;
		this.compat_low = compat_low;
		this.error = error;
		this.return_ = return_;
	}

	public TransHd getTrans_hd() {
		return trans_hd;
	}

	public void setTrans_hd(TransHd trans_hd) {
		this.trans_hd = trans_hd;
	}

	public byte[] getWindow_size() {
		return window_size;
	}

	public void setWindow_size(byte[] window_size) {
		this.window_size = window_size;
	}

	public byte getCompat_high() {
		return compat_high;
	}

	public void setCompat_high(byte compat_high) {
		this.compat_high = compat_high;
	}

	public byte getCompat_low() {
		return compat_low;
	}

	public void setCompat_low(byte compat_low) {
		this.compat_low = compat_low;
	}

	public byte getError() {
		return error;
	}

	public void setError(byte error) {
		this.error = error;
	}

	public byte getReturn_() {
		return return_;
	}

	public void setReturn_(byte return_) {
		this.return_ = return_;
	}

	public byte[] getCmd() {
		return cmd;
	}

}
