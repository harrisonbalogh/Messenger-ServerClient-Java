package gui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;

import gui.components.RetinaIcon;
import hxUIEngine.HxUIClock;
import hxUIEngine.HxUIFader;
import hxUIEngine.HxUIMover;
import hxUIEngine.HxUIResizer;

import java.awt.Font;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JTextField;
import java.awt.Color;
import javax.swing.JSeparator;
import javax.swing.JPanel;

public class SignInWindow {

	private JFrame frame;
	
	private JTextField textField_nameField;
	private JLabel label_nameField;
	private JLabel icon_logo;
	private JLabel label_logo;
	private JSeparator separator_nameField;
	private JPanel clipPanel_logo;
	private JPanel clipPanel_nameField;
	
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					SignInWindow window = new SignInWindow();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public SignInWindow() {
		initialize();
		
		frame.setVisible(true);
		
		new HxUIClock();
		
		new Thread() {
			public void run() {
				try {Thread.sleep(3000);} catch (InterruptedException e) {}
				new HxUIMover(clipPanel_nameField, -202, 0, 0.4);
				new HxUIMover(icon_logo, -202, 0, 0.4);
				new HxUIMover(clipPanel_logo, -202, 0, 0.4);
				try {Thread.sleep(250);} catch (InterruptedException e) {}
				textField_nameField.setVisible(true);
				textField_nameField.setEnabled(true);
				textField_nameField.requestFocus();
			}
		}.start();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(Color.WHITE);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(new Dimension(280, 80));
		frame.setMinimumSize(frame.getSize());
		frame.getContentPane().setLayout(null);
		frame.setResizable(false);
		
		icon_logo = new JLabel("");
		icon_logo.setHorizontalAlignment(SwingConstants.CENTER);
		icon_logo.setIcon(new RetinaIcon(LogoWindow.class.getResource("/resources/webIcon@2x.png")));
		icon_logo.setBounds(202, 0, 78, 57);
		frame.getContentPane().add(icon_logo);
		
		textField_nameField = new JTextField();
		textField_nameField.setFont(new Font("Arial", Font.BOLD, 15));
		textField_nameField.setEnabled(false);
		textField_nameField.setHorizontalAlignment(SwingConstants.CENTER);
		textField_nameField.setBorder(null);
		textField_nameField.setBackground(new Color(100, 100, 100, 0));
		textField_nameField.setBounds(66, 10, 148, 26);
		frame.getContentPane().add(textField_nameField);
		textField_nameField.setColumns(10);
		textField_nameField.addKeyListener(new KeyListener() {
			@Override
			public void keyTyped(KeyEvent e) { }
			@Override
			public void keyPressed(KeyEvent e) {
				if ((e.getKeyChar() == KeyEvent.VK_ENTER) && (textField_nameField.getText() != "")) {
					new Thread() {
						public void run() {
							textField_nameField.setEditable(false);
							textField_nameField.setHighlighter(null);
							frame.getContentPane().remove(label_logo);
							label_logo = null;
							frame.requestFocus();
							new HxUIFader(separator_nameField, 0, 0.4);
							new HxUIFader(label_nameField, 0, 0.4);
							new HxUIResizer(frame, 0, 400, 0.4);
							frame.setResizable(true);
						}
					}.start();
				}
			}
			@Override
			public void keyReleased(KeyEvent e) { }
		});
		
		clipPanel_logo = new JPanel();
		clipPanel_logo.setBorder(null);
		clipPanel_logo.setBackground(Color.WHITE);
		clipPanel_logo.setBounds(238, 4, 199, 26);
		frame.getContentPane().add(clipPanel_logo);
		
		label_logo = new JLabel("H X M e s s e n g e r");
		label_logo.setForeground(Color.DARK_GRAY);
		label_logo.setFont(new Font("Arial", Font.PLAIN, 14));
		label_logo.setHorizontalAlignment(SwingConstants.CENTER);
		label_logo.setBounds(0, 0, 276, 41);
		frame.getContentPane().add(label_logo);
		
		clipPanel_nameField = new JPanel();
		clipPanel_nameField.setBorder(null);
		clipPanel_nameField.setBackground(Color.WHITE);
		clipPanel_nameField.setBounds(42, 30, 199, 20);
		frame.getContentPane().add(clipPanel_nameField);
		
		separator_nameField = new JSeparator();
		separator_nameField.setForeground(Color.DARK_GRAY);
		separator_nameField.setBounds(66, 28, 148, 12);
		frame.getContentPane().add(separator_nameField);
		
		label_nameField = new JLabel("Chat Name");
		label_nameField.setFont(new Font("Arial", Font.ITALIC, 11));
		label_nameField.setHorizontalAlignment(SwingConstants.CENTER);
		label_nameField.setForeground(Color.LIGHT_GRAY);
		label_nameField.setBounds(0, 34, 280, 16);
		frame.getContentPane().add(label_nameField);
	}
}
