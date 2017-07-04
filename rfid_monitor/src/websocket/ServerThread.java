package websocket;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.websocket.Session;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import xjtu.mes.model.Event;
import xjtu.mes.model.Machine;
import xjtu.mes.model.WorkpieceProcess;
import xjtu.mes.util.DbUtil;
import xjtu.mes.util.Log4JManager;
import xjtu.mes.util.MyPool;
import xjtu.mes.util.Mypool2;

public class ServerThread extends Thread{
	private boolean flag = false;
	//要特别主要这个变量的是用，一次while循环仅调用一次，防止因为在websocket的修改，使得一循环中两次不一致
	private String workpieceId =null;
	private Session session =null;
	private String newOne=null;
	private Mypool2 pool =new Mypool2();
	//需要三个集合来保存数据库的东西到内存，一次性搞定，避免多次连接数据库
	private List<WorkpieceProcess> M_workpieceProcessList = new ArrayList<WorkpieceProcess>();
	private List<Machine> M_machineList = new ArrayList<Machine>();
	private List<Event> M_eventList = new ArrayList<Event>();
	
	public void pourDateToMem(){
		Log4JManager.getInstance().writeLog("websockt的循环读取数据库,开始");
		this.M_workpieceProcessList .clear();
		this.M_machineList .clear();
		this.M_eventList .clear();
		Connection conn = null;
		Statement stm = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		try {
			delay(1000);
			conn = pool.getConnection();
			stm = conn.createStatement();
			
			String sql ="select * from machine";
			rs1 = stm.executeQuery(sql);
			while(rs1.next()){
				Machine machine = new Machine();
				machine.setMachName(rs1.getString(1));
				machine.setFlag(rs1.getString(2));
				machine.setMachId(rs1.getString(3));
				this.M_machineList.add(machine);
			}
			sql ="select * from event";
			rs2 = stm.executeQuery(sql);
			while(rs2.next()){
				Event event = new Event();
				event.setId(rs2.getInt(1));
				event.setWorkpieceId(rs2.getString(2));
				event.setProcessId(rs2.getString(3));
				event.setEvent(rs2.getString(4));
				//从小到大排列
				//System.out.println(rs.getString(4));
				event.setDate(rs2.getTimestamp(5));
				//System.out.println(event.getDate());
				this.M_eventList.add(event);
			}
			sql ="select * from workpiece_process order by Num";
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
				this.M_workpieceProcessList.add(workpieceProcess);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log4JManager.getInstance().writeLog(e.getMessage());
			Log4JManager.getInstance().writeLog("循环读取数据库到内存出问题,中断自己开启新的线程");
			this.flag=false;
			//delay(1000);
			//this.interrupt();
			//pourDateToMem();
		}
		catch( Exception e){
			e.printStackTrace();
			Log4JManager.getInstance().writeLog(e.getMessage());
			Log4JManager.getInstance().writeLog("循环读取数据库到内存出问题,中断自己开启新的线程");
			this.flag=false;
			//delay(1000);
			//this.interrupt();
			//pourDateToMem();
			
		}finally{
			DbUtil.close(rs1);
			DbUtil.close(rs2);
			DbUtil.close(rs3);
			DbUtil.close(stm);
			DbUtil.close(conn);
		}
				
		
	}
	
	public String getNewOne() {
		return newOne;
	}
	public void setNewOne(String newOne) {
		this.newOne = newOne;
	}
	public String getOldOne() {
		return oldOne;
	}
	public void setOldOne(String oldOne) {
		this.oldOne = oldOne;
	}

	private String oldOne=null;
	
