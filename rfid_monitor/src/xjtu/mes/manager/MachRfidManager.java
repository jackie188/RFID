package xjtu.mes.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import xjtu.mes.model.MachRfid;
import xjtu.mes.model.RfidSystem;
import xjtu.mes.util.DbUtil;

/**
* @author 姜文雷
* @version 创建时间：2016年11月5日 下午5:00:04
* 类说明
*/
public class MachRfidManager {
	private static MachRfidManager instance=null;
	protected MachRfidManager(){}
	public  synchronized static MachRfidManager getInstance(){
		if(instance==null){
			instance= new MachRfidManager();
		}
		return instance;
	}
	/**
	 * 获取所有绑定列表
	 * @return List<RfidSystem>
	 */
	public  List<MachRfid> getMachRfids(){
		List<MachRfid> MachrfidList = new ArrayList<MachRfid>();
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		Connection conn2 = DbUtil.getConnection();
		Statement stm2 = DbUtil.createStatemnt(conn2);
		Connection conn3 = DbUtil.getConnection();
		Statement stm3 = DbUtil.createStatemnt(conn3);
		String sql1 = "select * from mach_rfid ";
		ResultSet rs1= null;
		ResultSet rs2= null;
		ResultSet rs3= null;
		try{
			rs1 = DbUtil.executeQuery(stm, sql1);
			while( rs1.next()){
				MachRfid machRfid = new MachRfid();
				String machId =rs1.getString(1);
				String SystemId = rs1.getString(2);
				//设置机床属性
				
				machRfid.setMachId(machId);
				
				String sql2 = "select * from machine where machId ='"+machId+"'";
				rs2 = DbUtil.executeQuery(stm2, sql2);
				if(rs2.next()){
					machRfid.setMachName(rs2.getString(1));
				}
				
				//设置系rfid统属性
				
				machRfid.setRfidSysId(SystemId );
				
				String sql3 = "select * from rfidsystem where rfidSysId ='"+SystemId+"'";
				rs3 = DbUtil.executeQuery(stm3, sql3);
				if(rs3.next()){
					machRfid.setRfidSysName(rs3.getString(2));
				}
				
				MachrfidList.add(machRfid );
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{	
			DbUtil.close(rs1);
			DbUtil.close(rs2);
			DbUtil.close(rs3);
			DbUtil.close(stm);
			DbUtil.close(conn);
			DbUtil.close(stm2);
			DbUtil.close(conn2);
			DbUtil.close(stm3);
			DbUtil.close(conn3);
		}
		return MachrfidList ;
	}
	
	
	public boolean deleteMachRfid(String rfidId ,String machId){
		boolean flag = false;
		PreparedStatement pstm =null;
		Connection conn =null;
		try{
			conn = DbUtil.getConnection();
			conn.setAutoCommit(false);
			String sql1 = "delete from mach_rfid where rfidSysId = ? and machId=? ";
			pstm = DbUtil.preparedStatement(conn,sql1);
			pstm.setString(1,rfidId );
			pstm.setString(2,machId );
			int rs = pstm.executeUpdate();
			if(rs!=0){
				System.out.println("删除绑定成功");
			}
			//接下来要跟新mach 和 rfid 的状态

			String sql2 = "update machine set flag = '0' where machId = ? ";
			pstm = DbUtil.preparedStatement(conn,sql2);
			pstm.setString(1, machId);
			rs = pstm.executeUpdate();
			if(rs != 0){
				System.out.println("更新机床状态成功");
			}
			String sql3 = "update rfidsystem set flag = '0' where rfidSysId = ? ";
			pstm = DbUtil.preparedStatement(conn,sql3);
			pstm.setString(1,rfidId );
			rs =pstm.executeUpdate();
			if(rs != 0){
				System.out.println("更新系统状态成功");
			}
			
			
			conn.commit();
			conn.setAutoCommit(true);
			flag = true;
			
		}catch(Exception e){
			System.out.println("出现意外");
			e.printStackTrace();
			
		}finally{
			DbUtil.close(pstm);
		
			DbUtil.close(conn);
		}
		return flag;
	}
	
	
}
