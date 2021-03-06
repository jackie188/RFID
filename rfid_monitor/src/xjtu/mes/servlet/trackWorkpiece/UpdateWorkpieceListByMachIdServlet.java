
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
import xjtu.mes.model.Workpiece;

/**
* @author 姜文雷
* @version 创建时间：2016年11月12日 下午3:07:46
* 类说明
*/
@WebServlet("/UpdateWorkpieceListByMachIdServlet")
public class UpdateWorkpieceListByMachIdServlet extends HttpServlet {

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
		String machId = req.getParameter("machId");
		List<Workpiece> workpieceList = new ArrayList<Workpiece>();
		workpieceList = WorkpieceManager.getInstance().getWorkpiecesByMachId(machId);
		if(workpieceList.isEmpty()){
			resp.getWriter().print("0");
		}else{
			for(Workpiece w: workpieceList){
				JSONObject cell = new JSONObject();
				cell.put("workpieceId",w.getWorkpieceId());
				cell.put("workpieceName", w.getWorkpieceName());
				rows.add(cell);	
			}
			jsonObj.put("rows", rows);
			System.out.println(jsonObj.toString());
			resp.getWriter().print(jsonObj.toString());	
		}
		
	}

}
