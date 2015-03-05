package pl.porscheLambo.client.gui;

import java.awt.EventQueue;

import javax.swing.JFrame;

import java.awt.CardLayout;

import javax.swing.DefaultListModel;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;
import javax.swing.Timer;

import pl.porscheLambo.client.SocketClient;
import pl.porscheLambo.client.SocketClientConnection;
import pl.porscheLambo.client.SocketClientHandler;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JScrollPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClientGui {

	private JFrame frame;
	private JTextField textField;
	private JScrollPane scrollPane;
	private JList<String> list;
	private SocketClient socketClient;
	private String username;
	private Timer timerFriendList;
	private Thread thread;
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientGui window = new ClientGui();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ClientGui() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		try {
			frame = new JFrame();
			frame.addWindowListener(new WindowAdapter() {
				@SuppressWarnings("deprecation")
				@Override
				public void windowClosing(WindowEvent e) {
					new SocketClientConnection().sendRequest(socketClient.getSocket(), username +  ":exit");
					timerFriendList.stop();
					thread.stop();
				}
			});
			frame.setBounds(100, 100, 450, 300);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			frame.getContentPane().setLayout(new CardLayout(0, 0));
			
			JPanel Login = new JPanel();
			frame.getContentPane().add(Login, "name_151304681214550");
			Login.setLayout(null);
			
			
			JLabel lblNewLabel = new JLabel("Give your login");
			lblNewLabel.setBounds(166, 60, 143, 42);
			Login.add(lblNewLabel);
			
			textField = new JTextField();
			textField.setBounds(124, 99, 177, 42);
			Login.add(textField);
			textField.setColumns(10);
			
			JPanel FriendList = new JPanel();
			frame.getContentPane().add(FriendList, "name_151306533339149");
			FriendList.setLayout(null);
			
			scrollPane = new JScrollPane();
			scrollPane.setBounds(10, 11, 414, 239);
			FriendList.add(scrollPane);
			
			JButton btnSubmit = new JButton("Submit");
			btnSubmit.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Login.setVisible(false);
					username = textField.getText();
					socketClient = new SocketClient("localhost", 9992, username);
					socketClient.connect();
					socketClient.sendLogin(username);
					SocketClientHandler socketClientHandler = new SocketClientHandler(socketClient.getSocket());
					thread = new Thread(socketClientHandler);
					thread.start();
					
					timerFriendList = new Timer(5000, new FriendListListener());
					timerFriendList.start();
					list = new JList<String>(new FriendListListener().getListModel());
					list.addMouseListener(new MouseAdapter() {
						@Override
						public void mouseClicked(MouseEvent evt) {
							JList<String> list = (JList<String>)evt.getSource();
							if (evt.getClickCount() == 2) {
											int index = list.locationToIndex(evt.getPoint());
											System.out.println("index: " + index);
											new MessageHandler(list.getSelectedValue());
							}             
						}
					});
					scrollPane.add(list);
					scrollPane.setViewportView(list);
					
					FriendList.setVisible(true);
				}
			});

			btnSubmit.setBounds(166, 159, 89, 23);
			Login.add(btnSubmit);
		}catch(NullPointerException e) {
			e.printStackTrace();
		}
	}
	
	public void launchChatGui() {
		
	}
}