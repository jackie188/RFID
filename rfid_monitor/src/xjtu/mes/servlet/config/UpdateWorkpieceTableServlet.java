package xjtu.mes.servlet.config;

import java.io.IOException;
import java.text.SimpleDateFormat;
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
import xjtu.mes.manager.MachRfidManager;
import xjtu.mes.manager.WorkpieceManager;
import xjtu.mes.model.MachRfid;
import xjtu.mes.model.User;
import xjtu.mes.model.Workpiece;

/**
* @author 姜文雷
* @version 创建时间：2016年11月21日 上午9:40:14
* 类说明
*/
@WebServlet("/UpdateWorkpieceTableServlet")
public class UpdateWorkpieceTableServlet extends HttpServlet {
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
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user_info");
		if(user == null){
			resp.sendRedirect("/rfid_monitor/errorPage.jsp");
			return ;
		}
		List<Workpiece> workpieces = new ArrayList<Workpiece>();
		//这里得到所有用户相关的零件
		if(user.getUserId().equals("admin")){
			workpieces= WorkpieceManager.getInstance().getWorkpieces();
		}else{
			workpieces = WorkpieceManager.getInstance().getWorkpiecesByUserId(user.getUserId());
		}
		if(workpieces.isEmpty()){
			resp.getWriter().print("0");
		}else{
			for(Workpiece w : workpieces){
				JSONObject cell = new JSONObject();
				String time =new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(w.getTime());
				cell.put("workpieceId", w.getWorkpieceId());
				cell.put("workpieceName", w.getWorkpieceName());
				cell.put("workpieceRfid", w.getWorkpieceRfid());
				if(w.getIsBanding().equals("1")){
					cell.put("isBanding", "已绑定标签");
				}else{
					cell.put("isBanding", "未绑定标签");
				}
				cell.put("time", time);
				if(w.getFlag().equals("1")){
					cell.put("processState", "已配置完毕");
				}else{
					cell.put("processState", "未配置完毕");
				}
				rows.add(cell);	
			}
			jsonObj.put("rows", rows);
			//System.out.println(jsonObj.toString());
			resp.getWriter().print(jsonObj.toString());	
		}
		
	}
}
