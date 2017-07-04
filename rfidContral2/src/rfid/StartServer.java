package rfid;

public class StartServer {
	
	
	public static void main(String[] args){
		//这里可以通过数据库，获取所有的rfid系统的ip值来，启动设备
		String ip1 = "192.168.1.129";
		ServerThread t1 = new ServerThread(ip1,19000);
		t1.start();
		System.out.println("启动129设备1");
		Log4JManager.getInstance().writeLog("启动129设备1");
		 
		String ip2 = "192.168.1.147";
		ServerThread t2 = new ServerThread(ip2,19001);
		t2.start();
		System.out.println("启动147设备1");
		Log4JManager.getInstance().writeLog("启动147设备2");
		
		String ip3 = "192.168.1.163";
		ServerThread t3 = new ServerThread(ip3,19002);
		t3.start();
		System.out.println("启动163设备1");
		Log4JManager.getInstance().writeLog("启动163设备3");
		
		long runTime = 10000; // milliseconds
		  long startTime = System.currentTimeMillis();
		  do {
		    try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		  } while( System.currentTimeMillis()-startTime < runTime);
		  System.out.println("主线程挂了");
	}
}
