package platform;

import java.awt.EventQueue;

import gui.MessangerWindow;

public class HXMessenger {
	
	public static final double HXServerProtocolVersion = 1.12;
	
	/**
	 * Implements the primary window (WindowPrimary). Launches application.
	 */
	
//	public static ConsoleWindow console_window;
	public static MessangerWindow wind;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
//					windowMain = new ConsoleWindow();
//					windowMain.showFrame();

					wind = new MessangerWindow();
					wind.showFrame();
					ShutdownHook hook = new ShutdownHook();
					hook.attachShutDownHook(wind);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}