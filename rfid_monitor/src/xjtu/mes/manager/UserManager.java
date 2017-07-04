package xjtu.mes.manager;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import xjtu.mes.exception.UserNotFoundException;
import xjtu.mes.model.Machine;
import xjtu.mes.model.RfidSystem;
import xjtu.mes.model.User;
import xjtu.mes.util.DbUtil;

/**
* @author 姜文雷
* @version 创建时间：2016年11月17日 上午10:11:09
* 类说明
*/
public class UserManager {

	private static UserManager instance=null;
	protected UserManager(){}
	public  synchronized static UserManager getInstance(){
		if(instance==null){
			instance= new UserManager();
		}
		return instance;
	}
	
	/**
	 * 获取所有用户
	 * @return
	 */
	public  List<User> getUsers(){
		List<User> userList = new ArrayList<User>();
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		String sql = "select * from user";
		try{
			ResultSet rs = DbUtil.executeQuery(stm, sql);
			while( rs.next()){
				User user = new User();
				user.setUserId(rs.getString(1));
				user.setPassword(rs.getString(2));
				user.setUserName(rs.getString(3));
				userList.add(user);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{	
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return userList;
	}
	public boolean editUser(User user){
		boolean flag = false;
		String sql1 = "update user set  password = ?,username =? where userId= ?";
		Connection conn = DbUtil.getConnection();
		PreparedStatement pstm = DbUtil.preparedStatement(conn,sql1);
		try{
			
		
			pstm.setString(1, user.getPassword());
			pstm.setString(2, user.getUserName());
			pstm.setString(3, user.getUserId());
			int rs = pstm.executeUpdate();
			if(rs!=0){
				System.out.println("修改用户成功");
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
	
	public boolean deleteUserByUserId(String userId){
		boolean flag = false;
		PreparedStatement pstm =null;
		PreparedStatement pstm1 =null;
		PreparedStatement pstm2 =null;
		PreparedStatement pstm3 =null;
		PreparedStatement pstm4 =null;
		PreparedStatement pstm5 =null;
		ResultSet r = null;
		String sql1 = "delete from user where userId = ? ";
		Connection conn = DbUtil.getConnection();
		pstm = DbUtil.preparedStatement(conn,sql1);
		try{
			conn.setAutoCommit(false);
			pstm.setString(1,userId );
			int rs = pstm.executeUpdate();
			if(rs!=0){
				System.out.println("删除用户成功");
			}
			
			String sql2 = "select * from workpiece where userId = ? ";
			pstm2 = DbUtil.preparedStatement(conn,sql2);
			pstm2.setString(1,userId );
			r  = pstm2.executeQuery();
			while(r.next()){
				
				String workpieceId = r.getString("workpieceId");
				String sql3 = "delete workpiece set  where workpieceId = ? ";
				pstm3 = DbUtil.preparedStatement(conn,sql3);
				pstm3.setString(1, workpieceId);
				rs = pstm3.executeUpdate();
				if(rs != 0){
					System.out.println("删除用户相关的零件成功");
				}
				
				String sql4 = "delete from workpiece_process where workpieceId = ? ";
				pstm4 = DbUtil.preparedStatement(conn,sql4);
				pstm4.setString(1,workpieceId );
				rs = pstm4.executeUpdate();
				if(rs!=0){
					System.out.println("删除零件工序成功");
				}
				String sql5 = "delete from event where workpieceId = ? ";
				pstm5 = DbUtil.preparedStatement(conn,sql4);
				pstm5.setString(1,workpieceId );
				rs = pstm5.executeUpdate();
				if(rs!=0){
					System.out.println("删除工序事件成功");
				}
			}
			
			conn.commit();
			conn.setAutoCommit(true);
			flag = true;
			
		}catch(Exception e){
			System.out.println("出现意外");
			e.printStackTrace();
			
		}finally{
			DbUtil.close(r);
			DbUtil.close(pstm);
			DbUtil.close(pstm1);
			DbUtil.close(pstm2);
			DbUtil.close(pstm3);
			DbUtil.close(pstm4);
			DbUtil.close(pstm);
			DbUtil.close(conn);
		}
		return flag;
	}
	/**
	 * 通过用户ID和密码，来验证用户，可根据返回user的flag属性标志，判断结果
	 * flag：00-不存在，10-存在密码错误，-11存在密码正确
	 * @param userId
	 * @param password
	 * @return User
	 */
		public User checkUser(String userId){
			User user = null;
			Connection conn = DbUtil.getConnection();
			Statement stm = DbUtil.createStatemnt(conn);
			String sql = "select * from user where userId = '"+userId+"'";
			ResultSet rs = DbUtil.executeQuery(stm, sql);
			//sql语法错误，会导致rs为null，引起空指针错误。最好先判断rs是否为null，在进行下一步
			//System.out.println(rs);
			try{
				if(rs.next()){
						user =new User();
						user.setUserId(rs.getString(1));
						user.setUserName(rs.getString(3));
						user.setPassword(rs.getString(2));
						System.out.println("登陆成功");
						return user;
				}
				System.out.println("用户名不存在");
				throw new UserNotFoundException("用户不存在");
			}catch(SQLException e){
				e.printStackTrace();
				
			}finally{	
				DbUtil.close(stm);
				DbUtil.close(conn);
			}
			return user;
		}
}
