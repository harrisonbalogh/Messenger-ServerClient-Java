package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.text.DefaultCaret;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;

import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;

public class Window {
	
	private final int  PORT_DEFAULT = 4444;
	private final String IP_DEFAULT = "chat.harxer.com";
	private final String MESSAGE_FEED_INITIAL_TEXT = "<html><font face='arial'>Harxer Chatter for " + System.getProperty("os.name") + "<br></font></html>";
	
	
	private     JFrame frame;
	private JTextField textField_sendMessage;
	private  JTextPane textPane_messageFeed;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				new Window();
			}
		});
	}

	/**
	 * Primary user interfaced window for chat application. Both client connection and server running.
	 */
	public Window() {
		initialize_components();	
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
		frame.setResizable(true);
		frame.setBounds(100, 100, 325, 300);
		frame.setMinimumSize(new Dimension(325, 125));
		frame.setMaximumSize(new Dimension(325, 1440));
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));

		// Components in this panel slide and interchange using an absolute layout
		JPanel panel_content = new JPanel();
		frame.getContentPane().add(panel_content);
		panel_content.setMinimumSize(new Dimension(325, 125));
		panel_content.setSize(new Dimension(325, 125));
		panel_content.setPreferredSize(new Dimension(325, 125));
		panel_content.setLayout(null);
		
		// The components in this panel are resizable and only relate to sending and viewing messages
		JPanel panel_chat = new JPanel();
		frame.getContentPane().add(panel_chat);
		panel_chat.setBorder(null);
		panel_chat.setBackground(Color.LIGHT_GRAY);
		panel_chat.setLayout(new BorderLayout(0, 0));
		
		// ============================================================ Chat panel compoenents ======
		textPane_messageFeed = new JTextPane();
		textPane_messageFeed.setEnabled(false);
		textPane_messageFeed.setEditable(false);
		textPane_messageFeed.setContentType("text/html");
		textPane_messageFeed.setText(MESSAGE_FEED_INITIAL_TEXT);
		DefaultCaret caret = (DefaultCaret)textPane_messageFeed.getCaret();
		caret.setUpdatePolicy(DefaultCaret.ALWAYS_UPDATE);
		
		JScrollPane messageFeed_scrollPane = new JScrollPane(textPane_messageFeed);
		panel_chat.add(messageFeed_scrollPane, BorderLayout.CENTER);
		
		textField_sendMessage = new JTextField();
		textField_sendMessage.setEnabled(false);
		textField_sendMessage.setText("Send message...");
		panel_chat.add(textField_sendMessage, BorderLayout.SOUTH);
		textField_sendMessage.setColumns(10);
		
		// ============================================================ Logo Screen Components ======
		JLabel label_logoIcon = new JLabel("");
		label_logoIcon.setHorizontalAlignment(SwingConstants.CENTER);
		label_logoIcon.setIcon(new ImageIcon(LogoWindow.class.getResource("/resources/small@2x.png")));
		label_logoIcon.setBounds(6, 6, 313, 116);
		frame.getContentPane().add(label_logoIcon);
		
		JLabel label_harxerTitle = new JLabel("Harxer");
		label_harxerTitle.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		label_harxerTitle.setHorizontalAlignment(SwingConstants.CENTER);
		label_harxerTitle.setBounds(6, 0, 313, 25);
		frame.getContentPane().add(label_harxerTitle);
		
		JLabel label_messangerTitle = new JLabel("Messanger");
		label_messangerTitle.setHorizontalAlignment(SwingConstants.CENTER);
		label_messangerTitle.setFont(new Font("Lucida Grande", Font.BOLD, 20));
		label_messangerTitle.setBounds(6, 97, 313, 25);
		frame.getContentPane().add(label_messangerTitle);
		
		// ============================================================ Sign In Screen Components ======
//		JLabel label_logoIcon = new JLabel("");
//		label_logoIcon.setHorizontalAlignment(SwingConstants.CENTER);
//		label_logoIcon.setIcon(new ImageIcon(LogoWindow.class.getResource("/resources/small@2x.png")));
//		label_logoIcon.setBounds(-6, 6, 100, 116);
//		frame.getContentPane().add(label_logoIcon);
//		
//		JLabel label_signInTitle = new JLabel("Sign in");
//		label_signInTitle.setFont(new Font("Lucida Grande", Font.PLAIN, 16));
//		label_signInTitle.setHorizontalAlignment(SwingConstants.CENTER);
//		label_signInTitle.setBounds(6, 19, 313, 25);
//		frame.getContentPane().add(label_signInTitle);
//		
//		textField_chatName = new JTextField();
//		textField_chatName.setBounds(83, 50, 158, 26);
//		frame.getContentPane().add(textField_chatName);
//		textField_chatName.setColumns(10);
//		
//		JLabel label_chatName = new JLabel("Chat Name");
//		label_chatName.setFont(new Font("Lucida Grande", Font.ITALIC, 11));
//		label_chatName.setBounds(86, 74, 79, 16);
//		frame.getContentPane().add(label_chatName);
		
		// ============================================================ Connect Screen Components ======
		
		
		showFrame();
	}

	/**
	 * Sets the parent JFrame to visible.
	 */
	public void showFrame() {
		frame.setVisible(true);
	}
}