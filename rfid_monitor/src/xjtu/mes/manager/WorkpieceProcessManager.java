package xjtu.mes.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import xjtu.mes.model.RfidSystem;
import xjtu.mes.model.Workpiece;
import xjtu.mes.model.WorkpieceProcess;
import xjtu.mes.util.DbUtil;

/**
* @author 姜文雷
* @version 创建时间：2016年11月8日 下午3:24:12
* 类说明
*/
public class WorkpieceProcessManager {
	private static WorkpieceProcessManager instance=null;
	protected WorkpieceProcessManager(){}
	public  synchronized static WorkpieceProcessManager getInstance(){
		if(instance==null){
			instance= new WorkpieceProcessManager();
		}
		return instance;
	}
	
	/**
	 * 通过零件id获取所有的工序集合
	 * @param workpieceId
	 * @return
	 */
	public  List<WorkpieceProcess> getAllProcessByWorkpieceId( String workpieceId){
		List<WorkpieceProcess> workpieceProcessList = new ArrayList<WorkpieceProcess>();
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		ResultSet rs = null;
		String sql = "select * from workpiece_process where workpieceId ='"+workpieceId+"' order by level";
		try{
			rs = DbUtil.executeQuery(stm, sql);
			while( rs.next()){
				WorkpieceProcess workpieceProcess = new WorkpieceProcess();
				workpieceProcess.setWorkpieceId(rs.getString(2));
				workpieceProcess.setProcessName(rs.getString(3));
				workpieceProcess.setProcessId(rs.getString(4));
				workpieceProcess.setMachId(rs.getString(5));
				workpieceProcess.setLevel(rs.getInt(6));
				workpieceProcess.setState(rs.getInt(7));
				workpieceProcessList.add(workpieceProcess);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{	
			DbUtil.close(rs);
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return workpieceProcessList;
	}
	

	/**
	 * 通过机床id获取所有在改机床加工的工序集合
	 * @param workpieceId
	 * @return
	 */
	public  List<WorkpieceProcess> getAllProcessByMachId( int machId){
		List<WorkpieceProcess> workpieceProcessList = new ArrayList<WorkpieceProcess>();
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		ResultSet rs = null;
		String sql = "select * from workpiece_process where machId ='"+machId+"'";
		try{
			rs = DbUtil.executeQuery(stm, sql);
			while( rs.next()){
				WorkpieceProcess workpieceProcess = new WorkpieceProcess();
				workpieceProcess.setWorkpieceId(rs.getString(2));
				workpieceProcess.setProcessName(rs.getString(3));
				workpieceProcess.setProcessId(rs.getString(4));
				workpieceProcess.setMachId(rs.getString(5));
				workpieceProcess.setLevel(rs.getInt(6));
				workpieceProcess.setState(rs.getInt(7));
				workpieceProcessList.add(workpieceProcess);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			DbUtil.close(rs);
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return workpieceProcessList;
	}
	/**
	 * 通过机床id及零件id，获取改零件所有在改机床加工的工序集合
	 * @param workpieceId
	 * @return
	 */
	public  List<WorkpieceProcess> getAllProcessByMachIdAndWorkpieceId( int machId,int workpieceId){
		List<WorkpieceProcess> workpieceProcessList = new ArrayList<WorkpieceProcess>();
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		ResultSet rs = null;
		String sql = "select * from workpiece_process where machId ='"+machId+"' and workpieceId ='"+workpieceId+"'";
		try{
			rs = DbUtil.executeQuery(stm, sql);
			while( rs.next()){
				WorkpieceProcess workpieceProcess = new WorkpieceProcess();
				workpieceProcess.setWorkpieceId(rs.getString(2));
				workpieceProcess.setProcessName(rs.getString(3));
				workpieceProcess.setProcessId(rs.getString(4));
				workpieceProcess.setMachId(rs.getString(5));
				workpieceProcess.setLevel(rs.getInt(6));
				workpieceProcess.setState(rs.getInt(7));
				workpieceProcessList.add(workpieceProcess);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{	
			DbUtil.close(rs);
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return workpieceProcessList;
	}
	
	public  boolean findProcessByWId_PId(String  workpieceId, String processId){
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		ResultSet rs = null;
		String sql = " select * from workpiece_process  where processId ='"+processId+"' and workpieceId ='"+workpieceId+"'";
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
	
	public boolean addWorkpieceProcess(WorkpieceProcess workpieceprocess){
		boolean flag = false;
		String sql1 = "insert into workpiece_process values(null,?,?,?,?,?,'0')";
		Connection conn = DbUtil.getConnection();
		
		PreparedStatement pstm = DbUtil.preparedStatement(conn,sql1);
		try{
			conn.setAutoCommit(false);
			
			pstm.setString(1,workpieceprocess.getWorkpieceId());
			pstm.setString(2, workpieceprocess.getProcessName());
			pstm.setString(3,workpieceprocess.getProcessId());
			pstm.setString(4,workpieceprocess.getMachId());
			int c =WorkpieceManager.getInstance().getProcessCountByWorkpieceId(workpieceprocess.getWorkpieceId());
			WorkpieceManager.getInstance().setProcessCountByWorkpieceId(workpieceprocess.getWorkpieceId(),c+1);
			pstm.setInt(5,c+1);
			
			int rs = pstm.executeUpdate();
			if(rs!=0){
				System.out.println("添加工序成功"+workpieceprocess.getLevel());
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
	/**
	 * 通过机床id获取所哟零件
	 * @return
	 */
	public  List<Workpiece> getWorkpiecesByMachId(String machId){
		List<Workpiece> workpieceList = new ArrayList<Workpiece>();
		PreparedStatement pstm1= null;
		ResultSet rs2 =null;
		ResultSet rs = null;
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		String sql = "select distinct workpieceId from workpiece_process where machId='"+machId+"'";
		try{
			rs = DbUtil.executeQuery(stm, sql);
			while( rs.next()){
				String workpieceId =rs.getString("workpieceId");
				String sql2 = "select * from workpiece where workpieceId='"+workpieceId+"'";
				pstm1 = conn.prepareStatement(sql2);
				rs2 = pstm1.executeQuery();
				if(rs2.next()){
					Workpiece workpiece = new Workpiece();
					workpiece.setWorkpieceId(rs2.getString(1));
					workpiece.setWorkpieceName(rs2.getString(2));
					workpiece.setWorkpieceRfid(rs2.getString(3));
					workpiece.setTime(rs2.getTimestamp(4));
					workpiece.setFlag(rs2.getString(5));
					workpiece.setUserId(rs2.getString(6));
					workpiece.setFinish(rs2.getString(7));
					workpiece.setProcessCount(rs2.getInt(8));
					workpieceList.add(workpiece);
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			DbUtil.close(rs);
			DbUtil.close(rs2);
			DbUtil.close(pstm1);
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return workpieceList;
	}
	/**
	 * 新加的函数，与用户有关，做机床数据统计
	 * 通过机床id获取所哟零件
	 * @return
	 */
	public  List<Workpiece> getWorkpiecesByMachIdAndUserId(String machId,String userId){
		List<Workpiece> workpieceList = new ArrayList<Workpiece>();
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		PreparedStatement pstm1= null;
		ResultSet rs2 =null;
		ResultSet rs = null;
		String sql = "select distinct workpieceId from workpiece_process where machId='"+machId+"'";
		try{
			rs = DbUtil.executeQuery(stm, sql);
			while( rs.next()){
				String workpieceId =rs.getString("workpieceId");
				String sql2 = "select * from workpiece where workpieceId='"+workpieceId+"' and userId ='"+userId+"'";
				pstm1 = conn.prepareStatement(sql2);
				rs2 = pstm1.executeQuery();
				if(rs2.next()){
					Workpiece workpiece = new Workpiece();
					workpiece.setWorkpieceId(rs2.getString(1));
					workpiece.setWorkpieceName(rs2.getString(2));
					workpiece.setWorkpieceRfid(rs2.getString(3));
					workpiece.setTime(rs2.getTimestamp(4));
					workpiece.setFlag(rs2.getString(5));
					workpiece.setUserId(rs2.getString(6));
					workpiece.setFinish(rs2.getString(7));
					workpiece.setProcessCount(rs2.getInt(8));
					workpieceList.add(workpiece);
				}
				
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{
			DbUtil.close(rs);
			DbUtil.close(rs2);
			DbUtil.close(pstm1);
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return workpieceList;
	}
	
	/**
	 * 通过机床id和用户,获取其零件的完成个数
	 * @return
	 */
	public  int getFinishedWorkpiecesByMachIdAndUserId(String machId ,String userId){
		int count=0;
		
		List<Workpiece> workpieceList = new ArrayList<Workpiece>();
		//要保证这句话的结果不为空，为空时就不调用了
		workpieceList =this.getWorkpiecesByMachIdAndUserId(machId,userId);
		for(Workpiece w:workpieceList){
			System.out.println("零件"+w.getWorkpieceId()+w.getFinish());
			if(w.getFinish().equals("1")){
				count++;
			}
		}
		return count;
	}
	/**
	 * 通过机床id,获取其零件的完成个数
	 * @return
	 */
	public  int getFinishedWorkpiecesByMachId(String machId){
		int count=0;
		
		List<Workpiece> workpieceList = new ArrayList<Workpiece>();
		//要保证这句话的结果不为空，为空时就不调用了
		workpieceList =this.getWorkpiecesByMachId(machId);
		for(Workpiece w:workpieceList){
			if(w.getFinish().equals("1")){
				count++;
			}
		}
		return count;
	}
	
	public  int getFinishedWorkpiecesByWorkpieceNameAndUserId(String workpieceName,String userId){
		int count=0;
		List<Workpiece> workpieceList = new ArrayList<Workpiece>();
		//要保证这句话的结果不为空，为空时就不调用了
		workpieceList =WorkpieceManager.getInstance().getWorkpiecesByNameAndUserId(workpieceName,userId);
		for(Workpiece w:workpieceList){
			if(w.getFinish().equals("1")){
				count++;
			}
		}
		return count;
	}
	public  int getFinishedWorkpiecesByWorkpieceName(String workpieceName){
		int count=0;
		List<Workpiece> workpieceList = new ArrayList<Workpiece>();
		//要保证这句话的结果不为空，为空时就不调用了
		workpieceList =WorkpieceManager.getInstance().getWorkpiecesByName(workpieceName);
		for(Workpiece w:workpieceList){
			if(w.getFinish().equals("1")){
				count++;
			}
		}
		return count;
	}
	/**
	 * 新加的函数，用于统计该用户下的机床工序情况
	 * 通过机床id,工序状态，获取该用户下的工序个数
	 * @return
	 */
	public  int getProcessCountByMachIdAndStateAndUserId(String machId,String state,String userId){
		int count=0;
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		ResultSet rs = null;
		List<Workpiece> workpieceList = new ArrayList<Workpiece>();
		//要保证这句话的结果不为空，为空时就不调用了
		workpieceList =this.getWorkpiecesByMachIdAndUserId(machId,userId);
		try{
			for(Workpiece w:workpieceList){
				String workpieceId = null;
				workpieceId = w.getWorkpieceId();
				String sql = "select *from workpiece_process where machId='"+machId +"' and state ='"+state+"' and workpieceId='"+workpieceId+"'";
				rs= DbUtil.executeQuery(stm, sql);
				while(rs.next()){
						count++;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{	
			DbUtil.close(rs);
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return count;
	}
	/**
	 * 新加的函数，用于统计该用户下的机床工序情况
	 * 通过机床id,工序状态，获取该用户下的工序个数
	 * @return 是个集合（1,2,3,4,5,6）状态下的个数 
	 */
	public  int[] getProcessCountByAllState(String machId,List<Workpiece> workpieceList){
		int count[]={0,0,0,0,0,0,0};
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		ResultSet rs = null;
		
		try{
			for(Workpiece w:workpieceList){
				String workpieceId = null;
				workpieceId = w.getWorkpieceId();
				String sql = "select *from workpiece_process where machId='"+machId +"' and workpieceId='"+workpieceId+"'";
				rs= DbUtil.executeQuery(stm, sql);
				while(rs.next()){
					count[rs.getInt("state")]++;
				}
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{	
			DbUtil.close(rs);
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return count;
	}
	
	
	/**
	 * 通过机床id,工序状态，获取其个数
	 * @return
	 */
	public  int getProcessCountByMachIdAndState(String machId,String state){
		int count=0;
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		ResultSet rs = null;
		try{
			String sql = "select *from workpiece_process where machId='"+machId +"' and state ='"+state+"'";
			rs= DbUtil.executeQuery(stm, sql);
			while(rs.next()){
					count++;
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{	
			DbUtil.close(rs);
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return count;
	}
}
