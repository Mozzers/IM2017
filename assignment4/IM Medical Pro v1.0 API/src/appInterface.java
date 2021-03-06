/**
 * Simple program to open communications ports and connect to Agilent Monitor
 * Graphical User Interface
 * @version 1.2 - 30 Set 2003
 * @author Francisco Cardoso (fmcc@student.dei.uc.pt)
 * @author Ricardo Sal (ricsal@student.dei.uc.pt)
 */


import javax.comm.CommPortIdentifier;
import javax.comm.SerialPort;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Enumeration;


public class appInterface extends javax.swing.JFrame {
	private ComInterface port;
	private ArrayList<ListInfo> currentParList;
	private boolean connected = false;
	private JTextArea textArea = new javax.swing.JTextArea();
	private JButton openButton = new javax.swing.JButton();
	private JButton disconnButton = new javax.swing.JButton();
	private JButton getParButton = new javax.swing.JButton();
	private JButton closeButton = new javax.swing.JButton();
	private JButton connButton = new javax.swing.JButton();
	private JComboBox portComboBox = new javax.swing.JComboBox();
	private JLabel portLabel = new javax.swing.JLabel();
	private JScrollPane scrollPane = new javax.swing.JScrollPane();
	private JButton singleTuneButton = new javax.swing.JButton();
	private JCheckBox invertCheckBox = new javax.swing.JCheckBox("Invert Bits");
	private JTextField idTextField = new javax.swing.JTextField();
	private JLabel idLabel = new javax.swing.JLabel();

	public appInterface() {

		setTitle("JFC Application");
		setDefaultCloseOperation(javax.swing.JFrame.DO_NOTHING_ON_CLOSE);
		getContentPane().setLayout(null);
		setSize(814, 611);
		setVisible(false);
		scrollPane.setBounds(36, 36, 624, 540);
		scrollPane.setViewportView(textArea);
		getContentPane().add(scrollPane);
		textArea.setEditable(false);
		setResizable(false);
		openButton.setText("Open");
		getContentPane().add(openButton);
		openButton.setBounds(684, 504, 113, 29);
		disconnButton.setText("Disconnect");
		disconnButton.setEnabled(false);
		getContentPane().add(disconnButton);
		disconnButton.setBounds(684, 84, 113, 29);
		getParButton.setText("Get Par List");
		getParButton.setEnabled(false);
		getContentPane().add(getParButton);
		getParButton.setBounds(684, 120, 113, 29);
		closeButton.setText("Close");
		closeButton.setEnabled(false);
		getContentPane().add(closeButton);
		closeButton.setBounds(684, 540, 113, 29);
		connButton.setText("Connect");
		connButton.setEnabled(false);
		getContentPane().add(connButton);
		connButton.setBounds(684, 48, 113, 29);
		getContentPane().add(portComboBox);
		portComboBox.setBounds(725, 456, 69, 25);

		Enumeration portList = Utils.getPorts();

		while (portList.hasMoreElements()) {
			CommPortIdentifier portId = (CommPortIdentifier) portList.nextElement();
			portComboBox.addItem(portId.getName());
		}

		portLabel.setText("Port:");
		getContentPane().add(portLabel);
		portLabel.setBounds(684, 456, 44, 26);
		idLabel.setText("Single Tune Id:");
		idLabel.setBounds(684, 156, 113, 29);
		getContentPane().add(idLabel);
		idTextField.setEnabled(false);
		getContentPane().add(idTextField);
		idTextField.setBounds(768, 156, 28, 29);
		singleTuneButton.setText("Single Tune");
		singleTuneButton.setEnabled(false);
		getContentPane().add(singleTuneButton);
		singleTuneButton.setBounds(684, 192, 113, 29);
		getContentPane().add(invertCheckBox);
		invertCheckBox.setBounds(684, 228, 113, 29);
		invertCheckBox.setEnabled(false);
		invertCheckBox.setSelected(true);
		SymWindow aSymWindow = new SymWindow();
		this.addWindowListener(aSymWindow);
		SymAction lSymAction = new SymAction();
		openButton.addActionListener(lSymAction);
		closeButton.addActionListener(lSymAction);
		connButton.addActionListener(lSymAction);
		disconnButton.addActionListener(lSymAction);
		getParButton.addActionListener(lSymAction);
		singleTuneButton.addActionListener(lSymAction);
		invertCheckBox.addActionListener(lSymAction);

	}

