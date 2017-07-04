package xjtu.mes.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import xjtu.mes.model.Machine;
import xjtu.mes.model.RfidSystem;
import xjtu.mes.util.DbUtil;

/**
* @author 姜文雷
* @version 创建时间：2016年11月5日 下午12:41:55
* 类说明
* 机床管理类
* 采用单利模式
*/
public class MachManager {
	
	private static MachManager instance=null;
	protected MachManager(){}
	public  synchronized static MachManager getInstance(){
		if(instance==null){
			instance= new MachManager();
		}
		return instance;
	}
	
	public  boolean findMachByMachId(String MachId){
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		ResultSet rs = null;
		String sql = " select * from machine  where machId ='"+MachId+"'";
		try{
			rs = stm.executeQuery(sql);
			if(rs.next()){
				return true;
			}else{
				return false;
			}
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("sql error");
		}finally{
			DbUtil.close(rs);
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
	
		return false;
	}
	public  Machine getMachByMachId(String MachId){
		Machine machine = null;
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		ResultSet rs = null;
		String sql = " select * from machine  where machId ='"+MachId+"'";
		try{
			rs = stm.executeQuery(sql);
			if(rs.next()){
				String machName = rs.getString("machName");
				machine = new Machine(machName,MachId );
			}
			
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("sql error");
		}finally{
			DbUtil.close(rs);
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
	
		return machine;
	}
	
	/**
	 * 获取所有未绑定的机床
	 * @return
	 */
	public  List<Machine> getMachines(){
		List<Machine> MachineList = new ArrayList<Machine>();
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		String sql = "select * from machine";
		try{
			ResultSet rs = DbUtil.executeQuery(stm, sql);
			while( rs.next()){
				Machine machine = new Machine();
				machine.setMachName(rs.getString(1));
				machine.setFlag(rs.getString(2));
				machine.setMachId(rs.getString(3));
				MachineList.add(machine);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{	
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return MachineList;
	}
	/**
	 * 获取所有未绑定的机床
	 * @return
	 */
	public  List<Machine> getAvalbMachines(){
		List<Machine> MachineList = new ArrayList<Machine>();
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		String sql = "select * from machine where flag ='0' ";
		try{
			ResultSet rs = DbUtil.executeQuery(stm, sql);
			while( rs.next()){
				Machine machine = new Machine();
				machine.setMachName(rs.getString(1));
				machine.setMachId(rs.getString(3));
				MachineList.add(machine);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{	
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return MachineList;
	}
	public boolean addMach(Machine machine){
		boolean flag = false;
		String sql1 = "insert into machine(machName,machId) values(?,?)";
		Connection conn = DbUtil.getConnection();
		PreparedStatement pstm = DbUtil.preparedStatement(conn,sql1);
		try{
			
			pstm.setString(1,machine.getMachName() );
			pstm.setString(2, machine.getMachId());
			int rs = pstm.executeUpdate();
			if(rs!=0){
				System.out.println("添加机床成功");
			}
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
	public boolean deleteMachByMachId(String machId){
		boolean flag = false;
		PreparedStatement pstm =null;
		PreparedStatement pstm1 =null;
		PreparedStatement pstm2 =null;
		PreparedStatement pstm3 =null;
		PreparedStatement pstm4 =null;
		
		String sql1 = "delete from machine where machId = ? ";
		Connection conn = DbUtil.getConnection();
		pstm = DbUtil.preparedStatement(conn,sql1);
		try{
			conn.setAutoCommit(false);
			
			pstm.setString(1,machId );
			int rs = pstm.executeUpdate();
			if(rs!=0){
				System.out.println("删除机床成功");
			}
			
			String sql2 = "select * from mach_rfid where machId = ? ";
			pstm2 = DbUtil.preparedStatement(conn,sql2);
			pstm2.setString(1,machId );
			ResultSet r  = pstm2.executeQuery();
			if(r.next()){
				
				String rfidId = r.getString("rfidSysId");
				String sql3 = "update rfidsystem set flag = '0' where rfidSysId = ? ";
				pstm3 = DbUtil.preparedStatement(conn,sql3);
				pstm3.setString(1, rfidId);
				rs = pstm3.executeUpdate();
				if(rs != 0){
					System.out.println("更新系统状态成功");
				}
				
				String sql4 = "delete from mach_rfid where machId = ? ";
				pstm4 = DbUtil.preparedStatement(conn,sql4);
				pstm4.setString(1,machId );
				rs = pstm4.executeUpdate();
				if(rs!=0){
					System.out.println("删除绑定关系成功");
				}
				
			}
			conn.commit();
			conn.setAutoCommit(true);
			
			flag = true;
			
		}catch(Exception e){
			System.out.println("出现意外");
			e.printStackTrace();
			
		}finally{
			DbUtil.close(pstm);
			DbUtil.close(pstm1);
			DbUtil.close(pstm2);
			DbUtil.close(pstm3);
			DbUtil.close(pstm4);
			DbUtil.close(conn);
		}
		return flag;
	}
	
	public boolean editMach(Machine machine){
		boolean flag = false;
		String sql1 = "update  machine set machName =? where machId =?";
		Connection conn = DbUtil.getConnection();
		PreparedStatement pstm = DbUtil.preparedStatement(conn,sql1);
		try{
			
			pstm.setString(1,machine.getMachName() );
			pstm.setString(2, machine.getMachId());
			int rs = pstm.executeUpdate();
			if(rs!=0){
				System.out.println("修改机床成功");
			}
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