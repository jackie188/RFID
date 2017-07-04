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
* @version 创建时间：2016年11月7日 下午9:40:09
* 类说明
*/
@WebServlet("/WorkpieceRfIdIdValidateServlet")
public class WorkpieceRfIdIdValidateServlet extends HttpServlet {

	/**
	 * 
	 */ 
	private static final long serialVersionUID = 1L;
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String workpieceRfId = request.getParameter("workpieceRfId");
		if(WorkpieceManager.getInstance().findWorkpieceByworkpieceRfid( workpieceRfId)){
			response.getWriter().print(true);
		}else{
			response.getWriter().print(false);
		}
	}
}
