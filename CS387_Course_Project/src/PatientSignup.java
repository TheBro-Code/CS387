

import java.io.IOException;
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
	
	private static final String html = "<html><head><title>Patient Form</title>" + 
			"<script src=\"jquery-3.3.1.js\"> </script>" + 
			"<script src=\"jquery.dataTables.min.js\"></script>" + 
			"<script src=\"jquery-ui.min.js\"></script>" + 
			"<link rel=\"stylesheet\" href=\"jquery-ui.css\" />" + 
			"<link rel=\"stylesheet\" href=\"jquery.dataTables.min.css\"/>" + 
			"<script src=\"Patient_Signup.js\"></script>" +
			"</head>" + 
			"<body><script>loadHome()</script></body>" +
			"</html>";
	
	private static final String htmlerror = "<html><head><title>Patient Form</title>" + 
			"<script src=\"jquery-3.3.1.js\"> </script>" + 
			"<script src=\"jquery.dataTables.min.js\"></script>" + 
			"<script src=\"jquery-ui.min.js\"></script>" + 
			"<link rel=\"stylesheet\" href=\"jquery-ui.css\" />" + 
			"<link rel=\"stylesheet\" href=\"jquery.dataTables.min.css\"/>" + 
			"<script src=\"Patient_Signup.js\"></script>" +
			"</head>" +
			"<body><script>loadHomeError()</script></body>" + 
			"</html>";
       
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
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement(query1);
			stmt.setString(1, userid);
			stmt.setString(2, password);
			stmt.setString(3, fullname);
			stmt.setString(4, phoneno);
			
			try {
				int count = stmt.executeUpdate();
				if(count <= 0) {
					response.setContentType("text/html");
					response.getWriter().print(htmlerror);
					return;
				}
				
				PreparedStatement stmt1 = conn.prepareStatement(query2);
				stmt1.setString(1, userid);
				int count1 = stmt1.executeUpdate();
				if(count1 <= 0) {
					
					response.setContentType("text/html");
					response.getWriter().print(htmlerror);
				}
				
				conn.commit();
				session.setAttribute("userid", userid);
				session.setAttribute("role","patient");
				response.sendRedirect("PatientHome");
			}
			catch (Exception e) {
				conn.rollback();
				e.printStackTrace();
				response.setContentType("text/html");
				response.getWriter().print(htmlerror);
			}
			
        }
		catch (Exception e) {
			e.printStackTrace();
			response.setContentType("text/html");
			response.getWriter().print(htmlerror);
        }
		
	}

}


