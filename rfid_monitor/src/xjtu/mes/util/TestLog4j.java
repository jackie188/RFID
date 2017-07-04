package xjtu.mes.util;

import org.apache.log4j.Logger;

/**
* @author 姜文雷
* @version 创建时间：2016年11月24日 上午9:13:00
* 类说明
*/
public class TestLog4j {
	public void log4j(){

		  Logger log=Logger.getLogger(this.getClass().getName());

		  System.out.println("test log4j");

		  log.info("testLog4j");

		 }


		 public static void main(String[] args) {

		  TestLog4j tl=new TestLog4j();

		  tl.log4j(); 

		 }
}
