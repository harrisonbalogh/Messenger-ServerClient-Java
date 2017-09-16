package gui;

import javax.swing.JFrame;
import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.time.LocalDateTime;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JRadioButton;
import java.awt.GridBagConstraints;
import javax.swing.ImageIcon;

public class ConsoleWindow {
	
	private JFrame frmHarxerServer;
	private JRadioButton rdbtnLineWrap;
	private JRadioButton rdbtnWordWrap;
	private JTextField textField;
	private JTextArea textArea;
	private JLabel serverImage;
	
	/**
	 * Create the application.
	 */
	public ConsoleWindow() {
		initialize();
		
//		serverWindow = new ServerListWindow();
//		serverWindow.setLocation(frmHarxerServer.getX() + frmHarxerServer.getWidth(), frmHarxerServer.getY());
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frmHarxerServer = new JFrame();
		frmHarxerServer.setTitle("Harxer Server");
		frmHarxerServer.getContentPane().setBackground(Color.GRAY);
		frmHarxerServer.getContentPane().setLayout(new BorderLayout(0, 0));
		frmHarxerServer.setMinimumSize(new Dimension(500, 300));
		
		JPanel panel = new JPanel();
		panel.setBackground(Color.LIGHT_GRAY);
		frmHarxerServer.getContentPane().add(panel, BorderLayout.NORTH);
		
		JPanel panel_1 = new JPanel();
		panel_1.setBackground(Color.LIGHT_GRAY);
		frmHarxerServer.getContentPane().add(panel_1, BorderLayout.WEST);
		
		JPanel panel_2 = new JPanel();
		panel_2.setBackground(Color.LIGHT_GRAY);
		frmHarxerServer.getContentPane().add(panel_2, BorderLayout.SOUTH);
		
		rdbtnLineWrap = new JRadioButton("Line Wrap");
		rdbtnLineWrap.setToolTipText("Determines if text will try and fit in the horizontal space of the window.");
		rdbtnLineWrap.setSelected(true);
		rdbtnLineWrap.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					textArea.setLineWrap(true);
					rdbtnWordWrap.setEnabled(true);
				} else {
					textArea.setLineWrap(false);
					rdbtnWordWrap.setEnabled(false);
				}
				
			}
		});
		
		JPanel panel_6 = new JPanel();
		panel_6.setOpaque(false);
		panel_2.add(panel_6);
		
		serverImage = new JLabel("");
		Image img = new ImageIcon(ConsoleWindow.class.getResource("/resources/server_status_offline.png")).getImage();
		Image dimg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
		serverImage.setIcon(new ImageIcon(dimg));
		panel_6.add(serverImage);
		
		JLabel lblNewLabel_1 = new JLabel("Server Open");
		panel_6.add(lblNewLabel_1);
		
		JRadioButton rdbtnRequireLogin = new JRadioButton("Require Login");
		rdbtnRequireLogin.setToolTipText("Sets whether the user must enter credentials on next startup.");
		panel_2.add(rdbtnRequireLogin);
		panel_2.add(rdbtnLineWrap);
		
		rdbtnWordWrap = new JRadioButton("Word Wrap");
		rdbtnWordWrap.setToolTipText("If Line Wrap is on, this option determines if the line wraps at the next word or next character.");
		rdbtnWordWrap.setSelected(true);
		rdbtnWordWrap.addItemListener(new ItemListener() {
			@Override
			public void itemStateChanged(ItemEvent e) {
				if (e.getStateChange() == ItemEvent.SELECTED) {
					textArea.setWrapStyleWord(true);
				} else {
					textArea.setWrapStyleWord(false);
				}
				
			}
		});
		panel_2.add(rdbtnWordWrap);
		
		JPanel panel_3 = new JPanel();
		panel_3.setBackground(Color.LIGHT_GRAY);
		frmHarxerServer.getContentPane().add(panel_3, BorderLayout.EAST);
		
		JPanel panel_4 = new JPanel();
		panel_4.setBackground(Color.WHITE);
		panel_4.setForeground(Color.WHITE);
		frmHarxerServer.getContentPane().add(panel_4, BorderLayout.CENTER);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		panel_4.add(panel_5, BorderLayout.SOUTH);
		GridBagLayout gbl_panel_5 = new GridBagLayout();
		gbl_panel_5.columnWidths = new int[]{0, 0};
		gbl_panel_5.rowHeights = new int[]{0, 0, 0};
		gbl_panel_5.columnWeights = new double[]{1.0, Double.MIN_VALUE};
		gbl_panel_5.rowWeights = new double[]{1.0, 0.0, Double.MIN_VALUE};
		panel_5.setLayout(gbl_panel_5);
		
		textField = new JTextField();
//		listenForOutput();
		textField.addKeyListener(new KeyListener(){
			@Override
			public void keyTyped(KeyEvent e) {
			}
			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyChar() == KeyEvent.VK_ENTER) && (textField.getText() != "")) {
					textField.setText("");
				}
			}
			@Override
			public void keyReleased(KeyEvent e) { }
		});
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 0;
		gbc_textField.gridy = 1;
		panel_5.add(textField, gbc_textField);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_4.add(scrollPane_2, BorderLayout.CENTER);
		
		textArea = new JTextArea();
		textArea.setLineWrap(true);
		textArea.setWrapStyleWord(true);
		textArea.setEditable(false);
		scrollPane_2.setViewportView(textArea);
		
		JLabel lblNewLabel = new JLabel("Console ");
		lblNewLabel.setFont(new Font("Lucida Grande", Font.ITALIC, 12));
		lblNewLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		panel_4.add(lblNewLabel, BorderLayout.NORTH);
		frmHarxerServer.setBounds(100, 100, 526, 300);
		frmHarxerServer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		printOut("User: ");
	}
	
	public void showFrame() {
		frmHarxerServer.setVisible(true);
//		serverWindow.setVisible(true);
	}
	 
	/**
	 * Print a string to the console.
	 * <p>
	 * Should only used by the ConsoleOutputStream class. Unless attempting to print error messages.
	 * @param s - The string to be printed in the console text area.
	 */
	public void printOut(String s) {
		textArea.append("> [" + LocalDateTime.now() + "] " + s + " \n");
		textArea.setCaretPosition(textArea.getDocument().getLength());
	}
	
	public void serverRunning(boolean b) {
		if (b) {
			Image img = new ImageIcon(ConsoleWindow.class.getResource("/resources/server_status_online.png")).getImage();
			Image dimg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			serverImage.setIcon(new ImageIcon(dimg));
		} else {
			Image img = new ImageIcon(ConsoleWindow.class.getResource("/resources/server_status_offline.png")).getImage();
			Image dimg = img.getScaledInstance(20, 20, Image.SCALE_SMOOTH);
			serverImage.setIcon(new ImageIcon(dimg));
		}
	}
	
//	public void listenForOutput(){
//		new Thread() {
//        	public void run() {
//        		String temp = outputBuffer.textAreaListen();
//        		if (!Client.connected) {
//        			printOut(temp);
//        			listenForOutput();
//        		}
//        	}
//		}.start();
//	}
}