package xjtu.mes.servlet.trackWorkpiece;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import xjtu.mes.manager.EventManager;
import xjtu.mes.manager.MachManager;
import xjtu.mes.manager.WorkpieceManager;
import xjtu.mes.manager.WorkpieceProcessManager;
import xjtu.mes.model.Event;
import xjtu.mes.model.Machine;
import xjtu.mes.model.Workpiece;
import xjtu.mes.model.WorkpieceProcess;

/**
* @author 姜文雷
* @version 创建时间：2016年11月14日 下午7:18:50
* 类说明
*/
@WebServlet("/DetialWorkpieceProcessServlet")
public class DetialWorkpieceProcessServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		JSONObject jsonObj = new JSONObject();
		JSONArray rows = new JSONArray();
		String workpieceId= req.getParameter("workpieceId");
		List<WorkpieceProcess> workpieceProcessList = new ArrayList<WorkpieceProcess>();
		workpieceProcessList = WorkpieceProcessManager.getInstance().getAllProcessByWorkpieceId(workpieceId);
		
		if(workpieceProcessList.isEmpty()){
			resp.getWriter().print("0");
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
				cell.put("workpieceId",w.getWorkpieceId());
				Workpiece workpiece = WorkpieceManager.getInstance().getWorkpieceByworkpieceId(w.getWorkpieceId());
				cell.put("workpieceName", workpiece.getWorkpieceName());
				//工序信息
				cell.put("processName", w.getProcessName());
				cell.put("processId", w.getProcessId());
				//机床信息
				cell.put("machId", w.getMachId());
				Machine machine = MachManager.getInstance().getMachByMachId(w.getMachId());
				cell.put("machName", machine.getMachName());
				
				cell.put("level", w.getLevel());
				cell.put("state", w.getState());
				List<Event> eventList = new ArrayList<Event>();
				eventList = EventManager.getInstance().getEventsByWorkpieceIdAndprocessId(workpieceId,  w.getProcessId());
				for(Event e:eventList){
					String time =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(e.getDate());
					event.put(e.getEvent(),time);
					
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
				cell.put("event",event);
				rows.add(cell);	
			}
			jsonObj.put("rows", rows);
			System.out.println(jsonObj.toString());
			resp.getWriter().print(jsonObj.toString());	
		}
	
		
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
}
