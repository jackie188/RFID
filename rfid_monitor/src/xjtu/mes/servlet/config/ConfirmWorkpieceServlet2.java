package xjtu.mes.servlet.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xjtu.mes.manager.WorkpieceManager;

/**
* @author 姜文雷
* @version 创建时间：2016年11月21日 下午12:15:41
* 类说明
*/
@WebServlet("/ConfirmWorkpieceServlet2")
public class ConfirmWorkpieceServlet2 extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String workpieceName= req.getParameter("workpieceName");
		if(WorkpieceManager.getInstance().confirmWorkpieceByWorkpieceName(workpieceName)){
			resp.getWriter().print("success");
		}else{
			resp.getWriter().print("fail");
		}
		
	}
}
