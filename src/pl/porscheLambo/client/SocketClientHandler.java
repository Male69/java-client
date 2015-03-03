package pl.porscheLambo.client;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Logger;

import pl.porscheLambo.client.gui.FriendListListener;
import pl.porscheLambo.client.gui.MessageHandler;

public class SocketClientHandler implements Runnable {
  
  private static Socket socket;
  private BufferedReader serverMsg;
  private final static Logger log = Logger.getLogger(SocketClientHandler.class.getName());
  private String message;
  private String username;
  private static List<String> conversations;

  public SocketClientHandler(Socket socket) {
    SocketClientHandler.socket = socket;  
  }
  
  public static Socket getSocket() {
    return socket;
  }

  public static void setSocket(Socket socket) {
    SocketClientHandler.socket = socket;
  }
  
  public String getUsername() {
    return username;
  }

  public void setUsername(String username) {
    this.username = username;
  }
  
  @Override
  public void run() {
    while(true) {
      message = getResponse();

      checkKindOfMsg(getFirstPartOfTheMsg(message));
    }
  }

  public String getResponse() {
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
  
  public String getFirstPartOfTheMsg(String msg) {
    String[] msgSplitter = msg.split(":");

    return msgSplitter[0];
  }

  public void checkKindOfMsg(String firstPartOfTheMsg) {
    if(firstPartOfTheMsg.equals("connections")) {
      new FriendListListener(message);
    } else {
      new MessageHandler(message);
    }
  }
  
  public static void sendRequest(String request ) {
    if(request.length() != 0) {
      try {
        BufferedWriter writeMsg = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream()));

        writeMsg.write(request);
        writeMsg.newLine();
        writeMsg.flush();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
