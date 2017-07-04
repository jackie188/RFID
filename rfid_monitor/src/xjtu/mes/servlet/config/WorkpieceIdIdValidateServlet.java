package xjtu.mes.servlet.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import xjtu.mes.manager.RfidSystemManager;
import xjtu.mes.manager.WorkpieceManager;
import xjtu.mes.model.User;

/**
* @author 姜文雷
* @version 创建时间：2016年11月7日 下午9:40:09
* 类说明
*/
@WebServlet("/WorkpieceIdIdValidateServlet")
public class WorkpieceIdIdValidateServlet extends HttpServlet {

	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		//这里把用户信息写入
		HttpSession session = request.getSession();
		User user = (User)session.getAttribute("user_info");
		if(null == user){
			response.sendRedirect("/rfid_monitor/errorPage.jsp");
			return ;
		}
		String workpieceId = user.getUserId()+request.getParameter("workpieceId");
		if(WorkpieceManager.getInstance().findWorkpieceByworkpieceId( workpieceId)){
			response.getWriter().print(true);
		}else{
			response.getWriter().print(false);
		}
	}
}
