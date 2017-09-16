//package platform;
//
//import platform.network.Client;
//import platform.network.Server;
//
//public class CommandsTransposer {
//	
//	private static final int DEFAULT_PORT = 10004;
//	private static final String DEFAULT_IP = "172.250.15.59";
//	public static final String[] COMMANDS_LIBRARY = {"help", "ping", "exit", "last_connection", "start", "stop", "check_messages"};
//
//	public CommandsTransposer(ConsoleInputStream input, ConsoleOutputStream output){
//		listenForInputStream(input, output);
//	}
//	
//	private static String transposeCommand(String s) {
//		
//		String cmd_base = s;
//		String cmd_parameter1 = "";
//		String cmd_parameter2 = "";
//		
//		if (s.contains(" ")){
//			cmd_base = s.substring(0, s.indexOf(" ", 0));
//			cmd_parameter1 = s.substring(s.indexOf(" ", 0) + 1);
//			if (cmd_parameter1.contains(" ")){
//				cmd_parameter2 = cmd_parameter1.substring(s.indexOf(" ", 0));
//				cmd_parameter1 = cmd_parameter1.substring(0, s.indexOf(" ", 0) - 1);
//			}
//		}
//		
//		System.out.println("Parsed parameters // param1: '" + cmd_parameter1 + "', param2: '" + cmd_parameter2 + "'.");
//		
////		if (Launcher.console_window.userName == ""){
////			Launcher.console_window.userName = s;
//			return ("Welcome, " + s + ".");
//		} else if (Client.connected) {
//			return (s);
//		} else if (cmd_base.equals(COMMANDS_LIBRARY[0])) { //help
//			String sBuilder = "Available Commands:";
//			for (int x = 1; x < COMMANDS_LIBRARY.length; x++) {
//				sBuilder += "\n     " + COMMANDS_LIBRARY[x];
//			}
//			return sBuilder;
//		} else if (cmd_base.equals(COMMANDS_LIBRARY[1])) { //ping
//			if (!cmd_parameter1.equals("")){
//				if (!cmd_parameter2.equals("")){
//					gui.ConsoleWindow.clientMain = new Client(Integer.parseInt(cmd_parameter1), cmd_parameter2);
//					return "Connecting to server on port " + cmd_parameter1 + ". Computer: " + cmd_parameter2;
//				} else {
//					gui.ConsoleWindow.clientMain = new Client(Integer.parseInt(cmd_parameter1), DEFAULT_IP);
//					return "Connecting to server on port " + cmd_parameter1 + ". Computer: " + DEFAULT_IP + " (default)";
//				}
//			}
//			gui.ConsoleWindow.clientMain = new Client(DEFAULT_PORT, DEFAULT_IP);
//			return "Connecting to server on port " + DEFAULT_PORT + " (default). Computer: " + DEFAULT_IP + " (default)";
//		} else if (cmd_base.equals(COMMANDS_LIBRARY[2])) { //exit
//			System.exit(0);
//			return "Exiting...";
//		} else if (cmd_base.equals(COMMANDS_LIBRARY[3])) { //last_connection
//			return "Not yet implemented last_connection functionality.";
//		} else if (cmd_base.equals(COMMANDS_LIBRARY[4])) { //start
//			if (!cmd_parameter1.equals("")){ 
//				gui.ConsoleWindow.serverMain = new Server(Integer.parseInt(cmd_parameter1));
////				Launcher.console_window.serverRunning(true);
//				return "Starting server on port " + cmd_parameter1 + ".";
//			}
//			gui.ConsoleWindow.serverMain = new Server(DEFAULT_PORT);
////			Launcher.console_window.serverRunning(true);
//			return "Starting server on port " + DEFAULT_PORT + " (default).";
//		} else if (s.equals(COMMANDS_LIBRARY[5])) { //stop
//			if (gui.ConsoleWindow.serverMain == null)
//				return "Server not running.";
//			gui.ConsoleWindow.serverMain.stop();
////			Launcher.console_window.serverRunning(false);
//			
//			return "Stopping server.";
//		} else if (s.equals(COMMANDS_LIBRARY[6])) { //check_messages
//			if (gui.ConsoleWindow.serverMain == null) {
////				if (!gui.ConsoleWindow.serverMain.running)
////					return "Server has to be running on this machine.";
//			}
////			gui.ConsoleWindow.serverMain.getMessages();
//			return "Retrieving messages.";
//		}
//		return "Invalid command, '" + s + "'.";
//	}
//	
//	private void listenForInputStream(ConsoleInputStream input, ConsoleOutputStream output){
//		new Thread() {
//        	public void run() {
//        		String s = input.waitForLine();
//        		output.write(transposeCommand(s));
//        		listenForInputStream(input, output);
//        	}
//		}.start();
//	}
//	
//}