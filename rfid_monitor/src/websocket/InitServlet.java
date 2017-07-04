package websocket;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;



import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import xjtu.mes.manager.EventManager;
import xjtu.mes.manager.MachManager;
import xjtu.mes.manager.WorkpieceManager;
import xjtu.mes.manager.WorkpieceProcessManager;
import xjtu.mes.model.Event;
import xjtu.mes.model.Machine;
import xjtu.mes.model.Workpiece;
import xjtu.mes.model.WorkpieceProcess;
import xjtu.mes.util.Log4JManager;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
@ServerEndpoint("/websocket")
public class InitServlet {
	
	public InitServlet() {
		super();
		count++;
		System.out.println(count);
	}
	private static  int  count =0;	
	private ServerThread serverThread = null;
	
  @OnMessage
  public void onMessage(String message, Session session)throws IOException, InterruptedException {
	
	 System.out.println("Received: " + message);
	Log4JManager.getInstance().writeLog("Received: " + message);
	if(!serverThread.isAlive()){
		Log4JManager.getInstance().writeLog("线程死了，开启一个新的 ");
		serverThread = null;
		serverThread = new ServerThread();
		 serverThread.setNewOne("");
		 serverThread.setOldOne("");
		 serverThread.setFlag(true);
		serverThread.start();
	}
	serverThread.setWorkpieceId(message);
	serverThread.setSession(session);
	
	
  }
  @OnOpen
  public void onOpen()  {
    System.out.println("Client connected");
    Log4JManager.getInstance().writeLog("Client connected,实例化线程，并开始");
    serverThread = new ServerThread();
    serverThread.setNewOne("");
    serverThread.setOldOne("");
    serverThread.setFlag(true);
    serverThread.start();

  }
 
  @OnClose
  public void onClose() {
	serverThread.setNewOne("");
	serverThread.setOldOne("");
	serverThread.setFlag(false);
	serverThread.setSession(null);	
	serverThread =null;
	 Log4JManager.getInstance().writeLog("Connection closed，销毁线程，并清除实例");
    System.out.println("Connection closed");
    count++;
	System.out.println(count);
  }
  
	
	
	
	
	
	
}
