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
import xjtu.mes.manager.MachManager;
import xjtu.mes.manager.RfidSystemManager;
import xjtu.mes.model.Machine;
import xjtu.mes.model.RfidSystem;

/**
* @author 姜文雷
* @version 创建时间：2016年11月5日 下午7:30:42
* 类说明
*/
@WebServlet("/GetMachListServlet")
public class GetMachListServlet extends HttpServlet {
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
		List<Machine> machineList = new ArrayList<Machine>();
		machineList = MachManager.getInstance().getMachines(); 
		if(machineList.isEmpty()){
			resp.getWriter().print("0");
		}else{
			for(Machine m : machineList){
				JSONObject cell = new JSONObject();
				cell.put("machId", m.getMachId());
				cell.put("machName", m.getMachName());
				rows.add(cell);	
			}
			jsonObj.put("rows", rows);
			System.out.println(jsonObj.toString());
			resp.getWriter().print(jsonObj.toString());	
		}
		
	}
	
}
