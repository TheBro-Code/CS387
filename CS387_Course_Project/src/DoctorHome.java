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
		HttpSession session = request.getSession(false);
		if(session.getAttribute("userid") == null) { //not logged in
			response.sendRedirect("LoginServlet");
		}
		
		String html = "<html><head><title>Home</title>" + 
			    "<link rel=\"stylesheet\" href=\"bootstrap.min.css\">"+
			    "	<link rel=\"stylesheet\" type=\"text/css\" href=\"PatientHome.css\">\r\n" +
			    "<link rel=\"stylesheet\" href=\"font-awesome.min.css\">" +
			    "<link rel=\"stylesheet\" href=\"https://use.fontawesome.com/releases/v5.5.0/css/all.css\" integrity=\"sha384-B4dIYHKNBt8Bc12p+WXckhzcICo0wtJAoU8YZTY5qE0Id1GSseTk6S+L3BlXeVIU\" crossorigin=\"anonymous\">"+
				"<link href=\"https://fonts.googleapis.com/icon?family=Material+Icons\" rel=\"stylesheet\">"+
			    "<script src=\"jquery.min.js\"></script>" +
			    "<script src=\"bootstrap.min.js\"></script>"+
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
				"<ul id=\"navbar\">"+
				"<li><button onclick=\"loadHome()\" class=\"btn\"><i class=\"fa fa-home\"></i> Home </button></li>" +
				"<li><button onclick=\"ongoingTreatments()\" class=\"btn\"><i class=\"fa fa-home\"></i> Ongoing Treatments </button></li>" +
				"<li><button onclick=\"loadHome()\" class=\"btn\"><i class=\"fa fa-home\"></i> Appointments </button></li>" +
//				"<li><button id=\"order_medicines\" onclick=\"order_medicines()\" class=\"btn\"><i class=\"fas fa-pills\"></i> Order Medicines </button></li>" +
				"<li><button id=\"treatment_history\" onclick=\"treatment_history()\" class=\"btn\"><i class=\"fas fa-clipboard\"></i> Treatment History </button></li>" +
//				"<li><button id=\"your_orders\" onclick=\"your_orders()\" class=\"btn\"><i class=\"fas fa-history\"></i> Your Orders </button></li>" +
				"<li><button id=\"doctor_profile\" onclick=\"loadProfile()\" class=\"btn\"><i class=\"material-icons\">tag_faces</i> View/Update Profile</button></li>" +
				"<li style=\"float: right;\"><button onclick=\"location.href='LogoutServlet'\" class=\"btn\"><i class=\"material-icons\">exit_to_app</i> Logout </button></li>" +
				//"<li><form action=\"LogoutServlet\"> <input type=\"submit\" value=\"Logout\"> </form></li>" +
				"</ul>"+
				"<br><br>"+
//				"<div id=\"find_doctor\">"+
//				"<center><h1> Find a Doctor </h1></center><br><br>"+
//				"<center>Name : <input type=\"text\" id = \"name\" name = \"name\"> " +
//				"Hospital : <input type=\"text\" id = \"hospital\" name = \"hospital\"> " +
//				"Locality : <input type=\"text\" id = \"locality\" name = \"locality\"> " +
//				"Qualifications : <input type=\"text\" id = \"qualifications\" name = \"qualifications\"> " +
//		        "<button id=\"search_doc\"> Submit </button></center>" +
//		        "</div> <br> <br>" +

				"<div class=\"form-popup\" id=\"myForm\">\n" + 
				"  <form method=\"post\" onSubmit=\"formclick(); return false;\" class=\"form-container\">\n" + 
				" <div id=\"comp\" style=\"color:red background-color:white\"> </div> " +
				"   <input type=\"text\" id=\"comments\" name=\"comments\"> </input>" +
				"   <input type=\"hidden\" style=\"background-color:white\" id=\"appointment_id\" name=\"appointment_id\"> </input>" + 
				"    <input type=\"submit\" value=\"Submit\" class=\"btn\"> Submit </input>\n" + 
				"    <button type=\"button\" class=\"btn cancel\" onclick=\"closeForm()\"> Close </button>\n" + 
				"  </form>\n" +
				"</div>" +
				
				"<div class=\"form-popup\" id=\"myForm1\">\n" + 
				"  <form method=\"post\" onSubmit=\"formclick1(); return false;\" class=\"form-container\">\n" + 
				" <div id=\"comp1\" style=\"color:red background-color:white\"> </div> " +
				"   MEDICINE ID : <input type=\"text\" id=\"med_id\" name=\"med_id\"> </input>" +
				"   QUANTITY : <input type=\"text\" id=\"quantity\" name=\"quantity\"> </input>" +
				"   <input type=\"hidden\" style=\"background-color:white\" id=\"appointment_id\" name=\"appointment_id\"> </input>" + 
				"    <input type=\"submit\" value=\"Submit\" class=\"btn\"> Submit </input>\n" + 
				"    <button type=\"button\" class=\"btn cancel\" onclick=\"closeForm1()\"> Close </button>\n" + 
				"  </form>\n" +
				"</div>" +
		        
				"<div id = \"hide_order\">" +
				"</div>" +
				"<br> <br>" +
				"<div id=\"dummy\"></div>" +
				"<div id=\"content\"> </div>" +
				"<br><br>" +
				
				
//				"<button onclick=\"loadHome()\"> Home </button><br><br>" +
//				"<div id = \"hide_order\">" +	
//				"</div>" +
//				"<button id=\"order_medicines\" onclick=\"order_medicines()\"> Order </button>" +
//				"<button id=\"treatment_history\" onclick=\"treatment_history()\"> Treatment History </button>" +
//				"<button id=\"your_orders\" onclick=\"your_orders()\"> Your Orders </button>" +
//				"<form action=\"LogoutServlet\"> <input type=\"submit\" value=\"Logout\"> </form>" +
////				"Name : <input type=\"text\" id = \"name\" name = \"name\"> " +
////				"Hospital : <input type=\"text\" id = \"hospital\" name = \"hospital\"> " +
////				"Locality : <input type=\"text\" id = \"locality\" name = \"locality\"> " +
////				"Qualifications : <input type=\"text\" id = \"qualifications\" name = \"qualifications\"> " +
////		        "<button id=\"search_doc\"> Submit </button> <br> <br>" +
//				"<button id=\"patient_profile\" onclick=\"loadProfile()\"> View/Update Profile</button>" +
//				"<br> <br>" +
//				"<div id=\"dummy\"></div>" +
//				"<div id=\"content\"> </div>" +
//				"<br><br>" +
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
