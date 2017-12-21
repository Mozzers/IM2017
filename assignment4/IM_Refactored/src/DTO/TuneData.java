package DTO;

public class TuneData {

	private TransHd trans_hd;
	private byte[] mpbHd = new byte[2];
	private byte rec_size;
	private byte mpb_len;
	private byte[] mpbData;

	public TuneData(TransHd trans_hd, byte[] mpbHd, byte recSize, byte mpbLen, byte[] mpbData2) {
		super();
		this.trans_hd = trans_hd;
		this.mpbHd = mpbHd;
		this.rec_size = recSize;
		this.mpb_len = mpbLen;
		this.mpbData = mpbData2;
	}

	public TransHd getTrans_hd() {
		return trans_hd;
	}

	public void setTrans_hd(TransHd trans_hd) {
		this.trans_hd = trans_hd;
	}

	public byte[] getMpbHd() {
		return mpbHd;
	}

	public void setMpbHd(byte[] mpbHd) {
		this.mpbHd = mpbHd;
	}

	public byte getRec_size() {
		return rec_size;
	}

	public void setRec_size(byte rec_size) {
		this.rec_size = rec_size;
	}

	public byte getMpb_len() {
		return mpb_len;
	}

	public void setMpb_len(byte mpb_len) {
		this.mpb_len = mpb_len;
	}

	public void setMpbData(byte[] mpbData) {
		this.mpbData = mpbData;
	}

	@Override
	public String toString() {
		String out = new String("Mpb Hd:" + Utility.toDecimalString(mpbHd));
		out = out + "\n Message received (in Hexadecimal):\n" + Utility.toHexString(mpbData);
		return out;

	}

}
