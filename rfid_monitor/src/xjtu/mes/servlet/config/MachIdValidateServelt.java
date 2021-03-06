package xjtu.mes.servlet.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import xjtu.mes.manager.MachManager;

/**
* @author 姜文雷
* @version 创建时间：2016年11月5日 下午12:35:54
* 类说明 
* 判断机床的id是否存在的servlet
*/
@WebServlet("/MachIdValidateServlet")
public class MachIdValidateServelt extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String machId= request.getParameter("machId");
		if(MachManager.getInstance().findMachByMachId(machId)){
			response.getWriter().print(true);
		}else{
			response.getWriter().print(false);
		}
	}
	

}
