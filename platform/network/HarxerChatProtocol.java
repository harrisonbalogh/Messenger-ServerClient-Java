package platform.network;

import java.io.IOException;

import platform.HXMessenger;

public class HarxerChatProtocol {
	
	/**
	 * Creates and runs through the protocol of the Harxer chat application.
	 * <p>
	 * If passed a Client object then protocol adheres to the Client side of instructions.
	 * <p>
	 * This protocol is responsible for sending the "[user name] has connected" message.
	 * @param c - Client object that is following protocol.
	 */
	public HarxerChatProtocol(Client c) {
		// 1
		c.getOut().println("<font color=gray><i>" + c.getUsername() + " has connected from a " + System.getProperty("os.name") + ".</i></font>");
		// 2
		c.getOut().println("" + HXMessenger.HXServerProtocolVersion);
	}
	
	/**
	 * Creates and runs through the protocol of the Harxer chat application.
	 * <p>
	 * If passed a Connection object then protocol adheres to the Server side of instructions.
	 * @param c - Connection object that is following protocol.
	 */
	public HarxerChatProtocol(Connection c) {
		try {
			// 1
			String data = c.getIn().readLine();
			 c.getInStream().write(data);
			// 2
			String version = c.getIn().readLine();
			if (!version.equals("" + HXMessenger.HXServerProtocolVersion)) {
				c.send("Your client version number (" + version + ") does not match "
						+ "the server's version number (" + HXMessenger.HXServerProtocolVersion + "). Please update else errors may arise .");
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}