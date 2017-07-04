package xjtu.mes.servlet.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import xjtu.mes.manager.WorkpieceManager;
import xjtu.mes.model.User;
import xjtu.mes.model.Workpiece;

/**
* @author 姜文雷
* @version 创建时间：2016年11月8日 上午9:06:02
* 类说明
*/
@WebServlet("/AddWorkpieceServlet")
public class AddWorkpieceServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String workpieceName = req.getParameter("workpieceName");
		String workpieceId = req.getParameter("workpieceId");
		//String workpieceRfid = workpieceId;
		//这里把用户信息写入
		HttpSession session = req.getSession();
		User user = (User)session.getAttribute("user_info");
		if(null == user){
			resp.sendRedirect("/rfid_monitor/errorPage.jsp");
			return ;
		}
		//给零件的尾加上用户id，保证每个用户的零件一定不同。
		workpieceId =user.getUserId()+workpieceId;
		String workpieceRfid = workpieceId;
		Workpiece workpiece = new Workpiece (workpieceId  ,workpieceName,workpieceRfid, user.getUserId());
		
		/*if(WorkpieceManager.getInstance().addWorkpiece(workpiece)){
			resp.getWriter().print("success");
		}else{
			resp.getWriter().print("fail");
		}*/
		//方法2，自动更新工序
		if(WorkpieceManager.getInstance().addWorkpiece2(workpiece)){
			resp.getWriter().print("success");
		}else{
			resp.getWriter().print("fail");
		}
		
	}

}
