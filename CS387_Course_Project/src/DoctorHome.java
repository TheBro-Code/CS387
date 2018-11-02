import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class DoctorHome
 */
@WebServlet("/DoctorHome")
public class DoctorHome extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DoctorHome() {
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
		
		String html = "<html><head><title>Home</title>" + 
				"<script src=\"jquery-3.3.1.js\"> </script>" + 
				"<script src=\"jquery.dataTables.min.js\"></script>" + 
				"<script src=\"jquery-ui.min.js\"></script>" + 
				"<link rel=\"stylesheet\" href=\"jquery-ui.css\" />" + 
				"<link rel=\"stylesheet\" href=\"jquery.dataTables.min.css\"/>" + 
				"<script src=\"DoctorHome.js\"></script>" +
				"</head>" + 
				"<style>\n" +
	            ".mytable {\n" +
	            "border: 1px solid black;\n" +
	            "text-align: center;\n" +
	            "}\n" +
	            " {" +
	            "</style>\n" +
				"<body>" +
				
				"<button onclick=\"loadHome()\"> Home </button><br><br>" +
				"<button id=\"doctor_profile\" onclick=\"loadProfile()\"> View/Update Profile</button>" +
				"<br> <br>" +
				"<div id=\"content\"> </div>" +
				"<br><br>" +
				"<form action=\"LogoutServlet\"> <input type=\"submit\" value=\"Logout\"> </form>" +
				"</body>" +
				"</html>";
		response.setContentType("text/html");
		response.getWriter().print(html);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
