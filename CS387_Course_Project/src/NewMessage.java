
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.safety.Whitelist;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class NewMessage
 */
@WebServlet("/NewMessage")
public class NewMessage extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public NewMessage() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession(false);
		if(session.getAttribute("id") == null) { //not logged in
			response.sendRedirect("LoginServlet");
		}
		
		String userid = (String)session.getAttribute("userid");
		String role = (String) session.getAttribute("role");
		String other_id = request.getParameter("other_id");
		String newMsg = request.getParameter("msg");
//		System.out.println("NewMessage : " + userid);
//		System.out.println(other_id);
		
		String sanitizedMsg = Jsoup.clean(newMsg, Whitelist.basic());; //Edit this line to sanitize newMsg before assigning to sanitizedMsg
		
		//Get thread_id
		String threadQuery = "";
		List<List<Object>> res;
		if(role.equals("patient")) {
			threadQuery = "select thread_id "
					+ "from conversations "
					+ "where patient_id = ? and doctor_id = ?";
			res = DbHelper.executeQueryList(threadQuery, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING, DbHelper.ParamType.STRING}, 
					new String[] {userid, other_id});
		}
		else {
			threadQuery = "select thread_id "
					+ "from conversations "
					+ "where patient_id = ? and doctor_id = ?";
			res = DbHelper.executeQueryList(threadQuery, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING, DbHelper.ParamType.STRING}, 
					new String[] {other_id, userid});
		}
		
		if(res.isEmpty()) {
			response.getWriter().print(DbHelper.errorJson("Conversation does not exist"));
			return;
		}
		
		int thread_id = (Integer)res.get(0).get(0);
		String newMsgQuery = "insert into posts (thread_id, userid, timestamp, text) "
				+ "values (?, ?, now(), ?)";
		String json = DbHelper.executeUpdateJson(newMsgQuery, 
				new DbHelper.ParamType[] {DbHelper.ParamType.INT, 
						DbHelper.ParamType.STRING,
						DbHelper.ParamType.STRING},
				new Object[] {thread_id, userid, sanitizedMsg});
		System.out.println(json);
		response.getWriter().print(json);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
