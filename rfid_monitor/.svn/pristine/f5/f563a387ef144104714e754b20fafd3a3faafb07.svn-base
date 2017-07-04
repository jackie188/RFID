package xjtu.mes.servlet.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xjtu.mes.manager.MachManager;
import xjtu.mes.manager.RfidSystemManager;

/**
* @author 姜文雷
* @version 创建时间：2016年11月5日 下午8:18:28
* 类说明
*/
@WebServlet("/RfidIdValidateServlet")
public class RfidIdValidateServlet extends HttpServlet {

	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String rfidId = request.getParameter("rfidId");
		if(RfidSystemManager.getInstance().findRfidByRfidId(rfidId)){
			response.getWriter().print(true);
		}else{
			response.getWriter().print(false);
		}
	}

}
