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
* @version 创建时间：2016年11月5日 下午8:34:17
* 类说明
*/
@WebServlet("/AddMachServlet")
public class AddMachServlet extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		req.setCharacterEncoding("utf-8");
		resp.setCharacterEncoding("utf-8");
		String machId = req.getParameter("machId");
		String machName = req.getParameter("machName");
		Machine machine = new Machine(machName, machId);
		if(MachManager.getInstance().addMach(machine)){
			resp.getWriter().print("success");
		}else{
			resp.getWriter().print("fail");
		}
		
	}

}
