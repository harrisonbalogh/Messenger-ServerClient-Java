package platform.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketException;
import java.util.concurrent.CopyOnWriteArrayList;

import platform.HXMessenger;

public class Client {
	
	private String username;
	private Boolean connected = false;
	private Socket socket;
	private Thread receiver;
	private BufferedReader in;
	private PrintWriter out;
	private CopyOnWriteArrayList<String> sentMessages; // Confirms a message was received by Server by receiving the exact same message from server.
	
	/**
	 * Receives and sends messages to a connected server.
	 * <p>
	 * Each application has access to one Client class to allow a connection to a single Server at a time.
	 * This class sends and receives messages on a socket specified by the port and IP parameters.
	 * <p>
	 * The disconnect() method in this class sends the "[user name] has disconnected" message.
	 * <p>
	 * <b>To-Do: </b>sentMessages system should be changed to a byte response instead of the same String response. 
	 * This will reduce server strain.
	 * 
	 * @param port - Port on which to connect to.
	 * @param ip - IP address of server.
	 * @param name - Name entered into the client application's name field.
	 */

	public Client(int port, String ip, String name) {
		socket = new Socket();
		sentMessages = new CopyOnWriteArrayList<String>();
		username = name;
		
		init_receiver();
		initHandshake(port, ip);
	}
	
	/**
	 * Closes the open socket as well as interrupts the server listener thread. 
	 * Also sends the server a message stating the user has disconnected.
	 */
	public void disconnect() {
		try {
			send("<font color=gray><i>" + username + " has disconnected. </i></font>");
			socket.close();
		} catch (IOException e) {
			HXMessenger.wind.playClientConnectedAnimations(false);
			System.out.println("I/O error check-2");
		}
	}
	
	/**
	 * Resolves an IP address and port to an InetSocketAddress and connects to the socket's I/O streams.
	 * Also starts up thread to begin listening to server on socket I/O stream as well as goes through the client side HXCP.
	 * @param port - Port number associated with connection.
	 * @param ip - IP address to connection.
	 */
	private void initHandshake(int port, String ip){
		try {
			// InetSocketAddress resolves the IP and port into a viable address. The connect() method will then link a socket to this address.
			socket.connect(new InetSocketAddress(ip, port), 3000);

			// I/O streams of socket. (PROBABLY GET RID OF CUSTOM INPUT/OUTPUT STREAMS I MADE)
			out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));

			// used in checking status of client.
			connected = true;

			// starts listening for data from the server. Messages are sent out using the send() method.
			receiver.start();

			// Sends all preliminary information as specified by the HarxerChatProtocol.
			new HarxerChatProtocol(this);
		} catch (IOException e) {
			HXMessenger.wind.playClientConnectedAnimations(false);
			System.out.println("I/O error check-3");
//	        	Launcher.console_window.printOut("Couldn't get I/O for the connection to " + ip);
	    } catch (IllegalArgumentException e) {
	    	HXMessenger.wind.playClientConnectedAnimations(false);
			System.out.println("I/O error check-1");
	    } finally {
	    	try {
	    		// A socket can get instantiated (and thus be open) in the constructor but not actually be connected. So if you try to use the streams 
	    		// assuming it is open, since its returning 'not closed' then you'll get a NullPointerException. This closes it just in case. The
	    		// isConnected method checks if it was able to get a connection. isConnected() doesn't change after the socket is closed but
	    		// this initHandshake method won't be called when the socket already has been opened for a time.
	    		if (!socket.isConnected())
	    			socket.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	}
	
	/**
	 * Initializes a thread that listens on the socket's BufferedReader stream and sends new messages to user interface.
	 */
	private void init_receiver() {
		receiver = new Thread() {
			public void run() {
				try {
					// readLine() returns null if the connection has been closed.
					String fromServer = "";
					while ((fromServer = in.readLine()) != null) {
						// Send to user interface if it's not a message echo being heard from same client who sent it.
						if (!sentMessages.contains(fromServer)) {
							output(fromServer);
						} else
							sentMessages.remove(fromServer);
					}
				} catch (SocketException e) {
					System.out.println("SocketException in Client. Expected interruption from closing socket.");
				} catch (IOException e) {
					System.out.println("I/O error check3");
				} finally {
					HXMessenger.wind.playClientConnectedAnimations(false);
					try {
						socket.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		};
	}
	
	/**
	 * Send a message to the server.
	 * @param data - String to be sent to server.
	 */
	public void send(String data) {
		sentMessages.add(data);
		out.println(data);
	}
	
	/**
	 * Push message to user interface.
	 * @param data - String to be pushed to user interface.
	 */
	private void output(String data) {
		HXMessenger.wind.receiveMessage(data);
	}
	public PrintWriter getOut() {
		return out;
	}
//	private boolean getConnected() {
//		return connected;
//	}
	public String getUsername() {
		return username;
	}
	public Socket getSocket() {
		return socket;
	}
}