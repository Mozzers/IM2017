package DTO;

public class MsgID {

	private byte[] sourceId; // 2 byte
	private byte[] channelId; // 2 byte
	private byte[] msgType; // 2 byte
	private byte[] channelNo; // 1 byte
	private byte[] sourceNo; // 1 byte
//	private byte[] unused; // 1 byte
	private byte[] layer; // 1 byte

	public MsgID(String hex) {
		this.sourceId = Utility.toByteArray(hex.substring(0, 4));
		this.channelId = Utility.toByteArray(hex.substring(4, 8));
		this.msgType = Utility.toByteArray(hex.substring(8, 12));
		this.channelNo = Utility.toByteArray(hex.substring(12, 14));
		this.sourceNo = Utility.toByteArray(hex.substring(14, 16));
		// unused field occupying 16-18
		this.layer = Utility.toByteArray(hex.substring(18, 20));
	}

	public MsgID(byte[] sourceId, byte[] channelId, byte[] msgTpe, byte[] channelNo, byte[] sourceNo, byte[] layer) {
		super();
		this.sourceId = sourceId;
		this.channelId = channelId;
		this.msgType = msgTpe;
		this.channelNo = channelNo;
		this.sourceNo = sourceNo;
		this.layer = layer;
	}

	protected byte[] getSourceId() {
		return sourceId;
	}

//	public void setSourceId(byte[] sourceId) {
//		this.sourceId = sourceId;
//	}

	protected byte[] getChannelId() {
		return channelId;
	}

//	public void setChannelId(byte[] channelId) {
//		this.channelId = channelId;
//	}

	protected byte[] getMsgType() {
		return msgType;
	}

//	public void setMsgType(byte[] msgType) {
//		this.msgType = msgType;
//	}

	protected byte[] getChannelNo() {
		return channelNo;
	}

//	public void setChannelNo(byte[] channelNo) {
//		this.channelNo = channelNo;
//	}

	protected byte[] getSourceNo() {
		return sourceNo;
	}

//	public void setSourceNo(byte[] sourceNo) {
//		this.sourceNo = sourceNo;
//	}

	protected byte[] getLayer() {
		return layer;
	}

//	public void setLayer(byte[] layer) {
//		this.layer = layer;
//	}

	public String toHexString() {
		String retVal = "";
		retVal += Utility.toHexString(this.getSourceId());
		retVal += Utility.toHexString(this.getChannelId());
		retVal += Utility.toHexString(this.getMsgType());
		retVal += Utility.toHexString(this.getSourceNo());
		retVal += Utility.toHexString(this.getChannelNo());
		retVal += "00"; // Null
		retVal += Utility.toHexString(this.getLayer());
		return retVal;
	}

	public String toString() {
		String retVal = "";
		retVal += "Source ID: " + Utility.toDecimalString(this.getSourceId());
		retVal += " Channel ID: " + Utility.toDecimalString(this.getChannelId());
		retVal += " Message Type: " + Utility.toDecimalString(this.getMsgType());
		retVal += " Channel No: " + Utility.toDecimalString(this.getChannelId());
		retVal += " Source No: " + Utility.toDecimalString(this.getSourceNo());
		retVal += " Layer: " + Utility.toDecimalString(this.getLayer());
		return retVal;
	}

}
