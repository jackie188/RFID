package xjtu.mes.servlet.config;

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
import xjtu.mes.manager.MachManager;
import xjtu.mes.manager.WorkpieceManager;
import xjtu.mes.manager.WorkpieceProcessManager;
import xjtu.mes.model.Machine;
import xjtu.mes.model.Workpiece;
import xjtu.mes.model.WorkpieceProcess;

/**
* @author 姜文雷
* @version 创建时间：2016年11月11日 下午6:47:40
* 类说明
*/

@WebServlet("/ShowWorkpieceDetialServlet")
public class ShowWorkpieceDetialServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String workpieceId= request.getParameter("workpieceId");
		JSONObject jsonObj = new JSONObject();
		JSONArray rows = new JSONArray();
		List<WorkpieceProcess> workpieceProcessList = new ArrayList<WorkpieceProcess>();
		workpieceProcessList = WorkpieceProcessManager.getInstance().getAllProcessByWorkpieceId(workpieceId);
		
		if(workpieceProcessList.isEmpty()){
			response.getWriter().print("0");
		}else{
			for(WorkpieceProcess w: workpieceProcessList){
				JSONObject cell = new JSONObject();
				//零件信息
				cell.put("workpieceId",w.getWorkpieceId());
				
				Workpiece workpiece = WorkpieceManager.getInstance().getWorkpieceByworkpieceId(w.getWorkpieceId());
				cell.put("workpieceName", workpiece.getWorkpieceName());
				cell.put("flag", workpiece.getFlag());
				//工序信息
				cell.put("processName", w.getProcessName());
				cell.put("processId", w.getProcessId());
				//机床信息
				cell.put("machId", w.getMachId());
				Machine machine = MachManager.getInstance().getMachByMachId(w.getMachId());
				cell.put("machName", machine.getMachName());
				
				cell.put("level", w.getLevel());
				cell.put("state", w.getState());
				rows.add(cell);	
			}
			jsonObj.put("rows", rows);
			System.out.println(jsonObj.toString());
			response.getWriter().print(jsonObj.toString());	
		}
		
	}
	

}
