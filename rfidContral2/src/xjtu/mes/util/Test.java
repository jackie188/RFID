package xjtu.mes.util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import db.MyPool3;
import rfid.Log4JManager;
import xjtu.mes.model.MachRfid;
import xjtu.mes.model.Machine;
import xjtu.mes.model.RfidSystem;
import xjtu.mes.model.Workpiece;
import xjtu.mes.model.WorkpieceProcess;
import xjtu.mes.util.DbUtil;

/**
 * 
 * 测试为什么，读取数据库，会前后不一致
* @author 姜文雷
* @version 创建时间：2016年11月25日 上午9:22:39
* 类说明
*/
public class Test {
	 private MyPool3 pool =new MyPool3();
	private List<WorkpieceProcess> M_workpieceProcessList = new ArrayList<WorkpieceProcess>();
	private List<Machine> M_machineList = new ArrayList<Machine>();
	private List<Workpiece> M_workpieceList = new ArrayList<Workpiece>();
	private List<RfidSystem> M_rfidSystemList = new ArrayList<RfidSystem>();
	private List<MachRfid> M_machRfidList = new ArrayList<MachRfid>();
	
	
	public static void main(String Args[]){
		Test test = new Test();
		//test.testMysql();
		while(true){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			test.pourDateToMem();
		}
		
		
	}
	public void testMysql(){
		Connection conn =null;
		Connection conn2 =null;
		Statement stm  =null;
		Statement stm2  =null;
		ResultSet rs1 = null;
		conn = DbUtil.getConnection();
		try{
			conn.setAutoCommit(false);
			stm =DbUtil.createStatemnt(conn);
			String sql = "update workpieceprocess set processId ='132' where Num =1 ";
			stm.executeUpdate(sql);
			conn.commit();
			conn.setAutoCommit(true);
			
			sql = "select * from workpieceprocess ";
			stm2 =DbUtil.createStatemnt(conn);
			rs1 =stm2.executeQuery(sql);
			while(rs1.next()){
				System.out.println(rs1.getString(3));
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log4JManager.getInstance().writeLog("循环读取数据库到内存出问题");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log4JManager.getInstance().writeLog("循环读取数据库到内存出问题");
		}finally{
			DbUtil.close(rs1);
			
			DbUtil.close(stm2);
			DbUtil.close(conn2);
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
			
		
		
	}
	public void pourDateToMem(){
		//Log4JManager.getInstance().writeLog("循环读取数据库");
		
		this.M_machineList .clear();
		this.M_workpieceList.clear();
		this.M_workpieceProcessList .clear();
		this.M_rfidSystemList.clear();
		this.M_machRfidList.clear();
		Connection conn =null;
		Statement stm  =null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		ResultSet rs5 = null;
		try {
			/*conn = DbUtil.getConnection();*/
			/*stm = DbUtil.createStatemnt(conn);*/
			conn = pool.getConnection();
			
			stm =conn.createStatement();
			String sql ="select * from machine";
			rs1 = stm.executeQuery(sql);
			while(rs1.next()){
				Machine machine = new Machine();
				machine.setMachName(rs1.getString(1));
				machine.setFlag(rs1.getString(2));
				machine.setMachId(rs1.getString(3));
				this.M_machineList.add(machine);
			}
			sql ="select * from workpiece";
			rs2 = stm.executeQuery(sql);
			while( rs2.next()){
				Workpiece workpiece = new Workpiece();
				
				workpiece.setWorkpieceId(rs2.getString(1));
				workpiece.setWorkpieceName(rs2.getString(2));
				workpiece.setWorkpieceRfId(rs2.getString(3));
				workpiece.setTime(rs2.getTimestamp(4));
				workpiece.setFlag(rs2.getString(5));
				workpiece.setUserId(rs2.getString(6));
				workpiece.setFinish(rs2.getString(7));
				workpiece.setProcessCount(rs2.getInt(8));
				this.M_workpieceList.add(workpiece);
			}
			sql ="select * from workpiece_process";
			rs3= stm.executeQuery(sql);
			while(rs3.next()){
				WorkpieceProcess workpieceProcess = new WorkpieceProcess();
				workpieceProcess.setNum(rs3.getInt(1));
				workpieceProcess.setWorkpieceId(rs3.getString(2));
				workpieceProcess.setProcessName(rs3.getString(3));
				workpieceProcess.setProcessId(rs3.getString(4));
				workpieceProcess.setMachId(rs3.getString(5));
				workpieceProcess.setLevel(rs3.getInt(6));
				workpieceProcess.setState(rs3.getInt(7));
				if(rs3.getInt(1)==73){
					Log4JManager.getInstance().writeLog("工序"+rs3.getInt(1)+"的状态"+rs3.getInt(7));
				}
			
				this.M_workpieceProcessList.add(workpieceProcess);
			}
			sql ="select * from rfidsystem";
			rs4= stm.executeQuery(sql);
			while( rs4.next()){
				RfidSystem rfidSystem = new RfidSystem();
				rfidSystem.setRfidSysId(rs4.getString(1));
				rfidSystem.setRfidName(rs4.getString(2));
				rfidSystem.setRfidModel(rs4.getString(3));
				rfidSystem.setRfidPort(rs4.getInt(4));
				rfidSystem.setRfidIp(rs4.getString(5));
				//System.out.println(rs.getString(5));
				rfidSystem.setFlag(rs4.getString(6));
				this.M_rfidSystemList.add(rfidSystem);
			}
			sql ="select * from mach_rfid";
			rs5= stm.executeQuery(sql);
			while( rs5.next()){
				MachRfid machRfid = new MachRfid();
				machRfid .setRfidSysId(rs5.getString(2));
				machRfid .setMachId(rs5.getString(1));
				this.M_machRfidList.add(machRfid);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log4JManager.getInstance().writeLog("循环读取数据库到内存出问题");
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log4JManager.getInstance().writeLog("循环读取数据库到内存出问题");
		}finally{
			DbUtil.close(rs1);
			DbUtil.close(rs2);
			DbUtil.close(rs3);
			DbUtil.close(rs4);
			DbUtil.close(rs5);
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
				
		
	}

}
