package rfid;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.alien.enterpriseRFID.tags.Tag;
import com.alien.enterpriseRFID.util.Converters;

import db.MyPool3;
import xjtu.mes.model.MachRfid;
import xjtu.mes.model.Machine;
import xjtu.mes.model.RfidSystem;
import xjtu.mes.model.Workpiece;
import xjtu.mes.model.WorkpieceProcess;
import xjtu.mes.util.DbUtil;

public class EventDeal extends Thread {
	 private Tag[] tagList = null;
	 private String rfidIp =null;
	 private String machId =null;
	 private boolean runSwitch =true;
	 //用来监听循环是否卡在某处了，且没报错
	 private long count=0;
	 public boolean isRunSwitch() {
		return runSwitch;
	}
	public void setRunSwitch(boolean runSwitch) {
		this.runSwitch = runSwitch;
	}
	//数据库线程池
	 private static MyPool3 pool =new MyPool3();
	 //定时器，定时去反应线程的活动情况
	
	private  List<WorkpieceProcess> M_workpieceProcessList = new ArrayList<WorkpieceProcess>();
	private  List<Machine> M_machineList = new ArrayList<Machine>();
	private  List<Workpiece> M_workpieceList = new ArrayList<Workpiece>();
	private  List<RfidSystem> M_rfidSystemList = new ArrayList<RfidSystem>();
	private  List<MachRfid> M_machRfidList = new ArrayList<MachRfid>();
	
