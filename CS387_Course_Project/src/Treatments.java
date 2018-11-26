

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Treatments
 */
@WebServlet("/Treatments")
public class Treatments extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Treatments() {
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
		
		String userid = (String) session.getAttribute("userid");
		String role = (String) session.getAttribute("role");
		
		String showResolved = request.getParameter("resolved");
		
		String query = "";
		String res = "";
		if(showResolved.equals("true")) {
			if(role.equals("doctor")) {
				query = "SELECT treatment_id, patient_id,start_time,end_time "
						+ "FROM treatment "
						+ "WHERE doctor_id = ? AND end_time IS NOT NULL";	
			}
			else {
				query = "SELECT treatment_id, patient_id,start_time,end_time "
						+ "FROM treatment "
						+ "WHERE patient_id = ? AND end_time IS NOT NULL";
			}
			
			res = DbHelper.executeQueryJson(query, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING}, 
					new String[] {userid});
			
			PrintWriter out = response.getWriter();
			out.print(res);
		}
		
		else {
			if(role.equals("doctor")) {
				query = "WITH treatment_helper(treatment_id,patient_id,start_time,next_appointment,rank) AS "
						+ "( SELECT t.treatment_id, patient_id, t.start_time, a.start_time, rank() over (PARTITION BY t.treatment_id ORDER BY a.start_time DESC) "
						+ "FROM treatment t, appointment a "
						+ "WHERE t.treatment_id = a.treatment_id AND doctor_id = ? AND end_time IS NULL) "
						+ "SELECT treatment_id, patient_id, start_time, next_appointment "
						+ "FROM treatment_helper "
						+ "WHERE rank = 1;"; 
			}
			else {
				query = "WITH treatment_helper(treatment_id,doctor_id,start_time,next_appointment,rank) AS "
						+ "( SELECT t.treatment_id, doctor_id, t.start_time, a.start_time, rank() over (PARTITION BY t.treatment_id ORDER BY a.start_time DESC) "
						+ "FROM treatment t, appointment a "
						+ "WHERE t.treatment_id = a.treatment_id AND patient_id = ? AND end_time IS NULL) "
						+ "SELECT treatment_id, doctor_id, start_time, next_appointment "
						+ "FROM treatment_helper "
						+ "WHERE rank = 1;";
			}
			
			res = DbHelper.executeQueryJson(query, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING}, 
					new String[] {userid});
			
			PrintWriter out = response.getWriter();
			out.print(res);
		}
	}

}
