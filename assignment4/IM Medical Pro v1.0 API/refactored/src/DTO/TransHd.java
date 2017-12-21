package DTO;

public class TransHd {
	private byte[] trans_len = new byte[2];
	private byte[] dest_id=	new byte[2];
	private byte[] source_id=new byte[2];
	
	
	public TransHd(byte[] trans_len, byte[] dest_id, byte[] source_id) {
		super();
		this.trans_len = trans_len;
		this.dest_id = dest_id;
		this.source_id = source_id;
	}
	
	public byte[] getTrans_len() {
		return trans_len;
	}
	public void setTrans_len(byte[] trans_len) {
		this.trans_len = trans_len;
	}
	public byte[] getDest_id() {
		return dest_id;
	}
	public void setDest_id(byte[] dest_id) {
		this.dest_id = dest_id;
	}
	public byte[] getSource_id() {
		return source_id;
	}
	public void setSource_id(byte[] source_id) {
		this.source_id = source_id;
	}
}
