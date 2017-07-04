package xjtu.mes.servlet.config;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xjtu.mes.manager.WorkpieceManager;
import xjtu.mes.manager.WorkpieceProcessManager;
import xjtu.mes.model.Workpiece;
import xjtu.mes.model.WorkpieceProcess;
import xjtu.mes.util.DbUtil;

/**
* @author 姜文雷
* @version 创建时间：2016年11月8日 下午5:13:47
* 类说明
*/
@WebServlet("/AddWorkpieceProcessServlet2")
public class AddWorkpieceProcessServlet2 extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String workpieceName = req.getParameter("workpieceName");
		String processName = req.getParameter("processName");
		String processId = req.getParameter("processId");
		String machId = req.getParameter("machId");
		//如果批量加入的话，这里要得到由零件名字得到的id集合，在循环调用下一行
		boolean f =false ;
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		PreparedStatement pstm = null;
		ResultSet rs = null;
		String sql = "select workpieceId from workpiece where workpieceName ='"+workpieceName+"'";
		try {
			conn.setAutoCommit(false);
			rs =stm.executeQuery(sql);
			while(rs.next()){
				String id = rs.getString(1);
				WorkpieceProcess workpieceprocess = new WorkpieceProcess (id ,processName,	processId ,machId );
				
				String sql1 = "insert into workpiece_process values(null,?,?,?,?,?,'0')";
				pstm = DbUtil.preparedStatement(conn,sql1);
				pstm.setString(1,workpieceprocess.getWorkpieceId());
				pstm.setString(2, workpieceprocess.getProcessName());
				//pstm.setString(3,workpieceprocess.getProcessId());
				pstm.setString(4,workpieceprocess.getMachId());
				
				int c =WorkpieceManager.getInstance().getProcessCountByWorkpieceId(workpieceprocess.getWorkpieceId());
				WorkpieceManager.getInstance().setProcessCountByWorkpieceId(workpieceprocess.getWorkpieceId(),c+1);
				pstm.setInt(5,c+1);
				//这里自动给工序添加id，不再输入
				processId = "p"+(c+1);
				workpieceprocess.setProcessId(processId);
				pstm.setString(3,workpieceprocess.getProcessId());
				
				int r = pstm.executeUpdate();
				if(r!=0){
					System.out.println("添加工序成功"+workpieceprocess.getLevel());
				}
			}
			conn.commit();
			conn.setAutoCommit(true);
			f=true;
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally{
			DbUtil.close(rs);
			DbUtil.close(stm);
			DbUtil.close(pstm);
			DbUtil.close(conn);
			if(f){
				resp.getWriter().print("success");
			}else{
				resp.getWriter().print("fail");
			}
		}
		
		
	}
}
