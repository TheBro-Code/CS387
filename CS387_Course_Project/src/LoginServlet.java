
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String LOGIN_FORM = "<html>\r\n" + 
			"<head>\r\n" + 
			"	<title>Login Form</title>\r\n" + 
			"	<link rel=\"stylesheet\" type=\"text/css\" href=\"LoginForm.css\">\r\n" + 
			"	<link rel=\"stylesheet\" href=\"https://www.w3schools.com/w3css/4/w3.css\">\r\n" + 
			"	<script src=\"jquery-3.3.1.js\"> </script>\r\n" + 
			"	<script src=\"jquery.dataTables.min.js\"></script>\r\n" + 
			"	<script src=\"jquery-ui.min.js\"></script>\r\n" + 
			"	<script src=\"LoginForm.js\"></script>\r\n" + 
			"</head>\r\n" + 
			"<body bgcolor = \"DodgerBlue\">\r\n" + 
			"	<div class=\"login-page\">\r\n" + 
			"	  <div class=\"form\">\r\n" + 
			"	    <form class=\"login-form\" method = \"post\">\r\n" + 
			"	      <label><b>User ID</b></label>\r\n" + 
			"	      <input type=\"text\" name=\"userid\" placeholder=\"userid\"/>\r\n" + 
			"	      <label><b>Password</b></label>\r\n" + 
			"	      <input type=\"password\" name = \"password\" placeholder=\"password\"/>\r\n" + 
			"	      <label><b>Patient</b></label>\r\n" + 
			"	      <input type=\"radio\" name=\"role\" value=\"patient\" checked><br>\r\n" + 
			"	      <label><b>Doctor</b></label>\r\n" + 
			"  		  <input type=\"radio\" name=\"role\" value=\"doctor\"> \r\n" + 
			"	      <button>login</button>\r\n" + 
			"	      <p class=\"message\">Not registered? Create an account<br>\r\n" + 
			"	      <a href=\"PatientSignup\">Patient</a><br>\r\n" + 
			"	      <a href=\"DoctorSignup\">Doctor</a></p>\r\n" + 
			"	    </form>\r\n" + 
			"	  </div>\r\n" + 
			"	</div>\r\n" + 
			"</body>\r\n" + 
			"</html>\r\n" + 
			"\r\n";
	
	
	private static final String LOGIN_FORM_ERROR = "<html>\r\n" + 
			"<head>\r\n" + 
			"	<title>Login Form</title>\r\n" + 
			"	<link rel=\"stylesheet\" type=\"text/css\" href=\"LoginForm.css\">\r\n" + 
			"	<link rel=\"stylesheet\" href=\"https://www.w3schools.com/w3css/4/w3.css\">\r\n" + 
			"	<script src=\"jquery-3.3.1.js\"> </script>\r\n" + 
			"	<script src=\"jquery.dataTables.min.js\"></script>\r\n" + 
			"	<script src=\"jquery-ui.min.js\"></script>\r\n" + 
			"	<script src=\"LoginForm.js\"></script>\r\n" + 
			"</head>\r\n" + 
			"<body bgcolor = \"DodgerBlue\">\r\n" + 
			"	<div class=\"login-page\">\r\n" + 
			"	  <div class=\"form\">\r\n" + 
			"	  	<div id=\"errortext\">Incorrect UserId/Password</div>\r\n" + 
			"	    <form class=\"login-form\" method = \"post\">\r\n" + 
			"	      <label><b>User ID</b></label>\r\n" + 
			"	      <input type=\"text\" name=\"userid\" placeholder=\"userid\"/>\r\n" + 
			"	      <label><b>Password</b></label>\r\n" + 
			"	      <input type=\"password\" name = \"password\" placeholder=\"password\"/>\r\n" + 
			"	      <label><b>Patient</b></label>\r\n" + 
			"	      <input type=\"radio\" name=\"role\" value=\"patient\" checked><br>\r\n" + 
			"	      <label><b>Doctor</b></label>\r\n" + 
			"  		  <input type=\"radio\" name=\"role\" value=\"doctor\"> \r\n" + 
			"	      <button>login</button>\r\n" + 
			"	      <p class=\"message\">Not registered? Create an account<br>\r\n" + 
			"	      <a href=\"PatientSignup\">Patient</a><br>\r\n" + 
			"	      <a href=\"DoctorSignup\">Doctor</a></p>\r\n" + 
			"	    </form>\r\n" + 
			"	  </div>\r\n" + 
			"	</div>\r\n" + 
			"</body>\r\n" + 
			"</html>\r\n" + 
			"\r\n";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public LoginServlet() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		out.println(LOGIN_FORM);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		String role = request.getParameter("role");
//		System.out.println("debug");
//		System.out.println(userid + " " + password + " " + role);

		if(role.equals("patient")) {
			String query = "select passwd from users, patients where patients.patient_id = ?";
			List<List<Object>> res = DbHelper.executeQueryList(query,
					new DbHelper.ParamType[] { DbHelper.ParamType.STRING }, new Object[] { userid });

			String dbPass = res.isEmpty() ? null : (String) res.get(0).get(0);
			if (dbPass != null && dbPass.equals(password)) {
				HttpSession session = request.getSession();
				session.setAttribute("userid", userid);
				session.setAttribute("role", "patient");
				response.sendRedirect("PatientHome");
			}
			else {
				response.setContentType("text/html");
				response.getWriter().print(LOGIN_FORM_ERROR);
			}
		}
		else {
			String query = "select passwd from users, doctors where doctors.doctor_id = ?";
			List<List<Object>> res = DbHelper.executeQueryList(query,
					new DbHelper.ParamType[] { DbHelper.ParamType.STRING }, new Object[] { userid });

			String dbPass = res.isEmpty() ? null : (String) res.get(0).get(0);
			if (dbPass != null && dbPass.equals(password)) {
				HttpSession session = request.getSession();
				session.setAttribute("userid", userid);
				session.setAttribute("role", "doctor");
				response.sendRedirect("DoctorHome");
			}
			else {
				response.setContentType("text/html");
				response.getWriter().print(LOGIN_FORM_ERROR);
			}
		}
	}
}
