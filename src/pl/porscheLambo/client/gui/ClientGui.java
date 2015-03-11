package pl.porscheLambo.client.gui;

import java.awt.EventQueue;

import javax.imageio.ImageIO;
import javax.swing.JFrame;

import java.awt.CardLayout;

import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.JList;
import javax.swing.JScrollPane;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;

import java.awt.Component;
import java.awt.image.BufferedImage;

public class ClientGui {
	
	private final static Logger log = Logger.getLogger(ClientGui.class.getName());
	private JFrame frame;
	private JTextField textField;
	private JScrollPane scrollPane;
	private JList<String> list;
	private SocketClient socketClient;
	private String username;
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
				@Override
				public void windowClosing(WindowEvent e) {
					SocketClientConnection socketClientConnection = new SocketClientConnection();
					try {
						socketClientConnection.sendRequest(socketClient.getSocket(), username +  ":exit");
							thread.join(2000);
					} catch(NullPointerException | InterruptedException evt) {
						log.info("Window is closed");
					}
					
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
			
			scrollPane = new JScrollPane(list);
			scrollPane.setBounds(11, 11, 414, 239);
			FriendList.add(scrollPane);
			
			DefaultListModel<String> listModel = new DefaultListModel<String>();
			listModel.addElement("New");
			listModel.addElement("Edit");
			listModel.addElement("Open");
			
			JPopupMenu popupMenu = new JPopupMenu();
			addPopup(scrollPane, popupMenu);
			popupMenu.setBounds(0, 0, 200, 50);
			
			JList<String> list_1 = new JList<String>(listModel);
			popupMenu.add(list_1);
			
			JMenuBar menuBar = new JMenuBar();
			scrollPane.setColumnHeaderView(menuBar);
			
			JButton btnFile = new JButton("File");
			btnFile.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					popupMenu.show(btnFile, 0, 26);
				}
			});
			menuBar.add(btnFile);
			
			JButton btnSetting = new JButton("Setting");
			menuBar.add(btnSetting);
			
			JButton btnHelp = new JButton("Help");
			menuBar.add(btnHelp);
			
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
				
					FriendListListener friendListListener = new FriendListListener();
					list = new JList<String>(friendListListener.getListModel());
					list.setCellRenderer(new FriendListRenderer());

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
	
	public class FriendListRenderer extends DefaultListCellRenderer  {

		
		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;

		public Component getListCellRendererComponent (
				JList<?>list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
			  JLabel label = (JLabel) super.getListCellRendererComponent(
	                    list, value, index, isSelected, cellHasFocus);
	            label.setIcon(new ImageIcon("D:/Temp/Do chatu/unknownAvatar.jpg"));
	            label.setHorizontalTextPosition(JLabel.RIGHT);
			
			return label;			
		} 	
	}
	
	public void launchChatGui() {
		
	}
	private static void addPopup(Component component, final JPopupMenu popup) {
		component.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			public void mouseReleased(MouseEvent e) {
				if (e.isPopupTrigger()) {
					showMenu(e);
				}
			}
			private void showMenu(MouseEvent e) {
				popup.show(e.getComponent(), e.getX(), e.getY());
			}
		});
	}
}