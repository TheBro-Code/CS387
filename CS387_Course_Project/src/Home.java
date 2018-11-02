
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Home
 */
@WebServlet("/Home")
public class Home extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Home() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		if(session.getAttribute("id") == null) { //not logged in
			response.sendRedirect("LoginServlet");
		}
		
		String html = "<html><head><title>Home</title>" + 
				"<script src=\"jquery-3.3.1.js\"> </script>" + 
				"<script src=\"jquery.dataTables.min.js\"></script>" + 
				"<script src=\"jquery-ui.min.js\"></script>" + 
				"<link rel=\"stylesheet\" href=\"jquery-ui.css\" />" + 
				"<link rel=\"stylesheet\" href=\"jquery.dataTables.min.css\"/>" + 
				"<script src=\"whatasap_home.js\"></script>" +
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
				
				"<input type=\"text\" id = \"autocomp1\"> " +
		        "<button id=\"autocomp_submit1\"> Submit </button> <br> <br>" +
				
//				"<a href=\"\" id=\"createconv\"> Create Conversation </a>" +
				"<button id=\"createconv\"> Create Conversation</button>" +
				"<div id=\"dummy\"></div>" +
				"<br> <br>" +
				"<div id=\"content\"> </div>" +
				"<br><br>" +
				"<form action=\"LogoutServlet\"> <input type=\"submit\" value=\"Logout\"> </form>" +
					
//				"<button onclick=\"loadTableAsync()\">Load table data</button><br><br>" +
//				"<table id=\"usersTable\" class=\"display\">" + 
//				"<thead>"  + 
//				"<tr> <th>User ID</th> <th>Name</th> <th>Phone</th> </tr>" + 
//				"</thead>" + 
//				"</table>" +
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
