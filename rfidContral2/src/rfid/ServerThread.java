package rfid;
/*写这个类有点多此一举了
 其实可以去掉的，算了不去了
 * */

public class ServerThread extends Thread{
	private MyMessageListener myMesssageListerner =null;
	private boolean flag = true;
	private String IPAddressName =null;
	private int port ;

	
	
	public ServerThread( String iPAddressName,int port) {
		this.IPAddressName = iPAddressName;
		this.port = port;
	}

	@Override
	public void run() {
		 try {
			myMesssageListerner = new  MyMessageListener(this.IPAddressName,this.port);
			myMesssageListerner.startServer();
			 Log4JManager.getInstance().writeLog("启动监听");
		 } catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		while(true){
			try {
				//这里的死循环会占用大量cpu，并且现在根本用不到，写个sleep，让他睡觉吧
				Thread.sleep(1000000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			if(flag){
				if(!myMesssageListerner.isRunning()){
					myMesssageListerner.startServer();
				}else{
					;
				}
			}else{
				myMesssageListerner.stopServer();
			}
		}
	}



	public boolean isFlag() {
		return flag;
	}



	public void setFlag(boolean flag) {
		this.flag = flag;
	}



	public String getIPAddressName() {
		return IPAddressName;
	}



	public void setIPAddressName(String iPAddressName) {
		IPAddressName = iPAddressName;
	}
	

}
