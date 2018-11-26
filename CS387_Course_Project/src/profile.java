

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class profile
 */
@WebServlet("/profile")
public class profile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public profile() {
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
		
		String update = request.getParameter("update");
		String user_view_id = request.getParameter("user_view_id");
		
		String query = "";
		String res="";
		
		if(update == null) {
			if(role.equals("doctor")) {
				if(userid.equals(user_view_id)) {
					query = "SELECT * from doctors,users where doctor_id = userid and doctor_id = ?; ";
					res = DbHelper.executeQueryJson(query, 
							new DbHelper.ParamType[] {DbHelper.ParamType.STRING}, 
							new String[] {userid});
				}
				else {
					query = "SELECT name,gender,blood_type,age,patient_id,blood_pressure,blood_sugar,cardiac_ailment,"
							+ "asthma,allergies,chronic_diseases,major_surgeries,long_term_med,transf_hist "
							+ "from patients,users where userid = patient_id and patient_id = ?; ";
					res = DbHelper.executeQueryJson(query, 
							new DbHelper.ParamType[] {DbHelper.ParamType.STRING}, 
							new String[] {user_view_id});
				}
			}
			else {
				if(user_view_id == null) {
					query = "SELECT * from patients,users where patient_id = userid and patient_id = ?; ";
					res = DbHelper.executeQueryJson(query, 
							new DbHelper.ParamType[] {DbHelper.ParamType.STRING}, 
							new String[] {userid});
				}
				else if(userid.equals(user_view_id)) {
					query = "SELECT * from patients,users where patient_id = userid and patient_id = ?; ";
					res = DbHelper.executeQueryJson(query, 
							new DbHelper.ParamType[] {DbHelper.ParamType.STRING}, 
							new String[] {userid});
				}
				else {
					query = "SELECT name,gender,age,doctor_id,qualifications,speciality,college,completion, "
							+ "experience, regnum, regcouncil, regyear, start_time,end_time,slot_time,hospital, "
							+ "hospital_address, fees,rating "
							+ "from doctors,users where userid = doctor_id and doctor_id = ?; ";
					res = DbHelper.executeQueryJson(query, 
							new DbHelper.ParamType[] {DbHelper.ParamType.STRING}, 
							new String[] {user_view_id});
				}
			}
		}
		
		else {
				
			}
			
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
