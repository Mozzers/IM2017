import logic.CMSInterface;

public class test {

	public static void main(String[] args) {
		byte[] b= {(byte) 0xFF,(byte)0xFE};
		String res=CMSInterface.toDecimalString(b);
		System.out.println(res);
		byte[] p ={(byte) 0x80, 10, (byte) 0x80, 6 , 0 , 3, 1, 2, 1, 0};
		System.out.println(CMSInterface.toHexString(p));
	}
}