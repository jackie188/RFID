package xjtu.mes.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import xjtu.mes.model.Machine;
import xjtu.mes.model.Workpiece;
import xjtu.mes.model.WorkpieceProcess;
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
	
	
	public  boolean findWorkpieceByworkpieceId(String  workpieceId){
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		ResultSet rs = null;
		String sql = " select * from workpiece where workpieceId ='"+workpieceId+"'";
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
	public  boolean setWorkpieceBandingStateByworkpieceId(String  workpieceId){
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		int rs = 0;
		String sql = " update  workpiece set isBanding = '1' where workpieceId ='"+workpieceId+"'";
		try{
			rs = stm.executeUpdate(sql);
			if(rs!=0){
				return true;
			}else{
				return false;
			}
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("sql error");
		}finally{
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
	
		return false;
	}
	public  Workpiece getWorkpieceByworkpieceId(String  workpieceId){
		Workpiece workpiece = null;
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		ResultSet rs = null;
		String sql = " select * from workpiece where workpieceId ='"+workpieceId+"'";
		try{
			rs = stm.executeQuery(sql);
			if(rs.next()){
				
				workpiece = new Workpiece(rs.getString(1),rs.getString(2),rs.getString(3));
				
				workpiece.setTime(rs.getTimestamp(4));
				workpiece.setFlag(rs.getString(5));
				workpiece.setUserId(rs.getString(6));
				workpiece.setFinish(rs.getString(7));
				workpiece.setProcessCount(rs.getInt(8));
				workpiece.setIsBanding(rs.getString(9));
			}
		}catch(SQLException e){
			e.printStackTrace();
			System.out.println("sql error");
		}finally{
			DbUtil.close(rs);
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
	
		return workpiece;
	}
	
	public  boolean findWorkpieceByworkpieceRfid(String  workpieceRfid){
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		ResultSet rs = null;
		String sql = " select * from workpiece where workpieceRfid ='"+workpieceRfid+"'";
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
	
	
	/**
	 * 获取所有未
	 * @return
	 */
	public  List<Workpiece> getWorkpiecesByNameAndUserId(String name,String userId){
		List<Workpiece> workpieceList = new ArrayList<Workpiece>();
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		String sql = "select * from workpiece where workpieceName ='"+name+"' and userId='"+userId+"'";
		try{
			ResultSet rs = DbUtil.executeQuery(stm, sql);
			while( rs.next()){
				Workpiece workpiece = new Workpiece();
				
				workpiece.setWorkpieceId(rs.getString(1));
				workpiece.setWorkpieceName(rs.getString(2));
				workpiece.setWorkpieceRfid(rs.getString(3));
				workpiece.setTime(rs.getTimestamp(4));
				workpiece.setFlag(rs.getString(5));
				workpiece.setUserId(rs.getString(6));
				workpiece.setFinish(rs.getString(7));
				workpiece.setProcessCount(rs.getInt(8));
				workpiece.setIsBanding(rs.getString(9));
				workpieceList.add(workpiece);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{	
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return workpieceList;
	}
	/**
	 * 获取所有未
	 * @return
	 */
	public  List<Workpiece> getWorkpiecesByName(String name){
		List<Workpiece> workpieceList = new ArrayList<Workpiece>();
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		String sql = "select * from workpiece where workpieceName ='"+name+"'";
		try{
			ResultSet rs = DbUtil.executeQuery(stm, sql);
			while( rs.next()){
				Workpiece workpiece = new Workpiece();
				
				workpiece.setWorkpieceId(rs.getString(1));
				workpiece.setWorkpieceName(rs.getString(2));
				workpiece.setWorkpieceRfid(rs.getString(3));
				workpiece.setTime(rs.getTimestamp(4));
				workpiece.setFlag(rs.getString(5));
				workpiece.setUserId(rs.getString(6));
				workpiece.setFinish(rs.getString(7));
				workpiece.setProcessCount(rs.getInt(8));
				workpiece.setIsBanding(rs.getString(9));
				workpieceList.add(workpiece);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{	
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return workpieceList;
	}
	/**
	 * 获取所有的零件名字，但不重复
	 * @return
	 */
	public  List<Workpiece> getWorkpieceNames(){
		List<Workpiece> workpieceList = new ArrayList<Workpiece>();
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		String sql = "select distinct workpieceName ,flag from workpiece";
		try{
			ResultSet rs = DbUtil.executeQuery(stm, sql);
			while( rs.next()){
				Workpiece workpiece = new Workpiece();
				
				workpiece.setWorkpieceName(rs.getString(1));
				workpiece.setFlag(rs.getString(2));
				workpieceList.add(workpiece);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{	
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return workpieceList;
	}
	
	/**
	 * 新加的函数，与用户有关
	 * 获取所有未
	 * @return
	 */
	public  List<Workpiece> getWorkpieceNamesByUserId(String userId){
		List<Workpiece> workpieceList = new ArrayList<Workpiece>();
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		String sql = "select distinct workpieceName ,flag from workpiece where userId='"+userId+"'";
		try{
			ResultSet rs = DbUtil.executeQuery(stm, sql);
			while( rs.next()){
				Workpiece workpiece = new Workpiece();
				
				
				workpiece.setWorkpieceName(rs.getString(1));
			
			
				workpiece.setFlag(rs.getString(2));
				
				workpieceList.add(workpiece);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{	
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return workpieceList;
	}
	
	/**
	 * 获取所有未
	 * @return
	 */
	public  List<Workpiece> getWorkpieces(){
		List<Workpiece> workpieceList = new ArrayList<Workpiece>();
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		String sql = "select * from workpiece";
		try{
			ResultSet rs = DbUtil.executeQuery(stm, sql);
			while( rs.next()){
				Workpiece workpiece = new Workpiece();
				
				workpiece.setWorkpieceId(rs.getString(1));
				workpiece.setWorkpieceName(rs.getString(2));
				workpiece.setWorkpieceRfid(rs.getString(3));
				workpiece.setTime(rs.getTimestamp(4));
				workpiece.setFlag(rs.getString(5));
				workpiece.setUserId(rs.getString(6));
				workpiece.setFinish(rs.getString(7));
				workpiece.setProcessCount(rs.getInt(8));
				workpiece.setIsBanding(rs.getString(9));
				workpieceList.add(workpiece);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{	
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return workpieceList;
	}
	
	/**
	 * 新加的函数，与用户有关
	 * 获取所有未
	 * @return
	 */
	public  List<Workpiece> getWorkpiecesByUserId(String userId){
		List<Workpiece> workpieceList = new ArrayList<Workpiece>();
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		String sql = "select * from workpiece where userId='"+userId+"'";
		try{
			ResultSet rs = DbUtil.executeQuery(stm, sql);
			while( rs.next()){
				Workpiece workpiece = new Workpiece();
				
				workpiece.setWorkpieceId(rs.getString(1));
				workpiece.setWorkpieceName(rs.getString(2));
				workpiece.setWorkpieceRfid(rs.getString(3));
				workpiece.setTime(rs.getTimestamp(4));
				workpiece.setFlag(rs.getString(5));
				workpiece.setUserId(rs.getString(6));
				workpiece.setFinish(rs.getString(7));
				workpiece.setProcessCount(rs.getInt(8));
				workpiece.setIsBanding(rs.getString(9));
				workpieceList.add(workpiece);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{	
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return workpieceList;
	}
	
	
	
	/**
	 * 新加的函数
	 * 获取所有不同名字的零件，代表不同类(某一个用户的)
	 * @return
	 */
	public  List<Workpiece> getWorkpiecesByDifferentNameAndUserId(String userId){
		List<Workpiece> workpieceList = new ArrayList<Workpiece>();
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		String sql = "select distinct workpieceName from workpiece where userId ='"+userId+"'";
		try{
			ResultSet rs = DbUtil.executeQuery(stm, sql);
			while( rs.next()){
				Workpiece workpiece = new Workpiece();
				workpiece.setWorkpieceName(rs.getString("workpieceName"));
				workpieceList.add(workpiece);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{	
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return workpieceList;
	}
	/**
	 * 获取所有不同名字的零件，代表不同类
	 * @return
	 */
	public  List<Workpiece> getWorkpiecesByDifferentName(){
		List<Workpiece> workpieceList = new ArrayList<Workpiece>();
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		String sql = "select distinct workpieceName from workpiece";
		try{
			ResultSet rs = DbUtil.executeQuery(stm, sql);
			while( rs.next()){
				Workpiece workpiece = new Workpiece();
				workpiece.setWorkpieceName(rs.getString("workpieceName"));
				workpieceList.add(workpiece);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{	
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return workpieceList;
	}
	
	
	public  List<Workpiece> getWorkpiecesByMachId(String machId){
		List<Workpiece> workpieceList = new ArrayList<Workpiece>();
		Connection conn =null;
		Statement stm1 = null;
		Statement stm2 = null;
		Statement stm3 = null;
		conn = DbUtil.getConnection();
		try{
			stm1 = DbUtil.createStatemnt(conn);
			String sql1 = "select distinct workpieceId from workpiece_process where machId ='"+machId+"'";
			ResultSet rs1 = DbUtil.executeQuery(stm1, sql1);
			while( rs1.next()){
				stm2 = DbUtil.createStatemnt(conn);
				String sql2 = "select * from workpiece where workpieceId ='"+rs1.getString("workpieceId")+"'";
				ResultSet rs2 = stm2.executeQuery(sql2);
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
					workpiece.setIsBanding(rs2.getString(9));
					workpieceList.add(workpiece);
				}
				
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{	
			DbUtil.close(stm1);
			DbUtil.close(stm2);
			DbUtil.close(stm3);
			DbUtil.close(conn);
		}
		return workpieceList;
	}

	/**
	 * 这种方式添加零件，不会自动更新同名零件的工序，每个零件的工序都要手动配置
	 * @param workpiece
	 * @return
	 */
	public boolean addWorkpiece(Workpiece workpiece){
		boolean flag = false;
		String sql1 = "insert into workpiece(workpieceId,workpieceName,workpieceRfid,time,flag,userId,finish,processCount) values(?,?,?,now(),'0',?,'0',0)";
		Connection conn = DbUtil.getConnection();
		PreparedStatement pstm = DbUtil.preparedStatement(conn,sql1);
		try{
			
			pstm.setString(1,workpiece.getWorkpieceId());
			pstm.setString(2, workpiece.getWorkpieceName());
			pstm.setString(3, workpiece.getWorkpieceRfid());
			//添加用户信息
			pstm.setString(4, workpiece.getUserId());
			int rs = pstm.executeUpdate();
			if(rs!=0){
				System.out.println("添加零件成功");
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
	/**
	 * 添加零件，同时会根据之前的零件，自动插入工序
	 * @param workpiece
	 * @return
	 */
	public boolean addWorkpiece2(Workpiece workpiece){
		boolean flag = false;
		String workpieceFlag="0";
		int c =this.getProcessCountByWorkpieceName(workpiece.getWorkpieceName(),workpiece.getUserId());
		if(c==-1){
			c=0;
		}
		Connection conn = null;
		PreparedStatement pstm1 = null;
		PreparedStatement pstm2= null;
		PreparedStatement pstm3= null;
		PreparedStatement pstm4= null;
		ResultSet rs =null;
		try{
			conn = DbUtil.getConnection();
			conn.setAutoCommit(false);
			//满足这个条件，才copy工序
			if(c>0){
				//先找出一个同名零件的id
				String sql1 = "select * from workpiece where workpieceName=? and userId =?";
				pstm1 = DbUtil.preparedStatement(conn,sql1);
				pstm1.setString(1, workpiece.getWorkpieceName());
				pstm1.setString(2, workpiece.getUserId());
				rs = pstm1.executeQuery();
				if(rs.next()){
					String id = rs.getString(1);
					workpieceFlag =rs.getString(5);
					//根据id，找出所有工序
					String sql2 = "select * from workpiece_process where workpieceId=? ";
					pstm2 = DbUtil.preparedStatement(conn,sql2);
					pstm2.setString(1, id);
					rs = null;
					rs = pstm2.executeQuery();
					while(rs.next()){
						String processName =rs.getString(3);
						String processId =rs.getString(4);
						String machId =rs.getString(5);
						int level =rs.getInt(6);
						String workpieceId =workpiece.getWorkpieceId();
						String sql3 = "insert into workpiece_process(Num,workpieceId,processName,processId,machId,level,state) values(null,?,?,?,?,?,'0')";
						pstm3=null;
						pstm3 = DbUtil.preparedStatement(conn,sql3);
						pstm3.setString(1, workpieceId );
						pstm3.setString(2, processName );
						pstm3.setString(3, processId );
						pstm3.setString(4, machId );
						pstm3.setInt(5, level );
						pstm3.executeUpdate();	
					}
				}
				
				
			}
			//添加零件
			String sql4 = "insert into workpiece(workpieceId,workpieceName,workpieceRfid,time,flag,userId,processCount) values(?,?,?,now(),?,?,?)";
			pstm4 = DbUtil.preparedStatement(conn,sql4);
			pstm4.setString(1,workpiece.getWorkpieceId());
			pstm4.setString(2, workpiece.getWorkpieceName());
			pstm4.setString(3, workpiece.getWorkpieceRfid());
			pstm4.setString(4, workpieceFlag);
			//添加用户信息
			pstm4.setString(5, workpiece.getUserId());
			pstm4.setInt(6, c);
			int r = pstm4.executeUpdate();
			if(r!=0){
				System.out.println("添加零件成功");
			}
			flag = true;
			conn.commit();
			conn.setAutoCommit(true);
			
		}catch(Exception e){
			System.out.println("出现意外");
			e.printStackTrace();
			
		}finally{
			DbUtil.close(pstm1);
			DbUtil.close(pstm2);
			DbUtil.close(pstm3);
			DbUtil.close(pstm4);
			DbUtil.close(conn);
		}
		return flag;
	}
	
	public boolean deleteWorkpieceByWorkpieceId(String workpieceId){
		boolean flag = false;
		
		Connection conn = DbUtil.getConnection();
		PreparedStatement pstm1 = null; 
		PreparedStatement pstm2 = null;
		PreparedStatement pstm3 = null;
		
		try{
			conn.setAutoCommit(false);
			String sql1 = "delete from workpiece where workpieceId = ? ";
			pstm1 = DbUtil.preparedStatement(conn,sql1);
			pstm1.setString(1,workpieceId );
			int rs1 = pstm1.executeUpdate();
			
			String sql2 = "delete from workpiece_process where workpieceId = ? ";
			pstm2 = DbUtil.preparedStatement(conn,sql2);
			pstm2.setString(1,workpieceId );
			int rs2 = pstm2.executeUpdate();
			
			String sql3 = "delete from event where workpieceId = ? ";
			pstm3 = DbUtil.preparedStatement(conn,sql3);
			pstm3.setString(1,workpieceId );
			int rs3 = pstm3.executeUpdate();
			
			if(rs1!=0){
				System.out.println("删除零件成功");
			}
			if(rs2!=0){
				System.out.println("删除零件的工序成功");
			}else{
				System.out.println("删除零件的工序不成功，或者其还未配置工序");
			}
			if(rs3!=0){
				System.out.println("删除零件的事件成功");
			}else{
				System.out.println("删除零件的事件不成功，或者其还未配置工序");
			}
			flag = true;
			conn.commit();
			conn.setAutoCommit(true);
		}catch(Exception e){
			System.out.println("删除零件出现意外");
			e.printStackTrace();
			
		}finally{
			DbUtil.close(pstm1);
			DbUtil.close(pstm2);
			DbUtil.close(conn);
		}
		return flag;
	}
	public boolean confirmWorkpieceByWorkpieceId(String workpieceId){
		boolean flag = false;
		
		Connection conn = DbUtil.getConnection();
		PreparedStatement pstm1 = null; 
		
		
		try{
			conn.setAutoCommit(false);
			String sql1 = " update workpiece set flag ='1' where workpieceId = ? ";
			pstm1 = DbUtil.preparedStatement(conn,sql1);
			pstm1.setString(1,workpieceId );
			int rs1 = pstm1.executeUpdate();
			
		
			if(rs1!=0){
				System.out.println("零件工序确定成功");
			}
			flag = true;
			conn.commit();
			conn.setAutoCommit(true);
		}catch(Exception e){
			System.out.println("零件工序确定出现意外");
			e.printStackTrace();
			
		}finally{
			DbUtil.close(pstm1);
			DbUtil.close(conn);
		}
		return flag;
	}
	public boolean confirmWorkpieceByWorkpieceName(String workpieceName){
		boolean flag = false;
		
		Connection conn = DbUtil.getConnection();
		PreparedStatement pstm1 = null; 
		
		
		try{
			conn.setAutoCommit(false);
			String sql1 = " update workpiece set flag ='1' where workpieceName = ? ";
			pstm1 = DbUtil.preparedStatement(conn,sql1);
			pstm1.setString(1,workpieceName);
			int rs1 = pstm1.executeUpdate();
			
		
			if(rs1!=0){
				System.out.println("零件工序确定成功");
			}
			flag = true;
			conn.commit();
			conn.setAutoCommit(true);
		}catch(Exception e){
			System.out.println("零件工序确定出现意外");
			e.printStackTrace();
			
		}finally{
			DbUtil.close(pstm1);
			DbUtil.close(conn);
		}
		return flag;
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
	/**
	 * 根据零件名，返回其可用的机床集（当零件配置一道工序，则其可用机床减一）
	 * @param workpieceName
	 * @return
	 */
	 
	public List<Machine> getAvailMach4WProcessByWName(String workpieceName){
		Connection conn = DbUtil.getConnection();
		PreparedStatement pstm1 = null;
		PreparedStatement pstm2 = null;
		PreparedStatement pstm3 = null;
		List<Machine> machineList = new ArrayList<Machine>();
		ResultSet rs1 =null;
		ResultSet rs2 =null;
		ResultSet rs3 =null;
		try{
			String sql1 = " select workpieceId from  workpiece  where workpieceName = ? ";
			pstm1 = DbUtil.preparedStatement(conn,sql1);
			pstm1.setString(1,workpieceName );
			rs1 = pstm1.executeQuery();
			if(rs1.next()){
				String workpieceId= rs1.getString("workpieceId");
				System.out.println("配置工序，更新机床列表，根据名字取得零件id"+workpieceId);
				String sql2 = " select machId from  workpiece_process  where workpieceId= ? ";
				pstm2 = DbUtil.preparedStatement(conn,sql2);
				pstm2.setString(1,workpieceId );
				rs2 = pstm2.executeQuery();
				StringBuffer temp = new StringBuffer();
				while(rs2.next()){
					String machId = rs2.getString("machId");
					System.out.println("配置工序，更新机床列表，该零件已经使用的机床的id："+machId);
					temp.append("and machId != '"+machId+"'");
					
				}
				String sql3 = " select * from machine  where machId !='abc' "+temp.toString();
				System.out.println(sql3);
				pstm3 = DbUtil.preparedStatement(conn,sql3);
				rs3 = pstm3.executeQuery();
				while(rs3.next()){
					Machine m =new Machine();
					m.setMachId(rs3.getString(3));
					m.setFlag(rs3.getString(2));
					m.setMachName(rs3.getString(1));
					machineList.add(m);
				}
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}finally{
			DbUtil.close(pstm1);
			DbUtil.close(conn);
		}
		return machineList;
	}
	/**
	 * 根据零件名，得到其目前配置的工序个数
	 * @param workpieceName
	 * @return
	 */
	public int getProcessCountByWorkpieceName(String workpieceName){
		int c =-1;
		Connection conn = DbUtil.getConnection();
		PreparedStatement pstm1 = null; 
		try{
			String sql1 = " select distinct processCount from  workpiece  where workpieceName = ? ";
			pstm1 = DbUtil.preparedStatement(conn,sql1);
			pstm1.setString(1,workpieceName );
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
	/**
	 * 根据零件名，得到其目前配置的工序个数，同时要约束在某个用户之下
	 * @param workpieceName
	 * @return
	 */
	public int getProcessCountByWorkpieceName(String workpieceName,String userId){
		int c =-1;
		Connection conn = DbUtil.getConnection();
		PreparedStatement pstm1 = null; 
		try{
			String sql1 = " select distinct processCount from  workpiece  where workpieceName = ? and userId =? ";
			pstm1 = DbUtil.preparedStatement(conn,sql1);
			pstm1.setString(1,workpieceName );
			pstm1.setString(2,userId );
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
	public List<Workpiece> getFinishedWorkpieceByWorkpieceList(List<Workpiece> workpieceList){
		List<Workpiece> workpieceList2 = new ArrayList<Workpiece>();
		for(Workpiece w:workpieceList){
			if(w.getFinish().equals("1")){
				workpieceList2.add(w);
			}
		}
		return workpieceList2;
	}
	
}
