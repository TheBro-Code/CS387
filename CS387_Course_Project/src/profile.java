

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
			
			String newname = request.getParameter("newname");
			String gender = request.getParameter("gender");
			String age = request.getParameter("age");
			String blood_type = request.getParameter("blood_type");
			String passwd = request.getParameter("passwd");
			String house_no = request.getParameter("house_no");
			String street = request.getParameter("street");
			String state = request.getParameter("state");
			String pin_code = request.getParameter("pin_code");
			String phone_no = request.getParameter("phone_no");
			String blood_pressure = request.getParameter("blood_pressure");
			String blood_sugar = request.getParameter("blood_sugar");
			String cardiac_ailment = request.getParameter("cardiac_ailment");
			String asthma = request.getParameter("asthma");
			String allergies = request.getParameter("allergies");
			String chronic_diseases = request.getParameter("chronic_diseases");
			String major_surgeries = request.getParameter("major_surgeries");
			String long_term_med = request.getParameter("long_term_med");
			String transf_hist = request.getParameter("transf_hist");
			
			System.out.println(newname);
			System.out.println(gender);
			System.out.println(age);
			System.out.println(blood_type);
			System.out.println(passwd);
			System.out.println(house_no);
			System.out.println(street);
			System.out.println(state);
			System.out.println(pin_code);
			System.out.println(phone_no);
			System.out.println(blood_pressure);
			System.out.println(blood_sugar);
			System.out.println(cardiac_ailment);
			System.out.println(asthma);
			System.out.println(allergies);
			System.out.println(chronic_diseases);
			System.out.println(major_surgeries);
			System.out.println(long_term_med);
			System.out.println(transf_hist);
			
			String query1 = "";
			String query2 = "";
			
			if(role.equals("patient")) 
			{
				query1 = " update users set (name,"
						+ "gender, "
						+ "age, "
//						+ "blood_type,"
						+ "passwd, "
						+ "house_no, "
						+ "street, "
						+ "state, "
//						+ "pin_code, "
						+ "phone_no) = (?,?,?,?,?,?,?,?) "
						+ "where userid = ?;";
				
				String res1 = DbHelper.executeUpdateJson(query1, 
						new DbHelper.ParamType[] {DbHelper.ParamType.STRING,
								DbHelper.ParamType.STRING,
								DbHelper.ParamType.STRING,
								DbHelper.ParamType.STRING,
								DbHelper.ParamType.STRING,
								DbHelper.ParamType.STRING,
								DbHelper.ParamType.STRING,
//								DbHelper.ParamType.STRING,
//								DbHelper.ParamType.STRING,
								DbHelper.ParamType.STRING,
								DbHelper.ParamType.STRING}, 
						new String[] {newname, 
									  gender, 
									  age,
//									  blood_type, 
									  passwd, 
									  house_no,
									  street,
									  state,
//									  pin_code,
									  phone_no,
								      userid});
				
//				query2 = "update patients set (blood_pressure,"
//						+ "blood_sugar, "
//						+ "cardiac_ailment, "
//						+ "asthma, "
//						+ "allergies, "
//						+ "chronic_diseases, "
//						+ "major_surgeries, "
//						+ "long_term_med, "
//						+ "trans_hist) = (?,?,?,?,?,?,?,?,?) "
//						+ "where patient_id = ?";
//				
//				
//				String res2 = DbHelper.executeUpdateJson(query2, 
//						new DbHelper.ParamType[] {DbHelper.ParamType.STRING,
//								DbHelper.ParamType.STRING,
//								DbHelper.ParamType.STRING,
//								DbHelper.ParamType.STRING,
//								DbHelper.ParamType.STRING,
//								DbHelper.ParamType.STRING,
//								DbHelper.ParamType.STRING,
//								DbHelper.ParamType.STRING,
//								DbHelper.ParamType.STRING,
//								DbHelper.ParamType.STRING}, 
//						new String[] {blood_pressure,
//								      blood_sugar,
//								      cardiac_ailment,
//								      asthma,allergies,
//								      chronic_diseases,
//								      major_surgeries,
//								      long_term_med,
//								      transf_hist,
//								      userid});
			}
			else 
			{
				
			}
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