	public  void  pourDateToMem(){
		Log4JManager.getInstance().writeLog("循环读取数据库，开始");
		delay(1000);
		M_machineList .removeAll(M_machineList);
		M_workpieceList.removeAll(M_workpieceList);
		M_workpieceProcessList .removeAll(M_workpieceProcessList);
		M_rfidSystemList.removeAll(M_rfidSystemList);
		M_machRfidList.removeAll(M_machRfidList);
		Connection conn1 =null;
		PreparedStatement pstm  =null;
	
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		ResultSet rs5 = null;
		try {
			
			conn1 = pool.getConnection();
			
			
			String sql ="select * from machine";
			pstm =conn1.prepareStatement(sql);
			rs1 = pstm.executeQuery(sql);
			while(rs1.next()){
				Machine machine = new Machine();
				machine.setMachName(rs1.getString(1));
				machine.setFlag(rs1.getString(2));
				machine.setMachId(rs1.getString(3));
				M_machineList.add(machine);
			}
			DbUtil.close(pstm);
			/*conn2 = pool.getConnection();*/
			sql ="select * from workpiece";
			pstm =conn1.prepareStatement(sql);
			rs2 = pstm.executeQuery(sql);
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
				M_workpieceList.add(workpiece);
			}
			DbUtil.close(pstm);
			sql ="select * from workpiece_process";
			pstm =conn1.prepareStatement(sql);
			rs3 = pstm.executeQuery(sql);
			boolean onlyOne=true;
			while(rs3.next()){
				WorkpieceProcess workpieceProcess = new WorkpieceProcess();
				workpieceProcess.setNum(rs3.getInt(1));
				workpieceProcess.setWorkpieceId(rs3.getString(2));
				workpieceProcess.setProcessName(rs3.getString(3));
				workpieceProcess.setProcessId(rs3.getString(4));
				workpieceProcess.setMachId(rs3.getString(5));
				workpieceProcess.setLevel(rs3.getInt(6));
				workpieceProcess.setState(rs3.getInt(7));
				//System.out.println("工序"+rs3.getInt(1)+"的状态"+rs3.getInt(7));
				if(onlyOne){
					onlyOne=false;
					Log4JManager.getInstance().writeLog("工序"+rs3.getInt(1)+"的状态"+rs3.getInt(7));
				}
				
				/*if(rs3.getInt(1)==73){
				Log4JManager.getInstance().writeLog("工序"+rs3.getInt(1)+"的状态"+rs3.getInt(7));
				}*/
				M_workpieceProcessList.add(workpieceProcess);
			}
			DbUtil.close(pstm);
			//必须报用于读写的剔除掉
			sql ="select * from rfidsystem where rfidSysId !='writer'";
			pstm =conn1.prepareStatement(sql);
			rs4 = pstm.executeQuery(sql);
			while( rs4.next()){
				RfidSystem rfidSystem = new RfidSystem();
				rfidSystem.setRfidSysId(rs4.getString(1));
				rfidSystem.setRfidName(rs4.getString(2));
				rfidSystem.setRfidModel(rs4.getString(3));
				rfidSystem.setRfidPort(rs4.getInt(4));
				rfidSystem.setRfidIp(rs4.getString(5));
				//System.out.println(rs.getString(5));
				rfidSystem.setFlag(rs4.getString(6));
				M_rfidSystemList.add(rfidSystem);
			}
			DbUtil.close(pstm);
			sql ="select * from mach_rfid";
			pstm =conn1.prepareStatement(sql);
			rs5 = pstm.executeQuery(sql);
			while( rs5.next()){
				MachRfid machRfid = new MachRfid();
				machRfid .setRfidSysId(rs5.getString(2));
				machRfid .setMachId(rs5.getString(1));
				M_machRfidList.add(machRfid);
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log4JManager.getInstance().writeLog(e.getMessage());
			Log4JManager.getInstance().writeLog("循环读取数据库到内存出问题,中断自己，开启新的线程");
			this.runSwitch=false;
			//不用手动主动中断试试
			//delay(1000);
			//this.interrupt();
		//	pourDateToMem();
		}catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log4JManager.getInstance().writeLog(e.getMessage());
			Log4JManager.getInstance().writeLog("循环读取数据库到内存出问题2，中断自己，开启新的线程");
			this.runSwitch=false;
			//不用手动主动中断试试
			//delay(1000);
			//this.interrupt();
			//pourDateToMem();
		}finally{
			Log4JManager.getInstance().writeLog("循环读取数据库，结束");
			DbUtil.close(rs1);
			DbUtil.close(rs2);
			DbUtil.close(rs3);
			DbUtil.close(rs4);
			DbUtil.close(rs5);
			DbUtil.close(conn1);
			DbUtil.close(pstm);
			/*DbUtil.close(stm1);
			DbUtil.close(stm2);
			DbUtil.close(stm3);
			DbUtil.close(stm4);
			DbUtil.close(stm5);
			DbUtil.close(conn1);
			DbUtil.close(conn2);
			DbUtil.close(conn3);
			DbUtil.close(conn4);
			DbUtil.close(conn5);*/
			
		}
	}
	public static void delay(long s){
		try {
			Thread.sleep(s);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public String getMachId() {
		return machId;
	}

	public String getRfidIp() {
		return rfidIp;
	}

	public void setRfidIp(String rfidIp) {
		this.rfidIp = rfidIp;
	}

	public Tag[] getTagList() {
		return tagList;
	}

	public void setTagList(Tag[] tagList) {
		this.tagList = tagList;
	}
	public long getCount() {
		return count;
	}
	public void setCount(long count) {
		this.count = count;
	}
	public void setMachId() {
			String rfidSysId=null;
			for(RfidSystem rf: M_rfidSystemList){
				if(rf.getRfidIp().equals(this.rfidIp)){
					rfidSysId =rf.getRfidSysId();
				}
			}
			for(MachRfid mf: M_machRfidList){
				if(mf.getRfidSysId().equals(rfidSysId)){
					this.machId =mf.getMachId();
					System.out.println("rfid设备绑定机床成功");
					//Log4JManager.getInstance().writeLog("rfid设备绑定机床成功");
				}
			}
			if(machId ==null){
				Log4JManager.getInstance().writeLog("该rfid设备尚未绑定机床");
				System.out.println("该rfid设备尚未绑定机床");
			}
			
	}
					
	
	public void dealProcess(String workpieceId, String processId,int state, int num,int level){
		
		/*Connection conn =DbUtil.getConnection();
		while(conn ==null){
			conn =DbUtil.getConnection();
		}*/
		Connection conn =null;
		PreparedStatement pstm = null;
		
		
		Tag tag =null;
		boolean flag =false;
		
		try{
			conn =pool.getConnection();
			String sql="";
			/*String sql_2 = "select * from workpiece where workpieceId=? ";*/
			
			Workpiece workpiece =null;
			for(Workpiece w:M_workpieceList){
				if(w.getWorkpieceId().equals(workpieceId)){
					workpiece = w;
				}
			}
			
			if(workpiece!=null){
				conn.setAutoCommit(false);
				String tagId =workpiece.getWorkpieceRfId().replaceAll(" ", "");
				for(Tag t :this.tagList){
					String tagId_send = t.getTagID().replaceAll(" ", "");
					
					byte[] h =Converters.fromHexString(tagId_send);//"6161"->0x61 0x61 0x61 0x61<-
					tagId_send = new String(h);//0x61 0x61 0x61 0x61->"aaaa"
					Log4JManager.getInstance().writeLog("比较 tagId_send:"+tagId_send+"  和tagId:"+tagId);
					
					if(tagId_send.equals(tagId)){
						//数据库中的被追踪零件出现，
						flag =true;
						tag =t;
						Log4JManager.getInstance().writeLog("出现位置天线:"+t.getAntenna());
						//数据库中的被追踪零件出现在天线上
						break;
						
						//重大bug，一旦出现就没必要在循环下去
					}
				}
				if(flag){
					int antenna =tag.getAntenna();
					Log4JManager.getInstance().writeLog("出现位置天线:"+antenna);
					//数据库中的被追踪零件出现在天线上
					
					if(state==0&&antenna==1){
						//表示上次处于初始化状态，且现在在天线1了
						state++;
						//更新状态状态量
						sql = "update workpiece_process set state =? where Num=?";
						pstm =conn.prepareStatement(sql);
						pstm.setInt(1, state);
						pstm.setInt(2, num);
						pstm.executeUpdate();
						DbUtil.close(pstm);
						
						//出发事件（出入缓存等事件），写入到数据库
						sql = "insert into event(workpieceId,processId,event,time) values(?,?,?,now())";
						pstm =conn.prepareStatement(sql);
						pstm.setString(1,  workpieceId);
						pstm.setString(2,  processId);
						pstm.setInt(3, state);
						pstm.executeUpdate();
						
						
						conn.commit();
						conn.setAutoCommit(true);
						System.out.println("写入到event事件");
						System.out.println("标记2.1：零件"+workpieceId+" 的工序"+ processId +"状态更新为"+state);
						Log4JManager.getInstance().writeLog("标记2.1：零件"+workpieceId+" 的工序"+ processId +"状态更新为"+state);
						
						
						pourDateToMem();
					}
					else if(state==1&&antenna!=1){
						//表示上次处于初始化状态，且现在在天线1了
						state++;
						//更新状态状态量
						sql = "update workpiece_process set state =? where Num=?";
						pstm =conn.prepareStatement(sql);
						pstm.setInt(1, state);
						pstm.setInt(2, num);
						pstm.executeUpdate();
						DbUtil.close(pstm);
						
						//出发事件（出入缓存等事件），写入到数据库
						sql = "insert into event(workpieceId,processId,event,time) values(?,?,?,now())";
						pstm =conn.prepareStatement(sql);
						pstm.setString(1,  workpieceId);
						pstm.setString(2,  processId);
						pstm.setInt(3, state);
						pstm.executeUpdate();
						conn.commit();
						conn.setAutoCommit(true);
						System.out.println("标记2.2：零件"+workpieceId+" 的工序"+ processId +"状态更新为"+state);
						Log4JManager.getInstance().writeLog("标记2.2：零件"+workpieceId+" 的工序"+ processId +"状态更新为"+state);
						
						pourDateToMem();
					}
					else if(state==2&&antenna==2){
						//表示上次处于初始化状态，且现在在天线1了
						state++;
						//更新状态状态量
						sql = "update workpiece_process set state =? where Num=?";
						pstm =conn.prepareStatement(sql);
						pstm.setInt(1, state);
						pstm.setInt(2, num);
						pstm.executeUpdate();
						DbUtil.close(pstm);
						
						//出发事件（出入缓存等事件），写入到数据库
						sql = "insert into event(workpieceId,processId,event,time) values(?,?,?,now())";
						pstm =conn.prepareStatement(sql);
						pstm.setString(1,  workpieceId);
						pstm.setString(2,  processId);
						pstm.setInt(3, state);
						pstm.executeUpdate();
						conn.commit();
						conn.setAutoCommit(true);
						System.out.println("标记2.3：零件"+workpieceId+" 的工序"+ processId +"状态更新为"+state);
						Log4JManager.getInstance().writeLog("标记2.3：零件"+workpieceId+" 的工序"+ processId +"状态更新为"+state);
						
						pourDateToMem();
					}
					else if(state==3&&antenna!=2){
						//表示上次处于初始化状态，且现在在天线1了
						state++;
						//更新状态状态量
						sql = "update workpiece_process set state =? where Num=?";
						pstm=conn.prepareStatement(sql);
						pstm.setInt(1, state);
						pstm.setInt(2, num);
						pstm.executeUpdate();
						DbUtil.close(pstm);
						
						//出发事件（出入缓存等事件），写入到数据库
						sql= "insert into event(workpieceId,processId,event,time) values(?,?,?,now())";
						pstm=conn.prepareStatement(sql);
						pstm.setString(1,  workpieceId);
						pstm.setString(2,  processId);
						pstm.setInt(3, state);
						pstm.executeUpdate();
						conn.commit();
						conn.setAutoCommit(true);
						System.out.println("标记2.4：零件"+workpieceId+" 的工序"+ processId +"状态更新为"+state);
						Log4JManager.getInstance().writeLog("标记2.4：零件"+workpieceId+" 的工序"+ processId +"状态更新为"+state);
						
						pourDateToMem();
					}else if(state==4&&antenna==3){
						//表示上次处于初始化状态，且现在在天线1了
						state++;
						//更新状态状态量
						sql= "update workpiece_process set state =? where Num=?";
						pstm=conn.prepareStatement(sql);
						pstm.setInt(1, state);
						pstm.setInt(2, num);
						pstm.executeUpdate();
						DbUtil.close(pstm);
						
						//出发事件（出入缓存等事件），写入到数据库
						sql = "insert into event(workpieceId,processId,event,time) values(?,?,?,now())";
						pstm =conn.prepareStatement(sql);
						pstm.setString(1,  workpieceId);
						pstm.setString(2,  processId);
						pstm.setInt(3, state);
						pstm.executeUpdate();
						conn.commit();
						conn.setAutoCommit(true);
						System.out.println("标记2.5：零件"+workpieceId+" 的工序"+ processId +"状态更新为"+state);
						Log4JManager.getInstance().writeLog("标记2.5：零件"+workpieceId+" 的工序"+ processId +"状态更新为"+state);
						
						pourDateToMem();
					}
					else if(state==5&&antenna!=3){
						//表示上次处于初始化状态，且现在在天线1了
						state++;
						//更新状态状态量
						sql = "update workpiece_process set state =? where Num=?";
						pstm =conn.prepareStatement(sql);
						pstm.setInt(1, state);
						pstm.setInt(2, num);
						pstm.executeUpdate();
						DbUtil.close(pstm);
						
						//出发事件（出入缓存等事件），写入到数据库
						sql = "insert into event(workpieceId,processId,event,time) values(?,?,?,now())";
						pstm =conn.prepareStatement(sql);
						pstm.setString(1,  workpieceId);
						pstm.setString(2,  processId);
						pstm.setInt(3, state);
						pstm.executeUpdate();
						conn.commit();
						conn.setAutoCommit(true);
						System.out.println("标记2.6：零件"+workpieceId+" 的工序"+ processId +"状态更新为"+state);
						Log4JManager.getInstance().writeLog("标记2.6：零件"+workpieceId+" 的工序"+ processId +"状态更新为"+state);
						
						pourDateToMem();
					}else{
						
					}
				}else{
					//数据库中的被追踪这个零件没出现在标签列表中
					if(state==1||state ==3||state ==5){
						state++;
						//更新状态状态量
						sql = "update workpiece_process set state =? where Num=?";
						pstm=conn.prepareStatement(sql);
						pstm.setInt(1, state);
						pstm.setInt(2, num);
						pstm.executeUpdate();
						DbUtil.close(pstm);
						//出发事件（出入缓存等事件），写入到数据库
						sql = "insert into event(workpieceId,processId,event,time) values(?,?,?,now())";
						pstm =conn.prepareStatement(sql);
						pstm.setString(1,  workpieceId);
						pstm.setString(2,  processId);
						pstm.setInt(3, state);
						pstm.executeUpdate();
						
						Log4JManager.getInstance().writeLog("标记3：零件"+workpieceId+" 的工序"+ processId +"状态更新为"+state);
						System.out.println("标记3：零件"+workpieceId+" 的工序"+ processId +"状态更新为"+state);
						if(state == 6&&level==workpiece.getProcessCount()){
							//更新零件是完工了，好像还不行，工序是实时增加的，没有结尾(因为强行让其，工序配置完毕后才可以被追踪可以实现了)
							sql = "update workpiece set finish=? where workpieceId=?";
							pstm=conn.prepareStatement(sql);
							pstm.setString(1,"1");
							pstm.setString(2, workpieceId);
							pstm.executeUpdate();
							
							Log4JManager.getInstance().writeLog("标记3：零件"+workpieceId+" 的加工完成");
							System.out.println("标记3：零件"+workpieceId+" 加工完成");
							
						}
						conn.commit();
						conn.setAutoCommit(true);
						pourDateToMem();
					}
				}
			}
			
		}catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			Log4JManager.getInstance().writeLog(e.getMessage());
		}finally{
			DbUtil.close(pstm);
			
			DbUtil.close(conn);
		}
	}
	
	public boolean isAvailableWorkpiece(String workpieceId){
		boolean flag =false;
		Workpiece workpiece = null;
		
		for(Workpiece w:M_workpieceList){
			if(w.getWorkpieceId().equals(workpieceId)){
				workpiece =w;
			}
		}
		if(workpiece!=null&&workpiece.getFlag().equals("1")){
					flag =true;
		}
		return flag;
	}
	@Override
	public void run() {
		System.out.println("开启数据分析线程循环");
		Log4JManager.getInstance().writeLog("开启数据分析线程循环");
		while(runSwitch){
			this.count++;
			Log4JManager.getInstance().writeLog("我还活着,累加器为"+this.count);
			pourDateToMem();
			if(this.runSwitch){
				//Log4JManager.getInstance().writeLog("读取数据库正常结束");
				//Log4JManager.getInstance().writeLog("刷新数据");
				//保证获得设备的ip后再去数据库mach_rifd设置machid,同时循环一秒设置一下machid，保证实时根据数据库中的绑定关系来确定机床
				if( rfidIp!=null){
					setMachId();
					//Log4JManager.getInstance().writeLog(this.rfidIp+"绑定到机床"+getMachId());
					//System.out.println(this.rfidIp+"绑定到机床"+getMachId());
				}
				if(machId!=null){
					Connection conn1 =null;
					PreparedStatement pstm1 = null;
					
					if(tagList==null||tagList.length == 0){
						Log4JManager.getInstance().writeLog("没标签");
						try{
							
							/*String sql1 = "select * from workpiece_process where machId ='"+machId+"'";*/
							List<WorkpieceProcess> workpieceProcessList = new ArrayList<WorkpieceProcess>();
							for(WorkpieceProcess wp:M_workpieceProcessList){
								if(wp.getMachId().equals(this.machId)){
									workpieceProcessList.add(wp);
								}
							}
							for(WorkpieceProcess wp:workpieceProcessList){					
								int state = wp.getState();
								int num = wp.getNum();
								
								String workpieceId =wp.getWorkpieceId();
								String processId = wp.getProcessId();
								int level = wp.getLevel();
								//新版本追加的过滤层
								if(isAvailableWorkpiece(workpieceId)){
									Workpiece workpiece =null;
									for(Workpiece w:M_workpieceList){
										if(w.getWorkpieceId().equals(workpieceId)){
											workpiece=w;
										}
											
									}
									//如果之前的状态表示其在天线上（count为1,3,5），而此时却没有了数据，表明离开，则更新count状态量
									if(state==1||state ==3||state ==5){
										state++;
										//更新状态状态量
										conn1 = pool.getConnection();
										conn1.setAutoCommit(false);
										
										String sql1 = "update workpiece_process set state =? where Num=?";
										pstm1 =conn1.prepareStatement(sql1);
										pstm1.setInt(1, state);
										pstm1.setInt(2, num);
										pstm1.executeUpdate();
										DbUtil.close(pstm1);
										//触发事件（出入缓存等事件），写入到数据库
										sql1 = "insert into event(workpieceId,processId,event,time) values(?,?,?,now())";
										pstm1=conn1.prepareStatement(sql1);
										pstm1.setString(1,workpieceId);
										pstm1.setString(2,processId);
										pstm1.setInt(3, state);
										pstm1.executeUpdate();
										DbUtil.close(pstm1);
										Log4JManager.getInstance().writeLog("标记1：零件"+workpieceId+" 的工序"+ processId +"状态更新为"+state);
										System.out.println("标记1：零件"+workpieceId+" 的工序"+ processId +"状态更新为"+state);
										
										if(state == 6&&level==workpiece.getProcessCount()){
											//更新零件是完工了，好像还不行，工序是实时增加的，没有结尾(因为强行让其，工序配置完毕后才可以被追踪可以实现了)
											sql1 = "update workpiece set finish=? where workpieceId=?";
											pstm1 =conn1.prepareStatement(sql1);
											pstm1.setString(1,"1");
											pstm1.setString(2, workpieceId);
											pstm1.executeUpdate();
											System.out.println("标记1：零件"+workpieceId+"完成");
											Log4JManager.getInstance().writeLog("标记1：零件"+workpieceId+"完成");
										}
										
										conn1.commit();
										conn1.setAutoCommit(true);
										DbUtil.close(pstm1);
										DbUtil.close(conn1);
										pourDateToMem();
									}
								}
								
							}
						}catch(SQLException e){
							e.printStackTrace();
							Log4JManager.getInstance().writeLog(e.getMessage());
							
						}finally{
							DbUtil.close(pstm1);
							DbUtil.close(conn1);
							
						}
					}else{
						Log4JManager.getInstance().writeLog("有标签");
						/*String sql_1 = "select * from workpiece_process where machId ='"+machId+"'";*/
						List<WorkpieceProcess> workpieceProcessList = new ArrayList<WorkpieceProcess>();
						for(WorkpieceProcess wp:M_workpieceProcessList){
							if(wp.getMachId().equals(this.machId)){
								workpieceProcessList.add(wp);
							}
						}
						for(WorkpieceProcess wp:workpieceProcessList){
							String workpieceId = wp.getWorkpieceId();
							//新版本追加的过滤层
							if(isAvailableWorkpiece(workpieceId)){
								int state = wp.getState();
								int num = wp.getNum();
								int level = wp.getLevel();
								String processId = wp.getProcessId();
								if(0<state&&state<6){
									//处理该工序;
									dealProcess( workpieceId, processId,state, num,level);
								}else{
									if(state==0){
										if(level==1){
											//处理该工序;
											dealProcess( workpieceId, processId,state, num,level);
										}else{
											/*String sql_2 = "select * from workpiece_process where  workpieceId=? and level =?";*/
											
											WorkpieceProcess workpieceProcess =null;
											for(WorkpieceProcess wp2:M_workpieceProcessList){
												if(wp2.getWorkpieceId().equals(workpieceId) && wp2.getLevel()==(level-1) ){
													workpieceProcess=wp2;
												}
											}
											if(workpieceProcess.getState()==6){
												//处理该工序;
												dealProcess( workpieceId, processId,state, num,level);
											}
											//此时不处理	
										}
									}else{
										//此时为6不处理	
									}
								}
							}
						
						}
					}
				}
			}
		}
	}
	
}
