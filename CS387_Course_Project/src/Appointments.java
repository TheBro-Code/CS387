import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Appointments
 */
@WebServlet("/Appointments")
public class Appointments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Appointments() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		if(session.getAttribute("userid") == null) { //not logged in
			response.sendRedirect("LoginServlet");
		}
		
		String userid = (String) session.getAttribute("userid");
		String role = (String) session.getAttribute("role");
		
		String query = "";
		String res = "";
		
		if(role.equals("doctor")) {
			query = "SELECT treatment.treatment_id,appointment_id,name,appointment.start_time::time AS start_time "
					+ "FROM treatment,appointment, users "
					+ "WHERE userid = treatment.patient_id and treatment.treatment_id = appointment.treatment_id "
					+ "AND appointment.start_time::date = now()::date AND appointment.start_time::time >= now()::time AND "
					+ "doctor_id = ?;";
		}
		else {
			query = "SELECT treatment.treatment_id,appointment_id,doctor_id,appointment.start_time::time AS start_time "
					+ "FROM treatment,appointment "
					+ "WHERE treatment.treatment_id = appointment.treatment_id "
					+ "AND appointment.start_time::date = now()::date AND "
					+ "patient_id = ?;";
		}
		
		res = DbHelper.executeQueryJson(query, 
				new DbHelper.ParamType[] {DbHelper.ParamType.STRING}, 
				new String[] {userid});
		
		PrintWriter out = response.getWriter();
		out.print(res);
		System.out.println(res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
