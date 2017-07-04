package xjtu.mes.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import xjtu.mes.manager.MachManager;

/**
* @author 姜文雷
* @version 创建时间：2016年11月18日 上午10:24:16
* 类说明
*/
@WebServlet("/LogoutServlet")
public class LogoutServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		HttpSession session = req.getSession();
		session.removeAttribute("user_info");
		//次跳向屏蔽登录
	//	resp.sendRedirect("/rfid_monitor/errorPage.jsp");
		//到登录界面
		resp.sendRedirect("/rfid_monitor/login.jsp");
		
	}
}
