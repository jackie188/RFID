package xjtu.mes.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import xjtu.mes.util.DbUtil;

/**
* @author 姜文雷
* @version 创建时间：2016年11月7日 下午7:11:57
* 类说明
*/
public class WorkpieceManager {
	private static WorkpieceManager instance=null;
	protected WorkpieceManager(){}
	public  synchronized static WorkpieceManager getInstance(){
		if(instance==null){
			instance= new WorkpieceManager();
		}
		return instance;
	}
	
	

	public int getProcessCountByWorkpieceId(String workpieceId){
		int c =0;
		Connection conn = DbUtil.getConnection();
		PreparedStatement pstm1 = null; 
		try{
			String sql1 = " select * from  workpiece  where workpieceId = ? ";
			pstm1 = DbUtil.preparedStatement(conn,sql1);
			pstm1.setString(1,workpieceId );
			ResultSet rs1 = pstm1.executeQuery();
			if(rs1.next()){
				c = rs1.getInt("processCount");
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			DbUtil.close(pstm1);
			DbUtil.close(conn);
		}
		return c;
	}
	public void setProcessCountByWorkpieceId(String workpieceId,int c){
		
		Connection conn = DbUtil.getConnection();
		PreparedStatement pstm1 = null; 
		try{
			String sql1 = " update  workpiece set  processCount=? where workpieceId = ? ";
			pstm1 = DbUtil.preparedStatement(conn,sql1);
			pstm1.setInt(1,c );
			pstm1.setString(2,workpieceId );
			int rs1 = pstm1.executeUpdate();
			if(rs1!=0){
				System.out.println("跟新零件的工序数成功");
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			DbUtil.close(pstm1);
			DbUtil.close(conn);
		}
		
	}
	
}
