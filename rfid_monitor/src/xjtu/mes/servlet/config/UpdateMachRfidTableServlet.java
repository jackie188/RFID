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
import xjtu.mes.model.MachRfid;

/**
* @author 姜文雷
* @version 创建时间：2016年11月21日 上午9:40:14
* 类说明
*/
@WebServlet("/UpdateMachRfidTableServlet")
public class UpdateMachRfidTableServlet extends HttpServlet {
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
		List<MachRfid> machRfids = new ArrayList<MachRfid>();
		machRfids = MachRfidManager.getInstance().getMachRfids();
		if(machRfids.isEmpty()){
			resp.getWriter().print("0");
		}else{
			for(MachRfid m : machRfids){
				JSONObject cell = new JSONObject();
				
				cell.put("machId", m.getMachId());
				cell.put("machName", m.getMachName());
				cell.put("rfidName", m.getRfidSysName());
				cell.put("rfidId", m.getRfidSysId());
				rows.add(cell);	
			}
			jsonObj.put("rows", rows);
			//System.out.println(jsonObj.toString());
			resp.getWriter().print(jsonObj.toString());	
		}
		
	}
}
