package pl.porscheLambo.client.gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.swing.DefaultListModel;

import pl.porscheLambo.client.SocketClientHandler;

public class FriendListListener implements ActionListener {
  
  private final static Logger log = Logger.getLogger(FriendListListener.class.getName());
  private static DefaultListModel<String> listModel = new DefaultListModel<String>();
  private String message;
  
  public FriendListListener(String message) {
    this.message = message;
    setListModel(getFriendList(message));
  }
  
  public FriendListListener() {

  }
  
  @Override
  public void actionPerformed(ActionEvent e) {
    sendRequest();
    System.out.println("Size of the list model" + listModel.getSize());
  }

  public List <String> getFriendList(String message) {
    List <String> result = new ArrayList<String>();
    String[] messageParts = message.split(":");

    log.info(message);

    for (int i = 1; i < messageParts.length ; i++) {
      result.add(messageParts[i]);
    }

    log.info(result.get(0));
    return result;
  }
  
  public void sendRequest() {
    String request = "connections";
    SocketClientHandler.sendRequest(request);
  }
  
  public DefaultListModel<String> getListModel() {
    return listModel;
  }

  public static void setListModel(List<String> list) {
    FriendListListener.listModel.clear();
    
    for (int i = 0; i < list.size() ; i++) {
      listModel.addElement(list.get(i));
    }
  }
}
