package DTO;

public class ListInfo {

	private MsgID msgIdTyp;
	private String asciiId;

	public ListInfo(MsgID msgIdTyp, String asciiId) {
		super();
		this.msgIdTyp = msgIdTyp;
		this.asciiId = asciiId;
	}

	public MsgID getMsgIdTyp() {
		return msgIdTyp;
	}

//	public void setMsgIdTyp(MsgID msgIdTyp) {
//		this.msgIdTyp = msgIdTyp;
//	}

	protected String getAsciiId() {
		return asciiId;
	}

//	public void setAsciiId(String asciiId) {
//		this.asciiId = asciiId;
//	}

	@Override
	public String toString() {
		String retVal = "";
		retVal += this.msgIdTyp.toString();
		retVal += " ASCII: " + this.getAsciiId();
		return retVal;
	}
}
