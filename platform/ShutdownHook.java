package platform;

import gui.MessangerWindow;

public class ShutdownHook {
	public void attachShutDownHook(MessangerWindow w) {
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				if (w.getClient() != null)
					w.getClient().disconnect();
				if (w.getServer() != null)
					w.getServer().stop();
			}
		});
	}
	
}
