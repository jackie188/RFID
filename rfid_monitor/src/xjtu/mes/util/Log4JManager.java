package xjtu.mes.util;

import org.apache.log4j.Logger;


/**
* @author 姜文雷
* @version 创建时间：2016年11月24日 上午9:54:48
* 类说明
*/
public class Log4JManager {
	private static Log4JManager instance=null;
	protected Log4JManager(){}
	public  synchronized static Log4JManager getInstance(){
		if(instance==null){
			instance= new Log4JManager();
		}
		return instance;
	}
	
	public Logger getLoger(){
		 Logger log=Logger.getLogger(this.getClass().getName());
		 return log;
	}
	public void writeLog(String s){
		 Logger log=Logger.getLogger(this.getClass().getName());
		 log.info(s);
	}
}
