package DTO;

public class ParListReq {
	private TransHd trans_hd;
	private final byte[] cmd = PAR_LIST_REQ;
	
	
	public ParListReq(TransHd trans_hd) {
		super();
		this.trans_hd = trans_hd;
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
