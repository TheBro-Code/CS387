

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class AddPrescription
 */
@WebServlet("/AddPrescription")
public class AddPrescription extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddPrescription() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession(false);
		if(session.getAttribute("userid") == null) { //not logged in
			response.sendRedirect("LoginServlet");
		}
		
		
		String appointment_id = request.getParameter("appointment_id");
		String med_name = request.getParameter("med_id");
		String quantity = request.getParameter("quantity");
		
		int a_id = Integer.parseInt(appointment_id);
		int m_id = Integer.parseInt(med_name);
		
		System.out.println(appointment_id +  " " + med_name + " " + quantity);
		
		String query = "with med_id(m_id) as ("
				+ "select medicine_id from medicine where medicine.name = ?) "
				+ "insert into prescription (appointment_id,medicine_id,quantity) values (?,(select m_id from med_id),?);";
		String json = DbHelper.executeUpdateJson(query, 
				new DbHelper.ParamType[] {DbHelper.ParamType.INT,DbHelper.ParamType.INT,  DbHelper.ParamType.STRING},
				new Object[] {m_id,a_id,quantity});
	}

}
