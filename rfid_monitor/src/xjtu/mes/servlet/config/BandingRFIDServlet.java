package xjtu.mes.servlet.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alien.enterpriseRFID.reader.AlienClass1Reader;
import com.alien.enterpriseRFID.reader.AlienReaderException;

import xjtu.mes.manager.AlienClass1ReaderManager;
import xjtu.mes.manager.WorkpieceManager;

/**
 * Servlet implementation class BandingRFIDServlet
 */
@WebServlet("/BandingRFIDServlet")
public class BandingRFIDServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BandingRFIDServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		String number =request.getParameter("workpieceRfid");
		try {
			if(AlienClass1ReaderManager.getInstance().writeRFID(number)){
				System.out.println("write ok");
				//更新状态
				WorkpieceManager.getInstance().setWorkpieceBandingStateByworkpieceId(number);
				response.getWriter().print("ok");
			}else{
				response.getWriter().print("error");
				System.out.println("write error");
			}
		} catch (AlienReaderException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("write error");
			response.getWriter().print("error");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
