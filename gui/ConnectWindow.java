package gui;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;

public class ConnectWindow {

	private JFrame frame;
	private JTextField textField_ipAddress;
	private JTextField textField_portNumber;

	/**
	 * Create the application.
	 */
	public ConnectWindow() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setBounds(100, 100, 325, 150);
		frame.setMinimumSize(new Dimension(325, 150));
		frame.setMaximumSize(new Dimension(325, 150));
		frame.setSize(new Dimension(325, 150));
		frame.getContentPane().setLayout(null);
		
		final int slideAmount = 94;
		
		JLabel label_connectTitle = new JLabel("Messanger");
		label_connectTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
		label_connectTitle.setHorizontalAlignment(SwingConstants.LEFT);
		label_connectTitle.setBounds(6, 6, 313, 25);
		frame.getContentPane().add(label_connectTitle);
		
		textField_ipAddress = new JTextField();
		textField_ipAddress.setBounds(6, 32, 221 + slideAmount, 26);
		frame.getContentPane().add(textField_ipAddress);
		textField_ipAddress.setColumns(10);
		
		JLabel label_ipAddress = new JLabel("Server Address");
		label_ipAddress.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
		label_ipAddress.setBounds(9, 56, 92, 16);
		frame.getContentPane().add(label_ipAddress);
		
		JButton button_connectServer = new JButton("Connect");
		button_connectServer.setBounds(32 + slideAmount/2, 80, 118, 34);
		frame.getContentPane().add(button_connectServer);
		
		JButton button_logout = new JButton("Logout");
		button_logout.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
		button_logout.setBounds(144 + slideAmount/2, 85, 62, 24);
		frame.getContentPane().add(button_logout);
		
		JRadioButton disclosureButton_serverSettings = new JRadioButton("");
		disclosureButton_serverSettings.setBounds(289, 6, 30, 23);
		frame.getContentPane().add(disclosureButton_serverSettings);
		
		JLabel lblSettings = new JLabel("Settings");
		lblSettings.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
		lblSettings.setHorizontalAlignment(SwingConstants.RIGHT);
		lblSettings.setBounds(236, 9, 52, 16);
		frame.getContentPane().add(lblSettings);
		
		// disclosed components
		JSeparator separator = new JSeparator();
		separator.setOrientation(SwingConstants.VERTICAL);
		separator.setBounds(226 + slideAmount, 28, 12, 94);
		frame.getContentPane().add(separator);
		
		JButton button_runServer = new JButton("Run Server");
		button_runServer.setBounds(233 + slideAmount, 80, 90, 34);
		frame.getContentPane().add(button_runServer);
		
		textField_portNumber = new JTextField();
		textField_portNumber.setText("4444");
		textField_portNumber.setColumns(10);
		textField_portNumber.setBounds(236 + slideAmount, 32, 83, 26);
		frame.getContentPane().add(textField_portNumber);
		
		JLabel label_portNumber = new JLabel("Port");
		label_portNumber.setForeground(Color.GRAY);
		label_portNumber.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
		label_portNumber.setBounds(239 + slideAmount, 56, 60, 16);
		frame.getContentPane().add(label_portNumber);
		
	}
}
