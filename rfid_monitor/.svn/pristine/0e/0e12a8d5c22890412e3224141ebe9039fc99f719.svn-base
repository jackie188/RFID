package xjtu.mes.servlet.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xjtu.mes.manager.RfidSystemManager;
import xjtu.mes.manager.WorkpieceManager;

/**
* @author 姜文雷
* @version 创建时间：2016年11月9日 下午8:02:18
* 类说明
*/
@WebServlet("/DeleteWorkpieceServlet")
public class DeleteWorkpieceServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String workpieceId= req.getParameter("workpieceId");
		if(WorkpieceManager.getInstance().deleteWorkpieceByWorkpieceId(workpieceId)){
			resp.getWriter().print("success");
		}else{
			resp.getWriter().print("fail");
		}
		
	}
}
