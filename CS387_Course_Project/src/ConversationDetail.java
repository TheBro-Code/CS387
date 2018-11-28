
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ConversationDetail
 */
@WebServlet("/ConversationDetail")
public class ConversationDetail extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConversationDetail() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		if(session.getAttribute("userid") == null) { //not logged in
			response.sendRedirect("LoginServlet");
		}
		String userid = (String) session.getAttribute("userid");
		String role = (String) session.getAttribute("role");
		String other_id = request.getParameter("other_id");
//		System.out.println(userid);
//		System.out.println(other_id);
		
		String messagesQuery = "";
		String json = "";
		if (role.equals("patient")) {
			messagesQuery = "select p.* "
					+ "from posts p, conversations c "
					+ "where p.thread_id = c.thread_id "
					+ "and (c.patient_id = ? and c.doctor_id = ?) "
					+ "order by p.timestamp desc";
			json = DbHelper.executeQueryJson(messagesQuery, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING,
							DbHelper.ParamType.STRING}, 
					new String[] {userid, other_id});
		}
		else {
			messagesQuery = "select p.* "
					+ "from posts p, conversations c "
					+ "where p.thread_id = c.thread_id "
					+ "and (c.patient_id = ? and c.doctor_id = ?) "
					+ "order by p.timestamp desc";
			json = DbHelper.executeQueryJson(messagesQuery, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING,
							DbHelper.ParamType.STRING}, 
					new String[] {other_id, userid});
		}
//		System.out.println(json);
		response.getWriter().print(json);
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
	
	public static void main(String[] args) throws ServletException, IOException {
		new ConversationDetail().doGet(null, null);
	}

}
