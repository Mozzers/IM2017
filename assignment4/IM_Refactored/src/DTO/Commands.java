package DTO;

public class Commands {
	public static final byte[] CONNECT_REQ = { 0, 1 };
	public static final byte[] CONNECT_RSP = { 0, 2 };
	public static final byte[] DISCONNECT_REQ = { 0, 7 };
	public static final byte[] DISCONNECT_RSP = { 0, 8 };
	public static final byte[] PAR_LIST_REQ = { 0, 11 };
	public static final byte[] PAR_LIST_RSP = { 0, 12 };
	public static final byte[] TUNE_REQ = { 0, 13 };
	public static final byte[] TUNE_RSP = { 0, 14 };
	public static final byte[] SINGLE_TUNE_REQ = { 0, 15 };
	public static final byte[] SINGLE_TUNE_RSP = { 0, 16 };
	public static final byte[] CMS = { -128, 97 };
	public static final byte[] CLIENT = { 0, 10 };
}
