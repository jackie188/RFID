package xjtu.mes.servlet.trackWorkpiece;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import xjtu.mes.manager.WorkpieceManager;
import xjtu.mes.manager.WorkpieceProcessManager;

import xjtu.mes.model.Workpiece;
import xjtu.mes.model.WorkpieceProcess;

/**
* @author 姜文雷
* @version 创建时间：2016年11月12日 下午6:32:52
* 类说明
*/
@WebServlet("/DetialWorkpieceServlet")
public class DetialWorkpieceServlet extends HttpServlet {

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
		Workpiece w = WorkpieceManager.getInstance().getWorkpieceByworkpieceId(workpieceId);
		if(w==null){
			resp.getWriter().print("0");
		}else{
				
				JSONObject cell = new JSONObject();
				//零件信息
				cell.put("workpieceId",w.getWorkpieceId());
				cell.put("workpieceName", w.getWorkpieceName());
				cell.put("workpieceRfid", w.getWorkpieceRfid());
				cell.put("processInfo", "已配置工艺数："+workpieceProcessList.size());
				rows.add(cell);	
			
			jsonObj.put("rows", rows);
			System.out.println(jsonObj.toString());
			resp.getWriter().print(jsonObj.toString());	
		}
	
		
	}

}
