package xjtu.mes.servlet.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xjtu.mes.manager.MachManager;
import xjtu.mes.manager.WorkpieceProcessManager;

/**
* @author 姜文雷
* @version 创建时间：2016年11月8日 下午4:44:27
* 类说明
*/
@WebServlet("/ProcessIdValidateServlet")
public class ProcessIdValidateServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String processId = request.getParameter("processId");
		String workpieceId = request.getParameter("workpieceId");
		if(WorkpieceProcessManager.getInstance().findProcessByWId_PId( workpieceId, processId ) ){
			response.getWriter().print(true);
		}else{
			response.getWriter().print(false);
		}
	}

}
