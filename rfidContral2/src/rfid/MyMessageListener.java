package rfid;

import java.io.IOException;
import java.net.InetAddress;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

import com.alien.enterpriseRFID.notify.Message;
import com.alien.enterpriseRFID.notify.MessageListener;
import com.alien.enterpriseRFID.notify.MessageListenerService;
import com.alien.enterpriseRFID.reader.AlienClass1Reader;
import com.alien.enterpriseRFID.tags.Tag;


public class MyMessageListener implements MessageListener {
	private MessageListenerService service = null;
	private Tag[] TagList =null;
	private String ip;
	private int port=0 ;
	private EventDeal eventDeal =null;
	//这两个常亮是用来监听数据处理部分的循环是否卡到某处了，且没报错
	private long newN =0;
	private long oldN =0;
	
	private int count =0;
	
	//记录发送给eventDeal数据的次数
	private long num=0;
	
	private int[] N =new int[50];
	private Map<String,Tag[]> map=new HashMap<String,Tag[]>();
	public MyMessageListener()  throws Exception{
		if(this.port == 0){
			 System.out.println(" 线程没有定义监听的端口  !!!!");
			 System.exit(0);
		 }
		
		 service = new MessageListenerService(this.port);
		 service.setMessageListener(this);
		 if(this.ip == null){
			 System.out.println(" ip is null !!!!");
			 System.exit(0);
		 }
		 AlienClass1Reader reader = new AlienClass1Reader( this.ip, 23);
		  reader.open();
		  System.out.println("Configuring Reader");
		  //好像和message的不一样
		 /* reader.setReaderName("168");
		  reader.setHostname("168-1");*/

		  // Set up Notification.
		  // Use this host's IPAddress, and the port number that the service is listening on.
		  // getHostAddress() may find a wrong (wireless) Ethernet interface, so you may
		  // need to substitute your computers IP address manually.
		  reader.setNotifyAddress(InetAddress.getLocalHost().getHostAddress(), service.getListenerPort());
		  reader.setNotifyFormat(AlienClass1Reader.XML_FORMAT); // Make sure service can decode it.
		  reader.setNotifyTrigger("TrueFalse"); // Notify whether there's a tag or not
		  reader.setNotifyMode(AlienClass1Reader.ON);

		  // Set up AutoMode
		  reader.autoModeReset();
		  reader.setAutoStopTimer(50); // Read for 1 second
		  reader.setAutoMode(AlienClass1Reader.ON);

		  // Close the connection and spin while messages arrive
		  reader.close();
	}
	public MyMessageListener(String ip,int  port)  throws Exception{
		this.port =port;
		if(this.port == 0){
			 System.out.println(" 线程没有定义监听的端口  !!!!");
			 System.exit(0);
		 }
		service = new MessageListenerService(this.port);
		this.ip =ip;
		 service.setMessageListener(this);
		 if(this.ip == null){
			 System.out.println("no input ip!!!!");
			 System.exit(0);
		 }
		 AlienClass1Reader reader = new AlienClass1Reader( this.ip, 23);
		  reader.open();
		  System.out.println("Configuring Reader");
		  //好像和message的不一样
		/*  reader.setReaderName("168");
		  reader.setHostname("168-1");*/
		  // Set up Notification.
		  // Use this host's IPAddress, and the port number that the service is listening on.
		  // getHostAddress() may find a wrong (wireless) Ethernet interface, so you may
		  // need to substitute your computers IP address manually.
		  reader.setNotifyAddress(InetAddress.getLocalHost().getHostAddress(), service.getListenerPort());
		  reader.setNotifyFormat(AlienClass1Reader.XML_FORMAT); // Make sure service can decode it.
		  reader.setNotifyTrigger("TrueFalse"); // Notify whether there's a tag or not
		  reader.setNotifyMode(AlienClass1Reader.ON);

		  // Set up AutoMode
		  reader.autoModeReset();
		  reader.setAutoStopTimer(50); // Read for 1 second
		  reader.setAutoMode(AlienClass1Reader.ON);

		  // Close the connection and spin while messages arrive
		  reader.close();
		  //实例线程处理业务类
		  eventDeal = new EventDeal();
		//  eventDeal.pourDateToMem();
		  eventDeal.start();
		  System.out.println("启动数据分析");
		 
	}

