package xjtu.mes.servlet.UserManager;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xjtu.mes.manager.MachManager;
import xjtu.mes.manager.RfidSystemManager;
import xjtu.mes.manager.UserManager;
import xjtu.mes.model.Machine;
import xjtu.mes.model.RfidSystem;
import xjtu.mes.model.User;

/**
* @author 姜文雷
* @version 创建时间：2016年11月11日 下午2:55:23
* 类说明
*/
@WebServlet("/EditUserServlet")
public class EditUserServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;


	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String userId = req.getParameter("userId");
		String password = req.getParameter("password");
		String userName = req.getParameter("userName");
		
		User user = new User();
		user.setUserId(userId);
		user.setPassword(password);
		user.setUserName(userName);
		if(UserManager.getInstance().editUser(user)){
			resp.getWriter().print("success");
		}else{
			resp.getWriter().print("fail");
		}
		
	}
}
