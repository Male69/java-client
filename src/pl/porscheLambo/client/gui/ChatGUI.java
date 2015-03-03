package pl.porscheLambo.client.gui;

import java.awt.EventQueue;

import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.JList;
import javax.swing.JTextArea;
import javax.swing.JButton;

import pl.porscheLambo.client.SocketClient;
import pl.porscheLambo.client.SocketClientConnection;
import pl.porscheLambo.client.SocketClientHandler;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.net.Socket;

public class ChatGUI {

	private JFrame frame;
	private String username;
	private Socket socket;
	private DefaultListModel<String> listModel = new DefaultListModel<String>();
	private JList list;
	private String message;

	/**
	 * Launch the application.
	 * @param socket 
	 */
	public static void main(ChatGUI chat) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					chat.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ChatGUI(String username, Socket socket, String message) {
		this.username = username;
		this.socket = socket;
		this.message = message;
		initialize();
	}
	
	public ChatGUI(String username, Socket socket) {
		this.username = username;
		this.socket = socket;
		initialize();
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			frame = new JFrame();
			frame.setBounds(100, 100, 450, 300);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
			frame.getContentPane().setLayout(null);
			
			JPanel panel = new JPanel();
			panel.setBounds(0, 0, 434, 261);
			frame.getContentPane().add(panel);
			panel.setLayout(null);
			
			JSeparator separator = new JSeparator();
			separator.setBounds(10, 139, 414, 80);
			panel.add(separator);
			
			list = new JList<String>(listModel);
			if(message == null) {
				
			} else {
				listModel.addElement(message);
			}
			list.setBounds(10, 11, 414, 116);
			panel.add(list);

			JTextArea textArea = new JTextArea();
			textArea.setBounds(10, 139, 414, 85);
			panel.add(textArea);
			
			JButton btnSend = new JButton("Send");
			btnSend.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent evt) {
					
					String msg = username + ":" + textArea.getText();
					System.out.println("Message po wyslaniu" + msg);
					listModel.addElement(textArea.getText());
					list = new JList<String>(listModel);

					SocketClientHandler.sendRequest(msg);
					textArea.setText(null);
				}
			});
			btnSend.setBounds(358, 230, 66, 23);
			panel.add(btnSend);
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void addTextToWindow(String message) {
		System.out.println("Dodaje wiadomosc!");
		listModel.addElement(message);
		list = new JList<String>(listModel);
	}
}
