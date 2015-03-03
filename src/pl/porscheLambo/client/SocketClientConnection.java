package pl.porscheLambo.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.List;
import java.util.logging.Logger;

public class SocketClientConnection {

	private final static Logger log = Logger.getLogger(SocketClientConnection.class.getName()); 
	private BufferedWriter writeMsg;
	private BufferedReader serverMsg;
	private List<String> connections;

	
	public void sendRequest(Socket socket, String in) {
		//System.out.println("Wrrite your message(client): ");
		//Scanner input = new Scanner(System.in);
		//String in = input.nextLine();
		if(in.length() != 0) {
			try {
				writeMsg = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
				writeMsg.write(in);
				writeMsg.newLine();
				writeMsg.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public String getResponse(Socket socket) {
		String message = null;
		try {
			serverMsg = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			if(serverMsg != null) {
				message = serverMsg.readLine();
				log.info( message);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return message;
	}
	
//	public List<String> getConnections(String login) {
//		List<String> result = new ArrayList<String>();
//		String in = login + ":connections";
//		sendRequest(SocketClient.getSocket(), in);
//		//log.info(getResponse(SocketClient.getSocket()));
//		String[] messageParts = getResponse(SocketClient.getSocket()).split(":");
//		if(messageParts[0].equals("connections")) {
//			for (String elem : messageParts) {
//				result.add(elem);
//			}
//			return result;
//		}
//		
//		return null;
//		
//		
//	}
}
