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
import xjtu.mes.manager.MachRfidManager;
import xjtu.mes.manager.RfidSystemManager;
import xjtu.mes.model.MachRfid;
import xjtu.mes.model.RfidSystem;

/**
* @author 姜文雷
* @version 创建时间：2016年11月5日 下午4:56:49
* 类说明
*/
@WebServlet("/MachRfidListServlet")
public class MachRfidListServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		JSONObject jsonObj = new JSONObject();
		JSONArray rows = new JSONArray();
		List<MachRfid> machRfidList = new ArrayList<MachRfid>();
		machRfidList =  MachRfidManager.getInstance().getMachRfids(); 
		if(machRfidList.isEmpty()){
			resp.getWriter().print("0");
		}else{
			for(MachRfid r : machRfidList){
				JSONObject cell = new JSONObject();
				cell.put("machId", r.getMachId());
				cell.put("rfidSysId", r.getRfidSysId());
				cell.put("machName", r.getMachName());
				rows.add(cell);	
			}
			jsonObj.put("rows", rows);
			//System.out.println(jsonObj.toString());
			resp.getWriter().print(jsonObj.toString());	
		}
	}
	
	

}