	public void startServer(){
		try {
			service.startService();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void stopServer(){
		
			service.stopService();
		
	}
	public boolean isRunning(){
		
		return service.isRunning()?true:false;
	}
	

	@Override
	public void messageReceived(Message message) {
		// TODO Auto-generated method stub
		//eventDeal.pourDateToMem();
		//Log4JManager.getInstance().writeLog("刷新数据");
		if(!eventDeal.isAlive()){
			 Log4JManager.getInstance().writeLog("线程意外死了，重新开一个");
			eventDeal =null;
			eventDeal = new EventDeal();
			//eventDeal.pourDateToMem();
			eventDeal.start();
		}
		 //Log4JManager.getInstance().writeLog("线程还活着");
		
		 count++;
		 int TagN = message.getTagCount();
		 map.put( Integer.toString(TagN), message.getTagList());
		 N[TagN]++;
		 
		 if(count==20){
			//算法，选处20次中出现 频率最高的哪个标签集合,效果不错
			eventDeal.setTagList(map.get(Integer.toString(getMax(N))));
			if(eventDeal.getRfidIp()==null){
				eventDeal.setRfidIp(message.getReaderIPAddress());
				Log4JManager.getInstance().writeLog("获取ip来，确定机床");
				System.out.println("获取ip来，确定机床");
			}
			String tagId =null;
			Log4JManager.getInstance().writeLog("来自:"+message.getReaderIPAddress());
			System.out.println("来自:"+message.getReaderIPAddress() );
			if (getMax(N) == 0) {
				Log4JManager.getInstance().writeLog("(No Tags)");
			  }else{
				  for (int i = 0; i < getMax(N) ; i++) {
					  Tag tag = map.get(Integer.toString(getMax(N)))[i];
					 // System.out.println(tag.getTagID());
					  tagId =tag.getTagID().replaceAll(" ", "");
					  Log4JManager.getInstance().writeLog("标签id："+tagId+"  天线："+tag.getAntenna());
				  }
			          
			  }
			/*清空造作为下次使用*/
			num++;
			 if(num%50==0){
				 Log4JManager.getInstance().writeLog("线程还活着,看看是不是卡了");
				 //没发送五次数据，来监测一下循环是否卡死，时间应该够
				newN=eventDeal.getCount();
				Log4JManager.getInstance().writeLog("线程的累加器为"+eventDeal.getCount());
				if(newN == oldN){
					//说明卡死了
					 Log4JManager.getInstance().writeLog("线程卡在某处，重新开一个");
						eventDeal =null;
						eventDeal = new EventDeal();
						//eventDeal.pourDateToMem();
						eventDeal.start();
						oldN=0;
						newN=0;
				}else{
					oldN=newN;
				}
				
			 }
			Log4JManager.getInstance().writeLog("次数"+num);
			count =0;
			map.clear();
			for(int i=0;i<N.length;i++){
				N[i]=0;
			}
			
		 }
	
			  
	}

	public String getIp() {
		return ip;
	}

	public void setIp(String ip) {
		this.ip = ip;
	}

	public Tag[] getTagList() {
		return TagList;
	}

	public void setTagList(Tag[] tagList) {
		TagList = tagList;
	}
	public int getMax(int[] a){
	  int mylength=a.length;
	  int mymax=0;///最大值
	  int myloc=0;////下标搜索
		  
	  for(int i=0;i<mylength;i++){
		  if(i==0){
			  mymax=a[0];
		  }
		  int tmp=a[i];
		  if(tmp>mymax){
			  mymax=tmp;
			  myloc=i;
		  }
	  }
	  
	return myloc;
	}
}
