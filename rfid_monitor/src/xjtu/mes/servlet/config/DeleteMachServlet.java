package xjtu.mes.servlet.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import xjtu.mes.manager.MachManager;
import xjtu.mes.model.Machine;

/**
* @author 姜文雷
* @version 创建时间：2016年11月8日 下午9:16:35
* 类说明
*/
@WebServlet("/DeleteMachServlet")
public class DeleteMachServlet extends HttpServlet {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String machId = req.getParameter("machId");
		if(MachManager.getInstance().deleteMachByMachId(machId)){
			resp.getWriter().print("success");
		}else{
			resp.getWriter().print("fail");
		}
		
	}

}
