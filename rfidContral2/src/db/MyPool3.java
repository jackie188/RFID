package db;

import java.sql.Connection;

import xjtu.mes.util.DbUtil;

/**
* @author 姜文雷
* @version 创建时间：2016年11月24日 下午5:59:20
* 类说明
*/
  
  
public class MyPool3 {  
  
   public Connection getConnection(){
	   return DbUtil.getConnection();
   }
  
      
}  