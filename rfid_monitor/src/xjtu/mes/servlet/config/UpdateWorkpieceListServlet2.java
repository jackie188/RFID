package xjtu.mes.servlet.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import xjtu.mes.manager.WorkpieceManager;
import xjtu.mes.model.User;
import xjtu.mes.model.Workpiece;

/**
* @author 姜文雷
* @version 创建时间：2016年11月5日 下午3:44:56
* 类说明
* 查询当下可用的
*/
@WebServlet("/UpdateWorkpieceListServlet2")
public class UpdateWorkpieceListServlet2 extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Auto-generated method stub
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user_info");
		if(user == null){
			resp.sendRedirect("/rfid_monitor/errorPage.jsp");
			return ;
		}
		JSONObject jsonObj = new JSONObject();
		JSONArray rows = new JSONArray();
		List<Workpiece> workpieceList = new ArrayList<Workpiece>();
		//这里得到所有用户相关的零件,原方法
		/*if(user.getUserId().equals("admin")){
			workpieceList= WorkpieceManager.getInstance().getWorkpieces();
		}else{
			workpieceList = WorkpieceManager.getInstance().getWorkpiecesByUserId(user.getUserId());
		}*/
		
		//新方法，只得到不重复名字的零件，包含零件名和flag值
		if(user.getUserId().equals("admin")){
			workpieceList= WorkpieceManager.getInstance().getWorkpieceNames();
		}else{
			workpieceList = WorkpieceManager.getInstance().getWorkpieceNamesByUserId(user.getUserId());
		}
		if(workpieceList.isEmpty()){
			resp.getWriter().print("0");
		}else{
			for(Workpiece w: workpieceList){
				//零件
				
					JSONObject cell = new JSONObject();
					//新方法没有id值了，注释掉
					//cell.put("workpieceId",w.getWorkpieceId());
					cell.put("workpieceName", w.getWorkpieceName());
					cell.put("flag", w.getFlag());
					rows.add(cell);	
				
				
			}
			jsonObj.put("rows", rows);
			System.out.println(jsonObj.toString());
			resp.getWriter().print(jsonObj.toString());	
		}
		
	}
	

}
