package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.text.DefaultCaret;

import platform.HXMessenger;
import platform.network.Client;
import platform.network.Server;
import javax.swing.BoxLayout;
import java.awt.FlowLayout;

public class MessangerWindow {
	
	private final int  PORT_DEFAULT = 4444;
	private final String IP_DEFAULT = "chat.harxer.com";
	private final String MESSAGE_FEED_INITIAL_TEXT = "<html><font face='arial'>Harxer Chatter for " + System.getProperty("os.name") + "<br></font></html>";
	// Default name is based on the computer being used.
	
	// Both of these are set to null when the disconnect button is pressed.
	private Server serverMain = null;
	private Client clientMain = null;
	
	private     JFrame frame;
	private JTextField textField_portNumber;
	private JTextField textField_sendMessage;
	private JTextField textField_ipAddress;
	private JTextField textField_username;
	private  JTextPane textPane_messageFeed;
	private    JButton button_startServer;
	private    JButton button_connectToServer;
	
	// messageFeed is separated from the textPane's text so the content is always braced by HTML tags and font applicators.
	private String messageFeed = "";
	// initialize_components() checks the name of the OS and inserts a formatted String into computerPrefix.
	private String computerPrefix = "?";

	/**
	 * Primary user interfaced window for chat application. Both client connection and server running.
	 */
	public MessangerWindow() {
		initialize_components();	
		initialize_componentListeners();
	}

