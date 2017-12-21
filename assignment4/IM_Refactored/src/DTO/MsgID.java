package DTO;

public class MsgID {

	private byte[] sourceId = new byte[2];
	private byte[] channelId = new byte[2];
	private byte[] msgType = new byte[2];
	private byte channelNo;
	private byte sourceNo;
	private byte layer;

	public MsgID(String hex) {
		this.sourceId = Utility.toByteArray(hex.substring(0, 4));
		this.channelId = Utility.toByteArray(hex.substring(4, 8));
		this.msgType = Utility.toByteArray(hex.substring(8, 12));
		this.channelNo = Utility.toByteArray(hex.substring(12, 14))[0];
		this.sourceNo = Utility.toByteArray(hex.substring(14, 16))[0];
		// unused field occupying 16-18
		this.layer = Utility.toByteArray(hex.substring(18, 20))[0];
	}

	public MsgID(byte[] sourceId, byte[] channelId, byte[] msgTpe, byte channelNo, byte sourceNo, byte layer) {
		super();
		this.sourceId = sourceId;
		this.channelId = channelId;
		this.msgType = msgTpe;
		this.channelNo = channelNo;
		this.sourceNo = sourceNo;
		this.layer = layer;
	}

	public byte[] getSourceId() {
		return sourceId;
	}

	public void setSourceId(byte[] sourceId) {
		this.sourceId = sourceId;
	}

	public byte[] getChannelId() {
		return channelId;
	}

	public void setChannelId(byte[] channelId) {
		this.channelId = channelId;
	}

	public byte[] getMsgType() {
		return msgType;
	}

	public void setMsgType(byte[] msgType) {
		this.msgType = msgType;
	}

	public byte getChannelNo() {
		return channelNo;
	}

	public void setChannelNo(byte channelNo) {
		this.channelNo = channelNo;
	}

	public byte getSourceNo() {
		return sourceNo;
	}

	public void setSourceNo(byte sourceNo) {
		this.sourceNo = sourceNo;
	}

	public byte getLayer() {
		return layer;
	}

	public void setLayer(byte layer) {
		this.layer = layer;
	}

	public String toHexString() {
		String retVal = new String();
		retVal += Utility.toHexString(sourceId);
		retVal += Utility.toHexString(channelId);
		retVal += Utility.toHexString(msgType);
		retVal += Utility.toHexString(sourceNo);
		retVal += Utility.toHexString(channelNo);
		retVal += Utility.toHexString(layer);
		retVal += "00"; // Null
		return retVal;
	}

	public String toString() {
		String retVal = new String();
		retVal += "Source ID: " + Utility.toDecimalString(this.getSourceId());
		retVal += " Channel ID: " + Utility.toDecimalString(this.getChannelId());
		retVal += " Message Type: " + Utility.toDecimalString(this.getMsgType());
		retVal += " Channel No: " + Utility.toDecimalString(this.getChannelId());
		retVal += " Source No: " + Utility.toDecimalString(this.getSourceNo());
		retVal += " Layer: " + Utility.toDecimalString(this.getLayer());
		return retVal;
	}

}