	static public void main(String args[]) {
		try {
			(new appInterface()).setVisible(true);
		} catch (Throwable t) {
			t.printStackTrace();
			System.exit(1);
		}
	}

	void exitApplication() {
		try {
			Toolkit.getDefaultToolkit().beep();
			int reply = JOptionPane.showConfirmDialog(this,
					"Do you really want to exit?",
					"JFC Application - Exit",
					JOptionPane.YES_NO_OPTION,
					JOptionPane.QUESTION_MESSAGE);
			if (reply == JOptionPane.YES_OPTION) {
				if (connected)
					CMSInterface.disconnect(this.port);

				this.setVisible(false);
				this.dispose();
				System.exit(0);
			}
		} catch (Exception e) {
		}
	}

	class SymWindow extends java.awt.event.WindowAdapter {
		public void windowClosing(java.awt.event.WindowEvent event) {
			Object object = event.getSource();
			if (object == appInterface.this)
				appInterface_windowClosing(event);
		}
	}

	void appInterface_windowClosing(java.awt.event.WindowEvent event) {
		appInterface_windowClosing_Interaction1(event);
	}

	void appInterface_windowClosing_Interaction1(java.awt.event.WindowEvent event) {
		try {
			this.exitApplication();
		} catch (Exception e) {
		}
	}

	class SymAction implements java.awt.event.ActionListener {
		public void actionPerformed(java.awt.event.ActionEvent event) {
			Object object = event.getSource();
			if (object == openButton)
				openButton_actionPerformed(event);
			if (object == closeButton)
				closeButton_actionPerformed(event);
			if (object == connButton)
				connButton_actionPerformed(event);
			if (object == disconnButton)
				disconnButton_actionPerformed(event);
			if (object == getParButton)
				getParButton_actionPerformed(event);
			if (object == singleTuneButton)
				singleTuneButton_actionPerformed(event);
		}
	}

	void openButton_actionPerformed(java.awt.event.ActionEvent event) {
		String port = (String) portComboBox.getSelectedItem();
		this.port = new ComInterface(port);
		openButton.setEnabled(false);
		closeButton.setEnabled(true);
		connButton.setEnabled(true);
	}

	void closeButton_actionPerformed(java.awt.event.ActionEvent event) {
		if (connected)
			if(CMSInterface.disconnect(this.port)) {
				appendText("Disconnection succeded\n");
				connected = false;
			} else {
				appendText("Disconnection failed\n");
			}
		try {
			Thread.sleep(300);
		} catch (Exception e) {
		}
		if (!connected) {
			port.closeComInterface();
			openButton.setEnabled(true);
			closeButton.setEnabled(false);
			connButton.setEnabled(false);
			disconnButton.setEnabled(false);
			getParButton.setEnabled(false);
		}
	}

	void connButton_actionPerformed(java.awt.event.ActionEvent event) {
		if(CMSInterface.connect(this.port)) {
			appendText("Connection succeded\n");
			connected = true;
			disconnButton.setEnabled(true);
			getParButton.setEnabled(true);
			invertCheckBox.setEnabled(true);
		} else {
			appendText("Connection failed\n");
			appendText("Trying to disconnect from a previous connection...\n"); //if connection failed it means there's an open connection
			if(CMSInterface.disconnect(this.port))
				appendText("Disconnection succeded\n");
		}
	}

	void disconnButton_actionPerformed(java.awt.event.ActionEvent event) {
		if (CMSInterface.disconnect(this.port)) {
			appendText("Disconnection succeded\n");
			disconnButton.setEnabled(false);
			connButton.setEnabled(true);
			getParButton.setEnabled(false);
			singleTuneButton.setEnabled(false);
			invertCheckBox.setEnabled(false);
			idTextField.setEnabled(false);
			connected = false;
		} else {
			appendText("Disconnection failed\n");
		}
	}

	void getParButton_actionPerformed(java.awt.event.ActionEvent event) {
		appendText("Waiting for PAR output...\n");
		currentParList = CMSInterface.getParList(this.port);
		for(ListInfo entry: currentParList) {
			appendText(entry + "\n");
		}
		singleTuneButton.setEnabled(true);
		idTextField.setEnabled(true);
	}

	void singleTuneButton_actionPerformed(java.awt.event.ActionEvent event) {
		int msgId = Integer.parseInt(idTextField.getText());
		String out=CMSInterface.singleTuneRequest(this.port,"15",this.currentParList.get(msgId).getMsgIdTyp());
		textArea.append(out);
	}

	void appendText(String text) {
		textArea.append(text);
	}

}
