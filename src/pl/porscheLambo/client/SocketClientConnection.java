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
}
