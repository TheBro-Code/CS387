

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AppointmentDetail
 */
@WebServlet("/AppointmentDetail")
public class AppointmentDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AppointmentDetail() {
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
		
		String appointment_id = (String) request.getParameter("appointment_id");
		
		int a_id = Integer.parseInt(appointment_id);
		System.out.println("Appointment ID clicked is " + a_id);
		
		String query = "SELECT a.appointment_id, reason_visit, start_time, comments, medicine_id, quantity "
				+ "FROM appointment a, prescription p "
				+ "WHERE a.appointment_id = p.appointment_id AND a.appointment_id = ?";
		
		String res = DbHelper.executeQueryJson(query, 
				new DbHelper.ParamType[] {DbHelper.ParamType.INT}, 
				new Object[] {a_id});
		
		PrintWriter out = response.getWriter();
		out.print(res);
	}
}
