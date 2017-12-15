package DTO;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ConnectReq {
	
	private TransHd trans_id;
	private final byte[] cmd = Commands.CONNECT_REQ;
	private byte[] tick_periode= {0,0};
	
	public byte[] toSend() throws IOException{
			
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream( );
		outputStream.write( trans_id.getTrans_len());
		outputStream.write( trans_id.getDest_id());
		outputStream.write(trans_id.getSource_id());
		outputStream.write(cmd);
		outputStream.write(tick_periode);
		byte out[] = outputStream.toByteArray( );
		
		out=Utility.littleEndianSwap(out);
		out=Utility.addHeader(out);
		return out;
	}
	
	public ConnectReq(TransHd trans_hd) {
		super();
		this.trans_hd = trans_hd;
	}
	
	public ConnectReq(TransHd trans_hd, byte[] tick_periode){
		super();
		this.trans_hd=trans_hd;
		this.tick_periode=tick_periode;
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
	public byte[] getTick_periode() {
		return tick_periode;
	}
	public void setTick_periode(byte[] tick_periode) {
		this.tick_periode = tick_periode;
	}

	
}
