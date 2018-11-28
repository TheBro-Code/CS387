

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TreatmentDetail
 */
@WebServlet("/TreatmentDetail")
public class TreatmentDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TreatmentDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if(session.getAttribute("userid") == null) { //not logged in
			response.sendRedirect("LoginServlet");
		}
		
		String treatment_id = request.getParameter("treatment_id").toString();
		String currentOnly =  request.getParameter("current").toString();
		
		
		
		int t_id = Integer.parseInt(treatment_id);
		
		System.out.println(t_id);
		
		String query = "";
		String res = "";
		if(currentOnly.equals("true")) {
			query = "SELECT appointment_id, start_time "
					+ "FROM appointment "
					+ "WHERE treatment_id = ?";
		}
		else {
			query = "SELECT appointment_id, start_time "
					+ "FROM appointment "
					+ "WHERE treatment_id = ?";
		}
		
		res = DbHelper.executeQueryJson(query, 
				new DbHelper.ParamType[] {DbHelper.ParamType.INT}, 
				new Object[] {t_id});
		
		PrintWriter out = response.getWriter();
		out.print(res);
	}

}
