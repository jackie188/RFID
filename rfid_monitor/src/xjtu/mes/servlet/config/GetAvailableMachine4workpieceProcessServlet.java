package xjtu.mes.servlet.config;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import xjtu.mes.manager.WorkpieceManager;
import xjtu.mes.model.Machine;

/**
 * Servlet implementation class GetAvailableMachine4workpieceProcessServlet
 */
@WebServlet("/GetAvailableMachine4workpieceProcessServlet")
public class GetAvailableMachine4workpieceProcessServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetAvailableMachine4workpieceProcessServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String workpieceName= request.getParameter("selectWorkpieceName");
		List<Machine> machineList = new ArrayList<Machine>();
		JSONObject jsonObj = new JSONObject();
		JSONArray rows = new JSONArray();
		machineList = WorkpieceManager.getInstance().getAvailMach4WProcessByWName(workpieceName);
		if(machineList.isEmpty()){
			response.getWriter().print("0");	
		}else{
			
			for(Machine m : machineList){
				JSONObject cell = new JSONObject();
				cell.put("machId", m.getMachId());
				cell.put("machName", m.getMachName());
				rows.add(cell);	
			}
			jsonObj.put("rows", rows);
			System.out.println(jsonObj.toString());
			response.getWriter().print(jsonObj.toString());	
			
		}
	}

}
