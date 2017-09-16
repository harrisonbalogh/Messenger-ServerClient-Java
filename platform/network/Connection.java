package platform.network;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;

import platform.HxIOStream;
 
public class Connection {
    
	private Socket socket;
	private BufferedReader in;
	private PrintWriter out;
    private Thread clientListen;
    private HxIOStream inStream;

	/**
	 * The Server's link to a client is made through these Connection objects. 
	 * <p>
	 * A Connection has direct access
	 * to the server locally and shares messages it receives from its respective client with the server so it
	 * may distribute the message to all other Connections. The Server is the distribution center and a
	 * Connection is its representative.
	 * @param socket - Assigned by the server.
	 * @param inStream - Local link between Connection and Server as to distribute messages to all other Connections.
	 */

    public Connection(Socket socket, HxIOStream inStream) {
    	this.socket = socket;
    	// Setup I/O streams from socket.
        try {
        	out = new PrintWriter(socket.getOutputStream(), true);
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
        	System.out.println("I/O error check4");
//        	Launcher.console_window.printOut("I/O error occurred.");
        }
        // Link between server and connection. Shares messages that its client has sent.
     	this.inStream = inStream;
     	
        init_clientListener(this);
    	clientListen.start();
    }
    
    /**
     * Closes the socket associated with this Connection and Client. Closing the socket closes the input and output streams
     * associated with the socket. This will drop out the clientListen thread by returning null on the readLine() call.
     * This will do the same in the client's listener thread.
     */
    public void close() {
    	try {
    		socket.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    /**
     * Initializes the thread that listens for messages from the Client on the socket's BufferedReader input stream.
     * This thread will then send the message locally to it's parent server using an I/O stream.
     */
    private void init_clientListener(Connection self) {
    	clientListen = new Thread() {
        	public void run() {
        		// Receives all preliminary information as specified by the HarxerChatProtocol.
        		new HarxerChatProtocol(self);
        		String input = "";
        		try {
					while ((input = in.readLine()) != null) {
						inStream.write(input);
					}
        		} catch (SocketException e) {
        			System.out.println("SocketException in Connection. Expected interruption from closing socket.");
				} catch (IOException e) {
					System.out.println("IOException in Connection. 9");
				} finally {
					try {
						System.out.println("Closing server's socket.");
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
     * Sends data to its client over the socket's PrintWriter stream.
     * @param data - String to be buffered.
     */
	public void send(String data) {
		out.println(data);
	}
	public HxIOStream getInStream() {
		return inStream;
	}
    public BufferedReader getIn() {
    	return in;
    }
    public Socket getSocket() {
    	return socket;
    }
}