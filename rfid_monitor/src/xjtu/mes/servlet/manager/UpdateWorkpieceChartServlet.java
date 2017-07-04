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
* @version 创建时间：2016年11月16日 下午6:25:37
* 类说明
*/
@WebServlet("/UpdateWorkpieceChartServlet")
public class UpdateWorkpieceChartServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String workpieceName =req.getParameter("workpieceName");
		int w1 = 0;//零件完成个数
		int w2 = 0;//零件未完成个数
		int w_1 = 0;//零件完成个数
		int w_2 = 0;//零件未完成个数
		//首先获取与机床零件的完成率
		
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user_info");
		if(user == null){
			resp.sendRedirect("/rfid_monitor/errorPage.jsp");
		}
		List<Workpiece> workpieceList = new ArrayList<Workpiece>();
		if(user.getUserId().equals("admin")){
			workpieceList = WorkpieceManager.getInstance().getWorkpiecesByName(workpieceName);
			if(workpieceList.size()==0){
				//该机床还未绑定任何工序,到此结束
				resp.getWriter().print("0");
			}else{
				w1 = WorkpieceManager.getInstance().getFinishedWorkpieceByWorkpieceList(workpieceList).size();
				w2 = workpieceList.size()-w1;
				
				List<Workpiece> workpieceList2 = new ArrayList<Workpiece>();
				workpieceList2 =WorkpieceManager.getInstance().getWorkpieces();
				w_1=WorkpieceManager.getInstance().getFinishedWorkpieceByWorkpieceList(workpieceList2).size();
				w_2 = workpieceList2.size()-w_1;
				JSONObject cell = new JSONObject();
				cell.put("w1",w1);
				cell.put("w2",w2);
				cell.put("w_1",w_1);
				cell.put("w_2",w_2);
				System.out.println(cell.toString());
				resp.getWriter().print(cell.toString());	
			}
		}else{
			workpieceList = WorkpieceManager.getInstance().getWorkpiecesByNameAndUserId(workpieceName,user.getUserId());
			if(workpieceList.size()==0){
				//当前用户还未配置任何零件
				resp.getWriter().print("0");
			}else{
				w1 = WorkpieceManager.getInstance().getFinishedWorkpieceByWorkpieceList(workpieceList).size();
				w2 = workpieceList.size()-w1;
				
				List<Workpiece> workpieceList2 = new ArrayList<Workpiece>();
				workpieceList2 =WorkpieceManager.getInstance().getWorkpiecesByUserId(user.getUserId());
				w_1=WorkpieceManager.getInstance().getFinishedWorkpieceByWorkpieceList(workpieceList2).size();
				w_2 = workpieceList2.size()-w_1;
				JSONObject cell = new JSONObject();
				cell.put("w1",w1);
				cell.put("w2",w2);
				cell.put("w_1",w_1);
				cell.put("w_2",w_2);
				System.out.println(cell.toString());
				resp.getWriter().print(cell.toString());	
			}
			
		}
		
		
		
		
		
	}
}
