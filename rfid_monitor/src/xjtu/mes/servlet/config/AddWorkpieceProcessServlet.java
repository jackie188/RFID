package xjtu.mes.servlet.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xjtu.mes.manager.WorkpieceManager;
import xjtu.mes.manager.WorkpieceProcessManager;
import xjtu.mes.model.Workpiece;
import xjtu.mes.model.WorkpieceProcess;

/**
* @author 姜文雷
* @version 创建时间：2016年11月8日 下午5:13:47
* 类说明
*/
@WebServlet("/AddWorkpieceProcessServlet")
public class AddWorkpieceProcessServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String workpieceId = req.getParameter("workpieceId");
		String processName = req.getParameter("processName");
		String processId = req.getParameter("processId");
		String machId = req.getParameter("machId");
		//如果批量加入的话，这里要得到由零件名字得到的id集合，在循环调用下一行
		WorkpieceProcess workpieceProcess = new WorkpieceProcess (workpieceId ,processName,	processId ,machId );
	
		
		if(WorkpieceProcessManager.getInstance().addWorkpieceProcess(workpieceProcess)){
			resp.getWriter().print("success");
		}else{
			resp.getWriter().print("fail");
		}
		
	}
}
