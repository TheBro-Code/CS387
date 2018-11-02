

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class PatientSignup
 */
@WebServlet("/PatientSignup")
public class PatientSignup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String SignupForm = "<!DOCTYPE html>\n" + 
			"<html>\n" + 
			"<head>\n" + 
			"\n" + 
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">	\n" + 
			"<link href=\"https://fonts.googleapis.com/css?family=Raleway\" rel=\"stylesheet\">\n" + 
			"<style>\n" + 
			"\n" + 
			"* {\n" + 
			"  box-sizing: border-box;\n" + 
			"}\n" + 
			"\n" + 
			"body {\n" + 
			"  background-color: #f1f1f1;\n" + 
			"}\n" + 
			"\n" + 
			"#regForm {\n" + 
			"  background-color: #ffffff;\n" + 
			"  margin: 100px auto;\n" + 
			"  font-family: Raleway;\n" + 
			"  padding: 40px;\n" + 
			"  width: 70%;\n" + 
			"  min-width: 300px;\n" + 
			"}\n" + 
			"\n" + 
			"h1 {\n" + 
			"  text-align: center;  \n" + 
			"}\n" + 
			"\n" + 
			"input {\n" + 
			"  padding: 10px;\n" + 
			"  width: 100%;\n" + 
			"  font-size: 17px;\n" + 
			"  font-family: Raleway;\n" + 
			"  border: 1px solid #aaaaaa;\n" + 
			"  margin: 10px;\n" + 
			"}\n" + 
			"\n" + 
			"/* Mark input boxes that gets an error on validation: */\n" + 
			"input.invalid {\n" + 
			"  background-color: #ffdddd;\n" + 
			"}\n" + 
			"\n" + 
			"button {\n" + 
			"  background-color: #4CAF50;\n" + 
			"  color: #ffffff;\n" + 
			"  border: none;\n" + 
			"  padding: 10px 20px;\n" + 
			"  font-size: 17px;\n" + 
			"  font-family: Raleway;\n" + 
			"  cursor: pointer;\n" + 
			"}\n" + 
			"\n" + 
			"button:hover {\n" + 
			"  opacity: 0.8;\n" + 
			"}\n" + 
			"\n" + 
			"</style>\n" + 
			"<title>Patient Form</title>\n" + 
			"</head>\n" + 
			"<body>\n" + 
			"<br>\n" + 
			"<br>\n" + 
			"\n" + 
			"<form id = \"regForm\" method=\"Post\">\n" + 
			"	<h1>Register</h1>\n" +
			"	<p style=\"color:red\">UserId/Phone No already taken</p>" +
			"	<div>FULL NAME:\n" + 
			"	<input type=\"text\" name=\"fullname\" placeholder=\"Full Name\">\n" + 
			"	</div>\n" + 
			"	<div>USER ID:\n" + 
			"	<input type=\"text\" name=\"userid\" placeholder=\"User ID\">\n" + 
			"	</div>\n" + 
			"	<div>PASSWORD:\n" + 
			"	<input type=\"password\" name=\"password\" placeholder=\"Password\">\n" + 
			"	</div>\n" + 
			"	<div>PHONE NO:\n" + 
			"	<input type=\"text\" name=\"phoneno\" placeholder=\"Phone no.\">\n" + 
			"	</div>\n" + 
			"	<br><br>\n" + 
			"	<input id = \"submit\" type=\"submit\" value=\"SIGN UP\">\n" + 
			"</form>\n" + 
			"\n" + 
			"</body>\n" + 
			"</html>\n" + 
			"\n";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PatientSignup() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String html = "<html><head><title>Patient Form</title>" + 
				"<script src=\"jquery-3.3.1.js\"> </script>" + 
				"<script src=\"jquery.dataTables.min.js\"></script>" + 
				"<script src=\"jquery-ui.min.js\"></script>" + 
				"<link rel=\"stylesheet\" href=\"jquery-ui.css\" />" + 
				"<link rel=\"stylesheet\" href=\"jquery.dataTables.min.css\"/>" + 
				"<script src=\"Patient_Signup.js\"></script>" +
				"</head>" + 
				"</html>";
		response.setContentType("text/html");
		response.getWriter().print(html);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		
		String userid = request.getParameter("userid");
		String password = request.getParameter("password");
		String fullname = request.getParameter("fullname");
		String phoneno = request.getParameter("phoneno");

		String query1 = "Insert into users(userid, passwd, name, phone_no) values (?, ?, ?, ?)";
		String query2 = "Insert into patients(patient_id) values (?)";
		try (Connection conn = DriverManager.getConnection(Config.url, Config.user, Config.password))
        {
			PreparedStatement stmt = conn.prepareStatement(query1);
			stmt.setString(1, userid);
			stmt.setString(2, password);
			stmt.setString(3, fullname);
			stmt.setString(4, phoneno);
			
			int count = stmt.executeUpdate();
			if(count <= 0) {
				response.setContentType("text/html");
				response.getWriter().print(SignupForm);
				return;
			}
			
			PreparedStatement stmt1 = conn.prepareStatement(query2);
			stmt1.setString(1, userid);
			int count1 = stmt1.executeUpdate();
			if(count1 <= 0) {
				response.setContentType("text/html");
				response.getWriter().print(SignupForm);
				return;
			}
			
			session.setAttribute("userid", userid);
			session.setAttribute("role","patient");
			response.sendRedirect("PatientHome");
			
        }
		catch (Exception e) {
//            e.printStackTrace();
			response.setContentType("text/html");
			response.getWriter().print(SignupForm);
        }
		
	}

}


