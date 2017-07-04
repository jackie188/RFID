package xjtu.mes.servlet.manager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;
import xjtu.mes.manager.WorkpieceManager;
import xjtu.mes.manager.WorkpieceProcessManager;
import xjtu.mes.model.User;
import xjtu.mes.model.Workpiece;
/**
* @author 姜文雷
* @version 创建时间：2016年11月16日 下午4:00:05
* 类说明
*/
@WebServlet("/UpdateMachChartServlet")
public class UpdateMachChartServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String machId =req.getParameter("machId");
		System.out.print("更新机床图表");
		int w1 = 0;//零件完成个数
		int w2 = 0;//零件未完成个数
		int p0 = 0;//工序状态为0的个数
		int p1 = 0;//工序状态为1的个数
		int p2 = 0;//工序状态为2的个数
		int p3 = 0;//工序状态为3的个数
		int p4 = 0;//工序状态为4的个数
		int p5 = 0;//工序状态为5的个数
		int p6 = 0;//工序状态为6的个数
		//首先获取与机床零件的完成率
		List<Workpiece> workpieceList = new ArrayList<Workpiece>();
		//同时要保证与用户有关
		
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user_info");
		if(user == null){
			resp.sendRedirect("/rfid_monitor/errorPage.jsp");
			return ;
		}
		//这里得到所有用户相关的零件
		if(user.getUserId().equals("admin")){
			workpieceList= WorkpieceProcessManager.getInstance().getWorkpiecesByMachId(machId);
			if(workpieceList.size()==0){
				//该机床还未绑定任何工序,到此结束
				resp.getWriter().print("0");
			}else{
				System.out.println("时间点1"+System.currentTimeMillis());
				w1 = WorkpieceManager.getInstance().getFinishedWorkpieceByWorkpieceList(workpieceList).size();
				w2 = workpieceList.size()-w1;
				System.out.println("时间点2"+System.currentTimeMillis());
				int[] count={0,0,0,0,0,0,0};
				count = WorkpieceProcessManager.getInstance().getProcessCountByAllState(machId,workpieceList);
				p0=count[0];
				p1=count[1];
				p2=count[2];
				p3=count[3];
				p4=count[4];
				p5=count[5];
				p6=count[6];
				System.out.println("时间点3"+System.currentTimeMillis());
				JSONObject cell = new JSONObject();
				cell.put("w1",w1);
				cell.put("w2",w2);
				cell.put("p0",p0);
				cell.put("p1",p1);
				cell.put("p2",p2);
				cell.put("p3",p3);
				cell.put("p4",p4);
				cell.put("p5",p5);
				cell.put("p6",p6);
				System.out.println(cell.toString());
				resp.getWriter().print(cell.toString());	
			}
		}else{
			
			workpieceList = WorkpieceProcessManager.getInstance().getWorkpiecesByMachIdAndUserId(machId,user.getUserId());
			if(workpieceList.size()==0){
				//该机床还未绑定任何工序,到此结束
				resp.getWriter().print("0");
			}else{
				System.out.println("时间点1"+System.currentTimeMillis());
				w1 = WorkpieceManager.getInstance().getFinishedWorkpieceByWorkpieceList(workpieceList).size();
				w2 = workpieceList.size()-w1;
				System.out.println("时间点2"+System.currentTimeMillis());
				int count[]={0,0,0,0,0,0,0};
				count = WorkpieceProcessManager.getInstance().getProcessCountByAllState(machId,workpieceList);
				p0=count[0];
				p1=count[1];
				p2=count[2];
				p3=count[3];
				p4=count[4];
				p5=count[5];
				p6=count[6];
				
				JSONObject cell = new JSONObject();
				System.out.println("时间点3"+System.currentTimeMillis());
				cell.put("w1",w1);
				cell.put("w2",w2);
				cell.put("p0",p0);
				cell.put("p1",p1);
				cell.put("p2",p2);
				cell.put("p3",p3);
				cell.put("p4",p4);
				cell.put("p5",p5);
				cell.put("p6",p6);
				System.out.println("机床图表数据为"+cell.toString());
				resp.getWriter().print(cell.toString());	
			}
		}
		
		
		
		
	}
}
