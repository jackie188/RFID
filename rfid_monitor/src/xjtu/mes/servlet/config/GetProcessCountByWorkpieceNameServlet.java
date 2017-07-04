package xjtu.mes.servlet.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xjtu.mes.manager.MachManager;
import xjtu.mes.manager.WorkpieceManager;
import xjtu.mes.manager.WorkpieceProcessManager;
import xjtu.mes.model.Workpiece;
import xjtu.mes.model.WorkpieceProcess;

/**
* @author 姜文雷
* @version 创建时间：2016年11月8日 下午2:15:29
* 类说明
*/
@WebServlet("/GetProcessCountByWorkpieceNameServlet")
public class GetProcessCountByWorkpieceNameServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String workpieceName= request.getParameter("selectWorkpieceName");
		int  processCount = WorkpieceManager.getInstance().getProcessCountByWorkpieceName(workpieceName);
		if(processCount==-1){
			response.getWriter().print("fail");	
		}else{
			
			response.getWriter().print( processCount);
			
		}
		
	}
	
	

}
