/**
 * Simple program to open communications ports and connect to Agilent Monitor
 * Communications Port Interface
 * @version 1.2 - 30 Set 2003
 * @author Francisco Cardoso (fmcc@student.dei.uc.pt)
 * @author Ricardo Sal (ricsal@student.dei.uc.pt)
 */

package proxy;

import javax.comm.CommPortIdentifier;
import javax.comm.PortInUseException;
import javax.comm.SerialPort;
import javax.comm.UnsupportedCommOperationException;
import java.io.OutputStream;
import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.Enumeration;

public class ComInterface {

	private Enumeration<?> portList;
	private static CommPortIdentifier portId;
	private static SerialPort serialPort;
	private static OutputStream outputStream;
	private InputStream inputStream;
	private BufferedInputStream buffer;

	/**
	 * Creates a new connection to a specific port.
	 * 
	 * @param port
	 *            The port to connect
	 */
	public ComInterface(String port) {
		portList = CommPortIdentifier.getPortIdentifiers();
		while (portList.hasMoreElements()) {
			portId = (CommPortIdentifier) portList.nextElement();
			if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
				if (portId.getName().equals(port)) {
					try {
						serialPort = (SerialPort) portId.open("IM", 2000);
						try {
							serialPort.setSerialPortParams(38400, SerialPort.DATABITS_8, SerialPort.STOPBITS_1,
									SerialPort.PARITY_NONE);
						} catch (UnsupportedCommOperationException e) {
							System.out.println("Error configurating COM port");
						}
						try {
							inputStream = serialPort.getInputStream();
							buffer = new BufferedInputStream(inputStream, 2048);
						} catch (Exception e) {
							System.out.println("Error creating input stream");
						}
						try {
							outputStream = serialPort.getOutputStream();
						} catch (Exception e) {
							System.out.println("Error creating output stream");
						}
					} catch (PortInUseException e) {
						e.printStackTrace();
						System.out.println("Error opening COM port or port already in use");
					}
				}
			}
		}
	}

	/**
	 * Closes this port
	 */
	public void closeComInterface() {
		try {
			serialPort.close();
		} catch (Exception e) {
			System.out.println("Error closing COM port");
		}
	}

	/**
	 * Writes an array of bytes to this port
	 * 
	 * @param data
	 */
	public void writeBytes(byte[] data) {
		try {
			outputStream.write(data);
			outputStream.flush();
		} catch (Exception e) {
			System.out.println("Error writing to COM port");
		}
	}

	/**
	 * Reads bytes from this port
	 * 
	 * @return An array of bytes
	 */
	public byte[] readBytes() {
		byte[] readBuffer = null;
		try {
			int numBytes = buffer.available();
			readBuffer = new byte[numBytes];
			buffer.read(readBuffer, 0, numBytes);
		} catch (Exception e) {
			System.out.println("Error reading from COM port");
		}
		return readBuffer;
	}

	public byte[] readBytes(int numBytes) {
		byte[] readBuffer = null;
		try {
			if (numBytes == 0 || numBytes > buffer.available())
				numBytes = buffer.available();
			readBuffer = new byte[numBytes];
			buffer.read(readBuffer, 0, numBytes);
		} catch (Exception e) {
			System.out.println("Error reading from COM port");
		}
		return readBuffer;
	}

	/**
	 * Number of bytes available to read from this port
	 * 
	 * @return Number of bytes
	 */
	public int bytesAvailable() {
		int nBytes = 0;
		try {
			nBytes = buffer.available();
		} catch (Exception e) {
			System.out.println("Error reading available bytes from COM port");
		}
		return nBytes;
	}

}