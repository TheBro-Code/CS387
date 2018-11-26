

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
 * Servlet implementation class DoctorSignup
 */
@WebServlet("/DoctorSignup")
public class DoctorSignup extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private static final String html = "<html><head><title>Doctor Form</title>" + 
			"<script src=\"jquery-3.3.1.js\"> </script>" + 
			"<script src=\"jquery.dataTables.min.js\"></script>" + 
			"<script src=\"jquery-ui.min.js\"></script>" + 
			"<link rel=\"stylesheet\" href=\"jquery-ui.css\" />" + 
			"<link rel=\"stylesheet\" href=\"jquery.dataTables.min.css\"/>" + 
			"<script src=\"Doctor_Signup.js\"></script>" +
			"</head>" + 
			"<body><script>loadHome()</script></body>" +
			"</html>";
	
	private static final String htmlerror = "<html><head><title>Doctor Form</title>" + 
			"<script src=\"jquery-3.3.1.js\"> </script>" + 
			"<script src=\"jquery.dataTables.min.js\"></script>" + 
			"<script src=\"jquery-ui.min.js\"></script>" + 
			"<link rel=\"stylesheet\" href=\"jquery-ui.css\" />" + 
			"<link rel=\"stylesheet\" href=\"jquery.dataTables.min.css\"/>" + 
			"<script src=\"Doctor_Signup.js\"></script>" +
			"</head>" +
			"<body><script>loadHomeError()</script></body>" + 
			"</html>";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoctorSignup() {
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
		String degree = request.getParameter("degree");
		String speciality = request.getParameter("speciality");
		String college = request.getParameter("college");
		int completion = Integer.parseInt(request.getParameter("completion"));
		int experience = Integer.parseInt(request.getParameter("experience"));
		String regnum = request.getParameter("regnum");
		String regcouncil = request.getParameter("regcouncil");
		int regyear = Integer.parseInt(request.getParameter("regyear"));
		String clinic = request.getParameter("clinic");
		String city = request.getParameter("city");
		String locality = request.getParameter("locality");
		int fees = Integer.parseInt(request.getParameter("fees"));
		String start  = request.getParameter("starting_hours");
		String end  = request.getParameter("ending_hours");
		String slot_length  = request.getParameter("slot");
		String hospital_address = locality + ", " + city;	
		
		String query1 = "Insert into users(userid, passwd, name, phone_no) values (?, ?, ?, ?)";
		String query2 = "Insert into doctors(doctor_id, qualifications, speciality, college, completion, "
				+ "experience, regnum, regcouncil, regyear, start_time, end_time, slot_time, hospital, hospital_address, "
				+ "fees) values (?, ?, ?, ?, ?, ?, ?, ?, ?, to_timestamp(?,\'HH24:MM\')::time, to_timestamp(?,\'HH24:MM\')::time, ?::interval, ?, ?, ?)";
		
		try (Connection conn = DriverManager.getConnection(Config.url, Config.user, Config.password))
        {
			conn.setAutoCommit(false);
			PreparedStatement stmt = conn.prepareStatement(query1);
			stmt.setString(1, userid);
			stmt.setString(2, password);
			stmt.setString(3, fullname);
			stmt.setString(4, phoneno);
			
			
			try
			{
				int count = stmt.executeUpdate();
				if(count <= 0) {
					response.setContentType("text/html");
					response.getWriter().print(htmlerror);
					return;
				}
				
				PreparedStatement stmt1 = conn.prepareStatement(query2);
				stmt1.setString(1, userid);
				stmt1.setString(2, degree);
				stmt1.setString(3, speciality);
				stmt1.setString(4, college);
				stmt1.setInt(5, completion);
				stmt1.setInt(6, experience);
				stmt1.setString(7, regnum);
				stmt1.setString(8, regcouncil);
				stmt1.setInt(9, regyear);
				stmt1.setString(10, start);
				stmt1.setString(11, end);
				stmt1.setString(12, slot_length + " mins");
				stmt1.setString(13, clinic);
				stmt1.setString(14, hospital_address);
				stmt1.setInt(15, fees);
				int count1 = stmt1.executeUpdate();
				if(count1 <= 0) {
					response.setContentType("text/html");
					response.getWriter().print(htmlerror);
					return;
				}
				
				conn.commit();
				session.setAttribute("userid", userid);
				session.setAttribute("role","doctor");
				response.sendRedirect("DoctorHome");
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
