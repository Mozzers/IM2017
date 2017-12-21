package DTO;

public class ParListRsp {

	private TransHd trans_hd;
	private final byte[] cmd = Commands.PAR_LIST_RSP;
	private byte[] actual = new byte[1];
	private byte[] total = new byte[1];
	private ListInfo[] list_id;

	public ParListRsp(TransHd trans_hd, byte[] actual, byte[] total, ListInfo[] list_id) {
		super();
		this.trans_hd = trans_hd;
		this.actual = actual;
		this.total = total;
		this.list_id = list_id;
	}

	public TransHd getTrans_hd() {
		return trans_hd;
	}

	public void setTrans_hd(TransHd trans_hd) {
		this.trans_hd = trans_hd;
	}

	public byte[] getActual() {
		return actual;
	}

	public void setActual(byte[] actual) {
		this.actual = actual;
	}

	public byte[] getTotal() {
		return total;
	}

	public void setTotal(byte[] total) {
		this.total = total;
	}

	public ListInfo[] getList_id() {
		return list_id;
	}

	public void setList_id(ListInfo[] list_id) {
		this.list_id = list_id;
	}

	public byte[] getCmd() {
		return cmd;
	}

}