	public ServerThread( ) {
		Log4JManager.getInstance().writeLog("websock的线程属性被创建");
		System.out.println("websock的线程属性被创建");
		
	}
	public ServerThread( String workpieceId,Session session) {
		this.setWorkpieceId(workpieceId);
		this.setSession(session);
		
	}
	public static String calculatTime(long milliSecondTime) {
		  long hour = milliSecondTime /(60*60*1000);
		  long minute = (milliSecondTime - hour*60*60*1000)/(60*1000);
		  long seconds = (milliSecondTime - hour*60*60*1000 - minute*60*1000)/1000;
		  
		  if(seconds >= 60 )
		  {
		   seconds = seconds % 60;
		      minute+=seconds/60;
		  }
		  if(minute >= 60)
		  {
		    minute = minute % 60;
		    hour  += minute/60;
		  }
		  String sh = "";
		  String sm ="";
		  String ss = "";
		  if(hour <10) {
		     sh = "0" + String.valueOf(hour);
		  }else {
		     sh = String.valueOf(hour);
		  }
		  if(minute <10) {
		     sm = "0" + String.valueOf(minute);
		  }else {
		     sm = String.valueOf(minute);
		  }if(seconds <10) {
			     ss = "0" + String.valueOf(seconds);
		  }else {
		     ss = String.valueOf(seconds);
		  }
		  return sh +"小时，"+sm+"分钟，"+ ss+"秒";
	}
	public String getTime(Date d1,Date d2){
		String time=null;
		long temp = d1.getTime() - d2.getTime(); //ms
		//System.out.println("测试时间函数");
		//System.out.println(temp);
		//System.out.println("测试时间函数");
		return calculatTime(temp);
	}
	@Override
	public void run() {
		while(this.flag){
				try{
					if(this.session!= null&&this.workpieceId!=null&&!this.workpieceId.equals("")){
						pourDateToMem();
						if(this.flag){
							Log4JManager.getInstance().writeLog("websockt的循环读取数据库，正常结束");
							JSONObject jsonObj = new JSONObject();
							JSONArray rows = new JSONArray();
							List<WorkpieceProcess> workpieceProcessList = new ArrayList<WorkpieceProcess>();
							//////////////////////从读数据库，到读内存的替换
							for(WorkpieceProcess wp:this.M_workpieceProcessList){
									if(wp.getWorkpieceId().equals(this.workpieceId)){
										workpieceProcessList.add(wp);
									}
							}
							/*workpieceProcessList = WorkpieceProcessManager.getInstance().getAllProcessByWorkpieceId(this.workpieceId);*/
							///////////////////
							if(workpieceProcessList.isEmpty()){
								newOne ="0";
							}else{
								for(WorkpieceProcess w: workpieceProcessList){
									JSONObject cell = new JSONObject();
									JSONObject event = new JSONObject();
									String allTime ="无数据";
									String machTime ="无数据";
									String inTime ="无数据";
									String outTime ="无数据";
									String startTime ="无数据";
									
									//零件信息
									/*cell.put("workpieceId",w.getWorkpieceId());
									Workpiece workpiece = WorkpieceManager.getInstance().getWorkpieceByworkpieceId(w.getWorkpieceId());
									cell.put("workpieceName", workpiece.getWorkpieceName());*/
									//工序信息
									cell.put("processName", w.getProcessName());
									cell.put("processId", w.getProcessId());
									//机床信息
									cell.put("machId", w.getMachId());
									
									//////////////////////从读数据库，到读内存的替换
									Machine machine =null;
									for(Machine m:this.M_machineList){
										if(m.getMachId().equals(w.getMachId())){
											machine=m;
										}
									}
									/*Machine machine = MachManager.getInstance().getMachByMachId(w.getMachId());*/
									//////////////////////从读数据库，到读内存的替换
									
									cell.put("machName", machine.getMachName());
									
									cell.put("level", w.getLevel());
									cell.put("state", w.getState());
									List<Event> eventList = new ArrayList<Event>();
									
									//////////////////////从读数据库，到读内存的替换
									for(Event e :this.M_eventList){
										if( e.getWorkpieceId().equals(w.getWorkpieceId())&&e.getProcessId().equals(w.getProcessId()) ){
											eventList.add(e);
										}
									}
									/*eventList = EventManager.getInstance().getEventsByWorkpieceIdAndprocessId(w.getWorkpieceId(),  w.getProcessId());*/
									//////////////////////从读数据库，到读内存的替换
									
									
									
									//System.out.println(eventList.size());
									for(Event e:eventList){
										String time =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(e.getDate());
										event.put(e.getEvent(),time);
										//times[Integer.parseInt(e.getEvent())]=time;
									}
									if(eventList.size()==6){
										allTime =getTime( eventList.get(5).getDate(), eventList.get(0).getDate() );
										machTime =getTime( eventList.get(3).getDate(), eventList.get(2).getDate() );
										inTime =getTime( eventList.get(1).getDate(), eventList.get(0).getDate() );
										outTime =getTime( eventList.get(5).getDate(), eventList.get(4).getDate() );
									}else if(eventList.size()>=4){
										machTime =getTime( eventList.get(3).getDate(), eventList.get(2).getDate() );
										inTime =getTime( eventList.get(1).getDate(), eventList.get(0).getDate() );
									}else if(eventList.size()>=2){
										inTime =getTime( eventList.get(1).getDate(), eventList.get(0).getDate() );
									}
									if(eventList.size()>0){
										startTime =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(eventList.get(0).getDate());
									}
									
									cell.put("startTime", startTime);
									cell.put("allTime", allTime);
									cell.put("machTime", machTime);
									cell.put("inTime", inTime);
									cell.put("outTime", outTime);
									/*System.out.println("测试时间函数");
									System.out.println(allTime);
									System.out.println(machTime);
									System.out.println(inTime);
									System.out.println(outTime);
									System.out.println("测试时间函数");*/
									cell.put("event",event);
									rows.add(cell);	
								}
								jsonObj.put("rows", rows);
								//System.out.println(jsonObj.toString());
								//session.getBasicRemote().sendText(jsonObj.toString());
								newOne = jsonObj.toString();
							}
							
							if(!oldOne.equals(newOne)&&this.session!=null){
								try {
									this.session.getBasicRemote().sendText(jsonObj.toString());
								} catch (IOException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
									Log4JManager.getInstance().writeLog(e.getMessage());
									System.out.println("websockt的循环线程异常结束");
									Log4JManager.getInstance().writeLog("websockt的循环线程异常结束");
								}
								System.out.println("websoket发送数据"+jsonObj.toString());
								Log4JManager.getInstance().writeLog("websoket发送数据"+jsonObj.toString());
								oldOne = newOne;
							}
						}
						
					}else{
						System.out.println("websockt的循环语句的第二个if,没用发零件id号");
						Log4JManager.getInstance().writeLog("websockt的循环语句的第二个if语句，没有发零件id号");	
					}
					
				}catch(Exception e){
					e.printStackTrace();
					System.out.println("websockt的循环线程异常结束");
					Log4JManager.getInstance().writeLog("websockt的循环线程异常结束");
					Log4JManager.getInstance().writeLog(e.getMessage());
				}
			}
			System.out.println("websockt的循环语句的外侧,没有设置flag为true，结束");
			Log4JManager.getInstance().writeLog("websockt的循环语句的外侧，没有设置flag为true，结束");	
			
		
	}

	public void delay(int x){
		try {
			Thread.sleep(x);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log4JManager.getInstance().writeLog("通过sleep中断线程");
			Log4JManager.getInstance().writeLog(e.getMessage());
		}
	}

	public boolean isFlag() {
		return flag;
	}



	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getWorkpieceId() {
		return workpieceId;
	}

	public void setWorkpieceId(String workpieceId) {
		this.workpieceId = workpieceId;
	}

	public Session getSession() {
		return session;
	}

	public void setSession(Session session) {
		this.session = session;
	}


	public static void main(String[] args) {
		//算法没问题
		System.out.println(calculatTime(Long.MAX_VALUE));
		//long s =Long.MAX_VALUE;
		long s =9223372036854775805l;
		long l =s/1000/60/60/24;
		System.out.println(l);
	}



	

}
