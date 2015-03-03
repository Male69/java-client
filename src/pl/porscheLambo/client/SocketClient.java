package pl.porscheLambo.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Logger;



public class SocketClient {

	private final static Logger log = Logger.getLogger(SocketClient.class.getName()); 
	private  Socket socket;
	private String hostname;
	private int port;
	private BufferedWriter writeMsg;
	private BufferedReader serverMsg;
	private String login;
	
	public SocketClient(String hostname, int port, String login) {
		this.hostname = hostname;
		this.port = port;
		this.login = login;
	}
	
	public String getLogin() {
		return login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	public void connect() {
		try {
			socket = new Socket(hostname, port);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void sendLogin(String message) {
		try {
			writeMsg = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));
			writeMsg.write(message);
			writeMsg.newLine();
			writeMsg.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	

	public Socket getSocket() {
		return socket;
	}

	public void setSocket(Socket socket) {
		this.socket = socket;
	}	
	
	public static void main(String[] args) {
		
	}


}
