package xjtu.mes.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * 
 * 是用tomcat管理的连接池
* @author 姜文雷
* @version 创建时间：2016年11月28日 下午2:10:01
* 类说明
*/
public class Mypool2 {
	
	public Connection getConnection(){
		Connection conn =null;
		Context ctx;
		try {
			ctx = new  InitialContext();
			DataSource ds = (DataSource) ctx.lookup("java:comp/env/jdbc/rfid_monitor");
			conn = ds.getConnection();
		} catch (NamingException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return conn;
	}

}
