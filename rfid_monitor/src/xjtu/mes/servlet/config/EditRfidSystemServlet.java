package xjtu.mes.servlet.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xjtu.mes.manager.MachManager;
import xjtu.mes.manager.RfidSystemManager;
import xjtu.mes.model.Machine;
import xjtu.mes.model.RfidSystem;

/**
* @author 姜文雷
* @version 创建时间：2016年11月11日 下午2:55:23
* 类说明
*/
@WebServlet("/EditRfidSystemServlet")
public class EditRfidSystemServlet extends HttpServlet {

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String rfidId = req.getParameter("rfidId");
		String rfidName = req.getParameter("rfidName");
		String rfidIp = req.getParameter("rfidIp");
		
		RfidSystem rfidSystem = new RfidSystem (rfidId ,rfidName,rfidIp  );
		if(RfidSystemManager.getInstance().editRfid(rfidSystem)){
			resp.getWriter().print("success");
		}else{
			resp.getWriter().print("fail");
		}
		
	}
}
