

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AvailableAppointments
 */
@WebServlet("/AvailableAppointments")
public class AvailableAppointments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AvailableAppointments() {
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
		
		String doctor_id = request.getParameter("doctor_id");
		String appointment_date = request.getParameter("appointment_date");
		
		String query = "WITH RECURSIVE free_slots(start_time) AS ( "
				+ "SELECT start_time "
				+ "FROM doctors "
				+ "WHERE doctor_id = ? "
				+ "UNION "
				+ "SELECT free_slots.start_time + doctors.slot_time "
				+ "FROM free_slots, doctors "
				+ "WHERE doctor_id = ? AND free_slots.start_time + doctors.slot_time < doctors.end_time) "
				+ "SELECT free_slots.start_time FROM free_slots "
				+ "WHERE NOT EXISTS (SELECT * "
				+ "FROM appointment, treatment "
				+ "WHERE appointment.treatment_id = treatment.treatment_id AND treatment.doctor_id = ? AND appointment.start_time::time = free_slots.start_time "
				+ "AND appointment.start_time::date = TO_DATE(?,\'YYYY-MM-DD\'))";
		
		String res = DbHelper.executeQueryJson(query, 
				new DbHelper.ParamType[] {DbHelper.ParamType.STRING,DbHelper.ParamType.STRING,DbHelper.ParamType.STRING,DbHelper.ParamType.STRING}, 
				new String[] {doctor_id,doctor_id,doctor_id,appointment_date});
		
		PrintWriter out = response.getWriter();
		out.print(res);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
