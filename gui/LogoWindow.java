package gui;

import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.SwingConstants;
import java.awt.Font;

public class LogoWindow {

	private JFrame frame;

	/**
	 * Create the application.
	 */
	public LogoWindow() {
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
	}
}
