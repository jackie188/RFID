package xjtu.mes.servlet.config;

import java.io.IOException;
import java.net.URLDecoder;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import xjtu.mes.util.DbUtil;

/**
* @author 姜文雷
* @version 创建时间：2016年11月5日 下午1:15:44
* 类说明
*/
@WebServlet("/MachRfidbBangdingServlet")
public class MachRfidBandingServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request,response);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {	
		response.setContentType("text/html");
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
			
		String machId = request.getParameter("machId");		//获得机床标号
		String rfidSysId = request.getParameter("rfidSysId"); //获得rfid系统标号
		//@jiang 没看懂这两行的用意
		
		
		System.out.println("机床标号："+machId);
		System.out.println("rfid系统标号："+rfidSysId);
	
		String sql1 = "insert into mach_rfId(machId,rfidSysId) values(?,?)";
		Connection conn = DbUtil.getConnection();
		PreparedStatement pstm = DbUtil.preparedStatement(conn,sql1);
		try{
			conn.setAutoCommit(false);
			pstm.setString(1, machId);
			pstm.setString(2, rfidSysId);
			int rs = pstm.executeUpdate();
			if(rs!=0){
				System.out.println("绑定机器和rfid系统成功");
			}
			//插入另一个数据库
			
			String sql2 = "update machine set flag = '1' where machId = ? ";
			pstm = DbUtil.preparedStatement(conn,sql2);
			pstm.setString(1, machId);
			rs = pstm.executeUpdate();
			if(rs != 0){
				System.out.println("更新机床状态成功");
			}
			String sql3 = "update rfidsystem set flag = '1' where rfidSysId = ? ";
			pstm = DbUtil.preparedStatement(conn,sql3);
			pstm.setString(1,rfidSysId );
			rs =pstm.executeUpdate();
			if(rs != 0){
				System.out.println("更新机床状态成功");
			}
			conn.commit();
			conn.setAutoCommit(true);
			response.getWriter().print("success");
			
		}catch(Exception e){
			response.getWriter().print("fail");
			System.out.println("出现意外，撤销操作");
			e.printStackTrace();
			
		}finally{
			DbUtil.close(pstm);
			DbUtil.close(conn);
		}
		
		
	}

	
	

}
