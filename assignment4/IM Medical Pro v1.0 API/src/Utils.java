/**
 * Simple program to open communications ports and connect to Agilent Monitor
 * Several useful values and methods
 * @version 1.2 - 30 Set 2003
 * @author Francisco Cardoso (fmcc@student.dei.uc.pt)
 * @author Ricardo Sal (ricsal@student.dei.uc.pt)
 */


import javax.comm.CommPortIdentifier;
import java.util.Enumeration;

public class Utils {
	static final int TYP_SPI_WS = 3;
	static final int TYP_SPI_CW = 2;
	static final int TYP_SPI_NU = 7;
	static final int SPI_GAIN_OFFSET = 15;
	static final int SPI_CALIBR_PARAM = 11;
	static final int SPI_RT_UNIT = 35;
	static final int SPI_NRM_UNIT = 47;
	static final int SPI_UNIT = 28;
	static final int SPI_NUMERIC = 17;
	static final int SPI_ALARM_LIMITS = 3;
	static final int SPI_RANGE = 24;
	static final int SPI_NUMERIC_STRING = 18;
	static final int SPI_ABS_TIME_STAMP = 1;

	static int destino = 32865;
	static int origem = 10;

	/**
	 * Lists all available communication ports (COM and LPT) on this machine
	 * @return Enumeration with all the ports
	 */

	public static Enumeration getPorts() {
		Enumeration portList = CommPortIdentifier.getPortIdentifiers();
		return portList;
	}

}