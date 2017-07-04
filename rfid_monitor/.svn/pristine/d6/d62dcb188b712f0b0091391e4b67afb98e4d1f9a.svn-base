package xjtu.mes.manager;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import xjtu.mes.model.Event;
import xjtu.mes.util.DbUtil;

/**
* @author 姜文雷
* @version 创建时间：2016年11月14日 下午7:31:07
* 类说明
*/
public class EventManager {
	private static EventManager instance=null;
	protected EventManager(){}
	public  synchronized static EventManager getInstance(){
		if(instance==null){
			instance= new EventManager();
		}
		return instance;
	}
	
	
	public  List<Event> getEventsByWorkpieceId(String workpieceId){
		List<Event> eventList = new ArrayList<Event>();
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		String sql = "select * from event where workpieceId ='"+workpieceId+"'";
		try{
			ResultSet rs = DbUtil.executeQuery(stm, sql);
			while( rs.next()){
				Event event = new Event();
				event.setWorkpieceId(rs.getString(2));
				event.setProcessId(rs.getString(3));
				event.setEvent(rs.getString(4));
				event.setDate(rs.getTimestamp(5));
				eventList.add(event);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{	
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return eventList;
	}
	public  List<Event> getEventsByWorkpieceIdAndprocessId(String workpieceId,String processId){
		List<Event> eventList = new ArrayList<Event>();
		Connection conn = DbUtil.getConnection();
		Statement stm = DbUtil.createStatemnt(conn);
		String sql = "select * from event where workpieceId ='"+workpieceId+"' and processId='"+processId+"' order by event";
		try{
			ResultSet rs = DbUtil.executeQuery(stm, sql);
			while( rs.next()){
				Event event = new Event();
				event.setWorkpieceId(rs.getString(2));
				event.setProcessId(rs.getString(3));
				event.setEvent(rs.getString(4));
				//从小到大排列
				//System.out.println(rs.getString(4));
				event.setDate(rs.getTimestamp(5));
				//System.out.println(event.getDate());
				eventList.add(event);
			}
		}catch(SQLException e){
			e.printStackTrace();
		}
		finally{	
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
		return eventList;
	}
}
