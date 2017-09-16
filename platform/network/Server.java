package platform.network;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.SocketException;
import java.util.concurrent.CopyOnWriteArrayList;

import platform.HxIOStream;
import platform.HXMessenger;

public class Server {
	
    private int port;
    private boolean running = false;
    private ServerSocket serverSocket;
    private Thread receiverThread;
    private Thread connectionDelegateThread;
    private Thread messageDelegateThread;
    private CopyOnWriteArrayList<Connection> openConnections = new CopyOnWriteArrayList<Connection>();
    private HxIOStream inStream = new HxIOStream();
    
	/**
	 * Opens a server on the given port. The Server will then wait for a socket
	 * before creating a ServerConnection to a Client.
	 * <p>
	 * The stop() method is responsible for the "Server has stopped" message.
	 * @param port - The port to open the server on.
	 */
    public Server(int port){
		this.port = port;
		init_connectionReceiver();
		init_messageDelegate();
		init_connectionDelegate();
		receiverThread.start();
		messageDelegateThread.start();
		connectionDelegateThread.start();
	}
    
    /**
     * Sets the running boolean to false and stops the two listening threads by closing 
     * the serverSocket (for the accept() method)
     * and 
     * the inStream (for the readLine() method).
     */
    public void stop() {
    	running = false;
    	try {
    		for (Connection c : openConnections) {
    			if (!c.getSocket().isClosed()) {
    				c.send("<font color=gray><i>Server has stopped.</i></font>");
    			}
    		}
    		// Stops the receiverThread process
			serverSocket.close();
			// Stops the delegateThread process
			inStream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
	/**
	 * Initializes a thread that listens for a socket connection from a client.
	 * <p>
	 * A received socket will be handed over to a new Connection and will begin
	 * listening to the client from whence the socket originated.
	 */
	private void init_connectionReceiver() {
		running = true;
		receiverThread = new Thread() {
        	public void run() {
        		try (ServerSocket tempServerSocket = new ServerSocket(port)) { 
        			serverSocket = tempServerSocket;
		            while (running) {
		            	// Hangs on the accept() method until a connection is made.
		                openConnections.add(new Connection(serverSocket.accept(), inStream));
		            }
        		} catch (SocketException e) {
        			System.out.println("SocketException in Server. Should be ok. Expected interruption - due to accept() method from closing socket.");
		        } catch (IOException e) {
		        	System.out.println("IOException in Server. 8");
//		        	Launcher.console_window.printOut("Could not listen on port " + port);
		        } catch (IllegalArgumentException e) {
		        	System.out.println("Error in server. 3");
//		        	Launcher.console_window.printOut("Could not listen on port " + port);
		        } finally {
		        	HXMessenger.wind.playServerRunningAnimations(false);
		        	running = false;
		        	// Stops the clientListen process in Connection
					for (Connection c : openConnections)
						c.close();
		        }
        	}
        };
	}
	
	/**
	 * Initializes a thread that listens for messages a Connection has relayed from a Client.
	 * <p>
	 * A received message will then get sent to iall connections currently open with the server.
	 */
	private void init_messageDelegate() {
		messageDelegateThread = new Thread() {
			public void run() {
				while (running) {
					String message = "";
					// Hangs on the readLine() method
					while ((message = inStream.readLine()) != null) {
						for (Connection c : openConnections)
							c.send(message);
					}
				}
			}
		};
	}
	
	/**
	 * Initializes a thread that continuously checks the connections list to ensure none have been closed. 
	 * If it finds one, it removes it from the list.
	 */
	private void init_connectionDelegate() {
		connectionDelegateThread = new Thread() {
			public void run() {
				long CHECK_INTERVAL = 500;
				while (running) {
					try {
						Thread.sleep(CHECK_INTERVAL);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// If a socket has been closed then remove it from openConnections list. Releasing the Connection object.
					for (Connection c : openConnections)
		    			if (c.getSocket().isClosed()) {
							openConnections.remove(c);
		    			}
				}
				
			}
		};
	}
}