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
* @version 创建时间：2016年11月21日 上午9:40:14
* 类说明
*/
@WebServlet("/UpdateRfidTableServlet")
public class UpdateRfidTableServlet extends HttpServlet {
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
		List<RfidSystem> rfidSystemList = new ArrayList<RfidSystem>();
		rfidSystemList = RfidSystemManager.getInstance().getRfidSystems();
		if(		rfidSystemList.isEmpty()){
			resp.getWriter().print("0");
		}else{
			for(RfidSystem r: rfidSystemList){
				JSONObject cell = new JSONObject();
				
				cell.put("rfidId", r.getRfidSysId());
				cell.put("rfidName", r.getRfidName());
				cell.put("rfidIp", r.getRfidIp());
				//System.out.println(r.getRfidIp());
				rows.add(cell);	
			}
			jsonObj.put("rows", rows);
			//System.out.println(jsonObj.toString());
			resp.getWriter().print(jsonObj.toString());	
		}
		
	}
}
