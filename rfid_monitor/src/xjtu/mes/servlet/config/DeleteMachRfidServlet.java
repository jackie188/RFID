package xjtu.mes.servlet.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import xjtu.mes.manager.MachRfidManager;
import xjtu.mes.manager.WorkpieceManager;
import xjtu.mes.model.User;

/**
* @author 姜文雷
* @version 创建时间：2016年11月11日 下午4:00:24
* 类说明
*/
@WebServlet("/DeleteMachRfidServlet")
public class DeleteMachRfidServlet extends HttpServlet {

	/**
	 * 
	 */
	
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String rfidId= req.getParameter("rfidId");
		String machId= req.getParameter("machId");
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user_info");
		if(!user.getUserId().equals("admin")){
			resp.getWriter().print("不是管理员");
			return ;
		}else{
			if(MachRfidManager.getInstance().deleteMachRfid(rfidId,machId)){
				resp.getWriter().print("success");
				System.out.println("在servlet中删除机床和rfid成功");
			}else{
				resp.getWriter().print("fail");
			}
		}
		
		
	}
}
