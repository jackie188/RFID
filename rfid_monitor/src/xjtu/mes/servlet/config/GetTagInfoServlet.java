package xjtu.mes.servlet.config;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alien.enterpriseRFID.reader.AlienReaderException;

import xjtu.mes.manager.AlienClass1ReaderManager;
import xjtu.mes.manager.WorkpieceManager;

/**
 * Servlet implementation class GetTagInfoServlet
 */
@WebServlet("/GetTagInfoServlet")
public class GetTagInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public GetTagInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("utf-8");
		response.setCharacterEncoding("utf-8");
		String tagId="no";
		try {
				tagId =AlienClass1ReaderManager.getInstance().getTagId();
				System.out.println("read ok");
				System.out.println(tagId );
			
		} catch (AlienReaderException e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			System.out.println("read error");
		}
		response.getWriter().print(tagId);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