	/**
	 * Initialize the components of the frame.
	 * <p>
	 * Attaches the "Harxer Chatter for [computer]" line at the top of messageFeed.
	 */
	private void initialize_components() {
		frame = new JFrame();
		frame.setTitle("Messenger");
		frame.getContentPane().setBackground(Color.LIGHT_GRAY);
		frame.setMinimumSize(new Dimension(325, 300));
		frame.setResizable(true);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		JPanel panel_connect = new JPanel();
		frame.getContentPane().add(panel_connect);
		panel_connect.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel label_ipAddress = new JLabel("IP Address");
		panel_connect.add(label_ipAddress);
		label_ipAddress.setFont(new Font("Lucida Grande", Font.ITALIC, 11));

		textField_ipAddress = new JTextField();
		panel_connect.add(textField_ipAddress);
		textField_ipAddress.setText(IP_DEFAULT);
		textField_ipAddress.setColumns(10);

		button_connectToServer = new JButton("Connect");
		button_connectToServer.setToolTipText("Connect to a server on the specified port and IP address.");
		panel_connect.add(button_connectToServer);

		JPanel port_panel = new JPanel();
		frame.getContentPane().add(port_panel);

		JLabel label_portNumber = new JLabel(" Port");
		port_panel.add(label_portNumber);
		label_portNumber.setFont(new Font("Lucida Grande", Font.ITALIC, 11));

		textField_portNumber = new JTextField();
		port_panel.add(textField_portNumber);
		textField_portNumber.setText("" + PORT_DEFAULT);
		textField_portNumber.setColumns(10);
		
		button_startServer = new JButton("Start");
		port_panel.add(button_startServer);
		button_startServer.setToolTipText("Start a chat server on your network - on the instructed port.");

		JPanel user_panel = new JPanel();
		frame.getContentPane().add(user_panel);

		JLabel label_userName = new JLabel(" Chat Name");
		user_panel.add(label_userName);
		label_userName.setFont(new Font("Lucida Grande", Font.ITALIC, 11));

		textField_username = new JTextField();
		user_panel.add(textField_username);
		// See: computerPrefix declaration
		if (System.getProperty("os.name").contains("Mac")) {
			textField_username.setText("Justin");
			computerPrefix = "<font color=orange>Mac</font>";
		} else if (System.getProperty("os.name").contains("Windows")) {
			textField_username.setText("John");
			computerPrefix = "<font color=blue>Win</font>";
		} else 
			textField_username.setText("Mr. Penguin");
		textField_username.setColumns(10);

		JPanel message_panel = new JPanel();
		frame.getContentPane().add(message_panel);
		message_panel.setMinimumSize(new Dimension(100, 50));
		message_panel.setBorder(null);
		message_panel.setBackground(Color.LIGHT_GRAY);
		message_panel.setLayout(new BorderLayout(0, 0));
		
		textPane_messageFeed = new JTextPane();
		textPane_messageFeed.setMinimumSize(new Dimension(300, 50));
		textPane_messageFeed.setEnabled(false);
		textPane_messageFeed.setEditable(false);
		textPane_messageFeed.setContentType("text/html");
		// Places in the "Harxer Chatter for [computer]" note at beginning of messageFeed.
		textPane_messageFeed.setText(MESSAGE_FEED_INITIAL_TEXT);
		DefaultCaret caret = (DefaultCaret)textPane_messageFeed.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		JScrollPane messageFeed_scrollPane = new JScrollPane(textPane_messageFeed);
		messageFeed_scrollPane.setMinimumSize(new Dimension(300, 50));
		message_panel.add(messageFeed_scrollPane, BorderLayout.CENTER);
		
		textField_sendMessage = new JTextField();
		textField_sendMessage.setEnabled(false);
		textField_sendMessage.setText("Send message...");
		message_panel.add(textField_sendMessage, BorderLayout.SOUTH);
		textField_sendMessage.setColumns(10);
		
		frame.setBounds(100, 100, 631, 503);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	/**
	 * Initializes various action and key listeners on needed components.
	 */
	private void initialize_componentListeners() {
		button_startServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (serverMain == null) {
					HXMessenger.wind.playServerRunningAnimations(true);
					serverMain = new Server(Integer.parseInt(textField_portNumber.getText()));
				} else {
					serverMain.stop();
					HXMessenger.wind.playServerRunningAnimations(false);
					serverMain = null;
				}
			}
		});
		button_connectToServer.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (clientMain == null) {
					HXMessenger.wind.playClientConnectedAnimations(true);
					clientMain = new Client(Integer.parseInt(textField_portNumber.getText()), textField_ipAddress.getText(), textField_username.getText());
				} else if (clientMain.getSocket().isClosed()){
					HXMessenger.wind.playClientConnectedAnimations(true);
					clientMain = new Client(Integer.parseInt(textField_portNumber.getText()), textField_ipAddress.getText(), textField_username.getText());
				} else {
					clientMain.disconnect();
					HXMessenger.wind.playClientConnectedAnimations(false);
					clientMain = null;
				}
			}
		});
		textField_sendMessage.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) { }
			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyChar() == KeyEvent.VK_ENTER) && (textField_sendMessage.getText() != "")) {
					// The line to be added. Formatted: ([computer]) [user name]: [message]
					String line = "(" + computerPrefix + ") "
							+ "<font color=#ADD8E6><b>" + textField_username.getText() + ":</b></font> " 
							+ textField_sendMessage.getText();
					// Appends the new line from the send text field to the messageFeed string.
					messageFeed += line + "<br>";
					// Refreshes the textPane's text with the updated messageFeed string.
					textPane_messageFeed.setText(
							"<html><font face='arial'>"
							+ "Harxer Chatter for " + System.getProperty("os.name") + "<br>" 
							+ messageFeed 
							+ "</font></html>"
					);
					// Send user name and message as a line to the client via send method
					clientMain.send(line);
					textField_sendMessage.setText("");
				}
			}
			@Override
			public void keyReleased(KeyEvent e) { }
		});
	}

	/**
	 * Sets the parent JFrame to visible.
	 */
	public void showFrame() {
		frame.setVisible(true);
	}
	
	/**
	 * Changes the Start button and port text field.
	 * <p>
	 * If the parameter is sent with a 'true' then the JButton text is changed to "Stop" and the text field uneditable.
	 * If the parameter is sent with a 'false' then the JButton text is changed to "Start" and the text field editable.
	 * @param running - Determines the user interface visuals based on above options.
	 */
	public void playServerRunningAnimations(boolean running) {
		if (running) {
			button_startServer.setText("Stop");
			textField_portNumber.setEnabled(false);
		} else {
			button_startServer.setText("Start");
			textField_portNumber.setEnabled(true);
		}
	}
	
	/**
	 * Changes the Connection button and IP text field.
	 * <p>
	 * If the parameter is sent with a 'true' then the JButton text is changed to "Disconnect" and the text field uneditable.
	 * If the parameter is sent with a 'false' then the JButton text is changed to "Connect" and the text field editable.
	 * @param connected - Determines the user interface visuals based on above options.
	 */
	public void playClientConnectedAnimations(boolean connected) {
		if (connected) {
			button_connectToServer.setText("Disconnect");
			textField_ipAddress.setEnabled(false);
			textField_username.setEnabled(false);
			textPane_messageFeed.setEnabled(true);
			textField_sendMessage.setEnabled(true);
			textField_sendMessage.requestFocus();
		} else {
			button_connectToServer.setText("Connect");
			textField_ipAddress.setEnabled(true);
			textField_username.setEnabled(true);
			textPane_messageFeed.setEnabled(false);
			textField_sendMessage.setEnabled(false);
		}
	}
	
	/**
	 * Appends a String to a new line in the message panel.
	 * @param data - The string to be appended.
	 */
	public void receiveMessage(String data) {
		messageFeed += data + "<br>";
		textPane_messageFeed.setText(
				"<html><font face='arial'>"
				+ "Harxer Chatter for " + System.getProperty("os.name") + "<br>" 
				+ messageFeed 
				+ "</font></html>"
		);
	}
	public Server getServer() {
		return serverMain;
	}
	public Client getClient() {
		return clientMain;
	}
}