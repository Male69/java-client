package pl.porscheLambo.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.logging.Logger;

import pl.porscheLambo.client.SocketClient;
import pl.porscheLambo.client.SocketClientConnection;
import pl.porscheLambo.client.SocketClientHandler;

public class MessageHandler {
	
	private String message;
	private String username;
	private static HashMap<String, ChatGUI> currentConversations = new HashMap<String, ChatGUI>();
	private final static Logger log = Logger.getLogger(MessageHandler.class.getName()); 
	private ChatGUI chat;
	
	public MessageHandler(String message) {
		

		
		//System.out.println("Value of the checkIfConversationExists" + checkConv);
		if(message != null) {
			splitter(message);
			System.out.println("message:" + message + "username: " + username);
		}
		

		if(checkIfConversationExists(username) == false || message.isEmpty()) {
			System.out.println("Wchodze w warunek");

			launchChatWindow();
			addConversation();
		}else {
			log.info("before addTextToWindow" + message);

			currentConversations.get(username).addTextToWindow(this.message);
		}
	}
	
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public HashMap<String, ChatGUI> getCurrentConversations() {
		return currentConversations;
	}

	public void setCurrentConversations(HashMap<String, ChatGUI> currentConversations) {
		this.currentConversations = currentConversations;
	}

	public String splitter(String msg) {
		String[] messageParts = msg.split(":");

		System.out.println(msg);
		
		if(messageParts.length == 1) {
			setUsername(messageParts[0]);
		} else {
			setUsername(messageParts[0]);
			setMessage(messageParts[1]);
		}
		
		return message;
	}
	
	public boolean checkIfConversationExists(String username) {
		log.info(username);
		
		try {
			for(Entry<String, ChatGUI> elem : currentConversations.entrySet()) {
				if(elem.getKey().equals(username)) {
					System.out.println("Jestem tu");
					return true;
				}
			}
		} catch (NullPointerException e) {
			System.out.println("tablica jest pusta");
		}

		return false;
	}
	
	public void launchChatWindow() {
		chat = new ChatGUI(username, SocketClientHandler.getSocket(), message);
		
		ChatGUI.main(chat);
	}
	
	public void addConversation() { 
		System.out.println("Size of the current conversations" + currentConversations.size());
		
		currentConversations.put(username, chat);
	}
}

