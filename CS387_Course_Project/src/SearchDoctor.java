

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SearchDoctor
 */
@WebServlet("/SearchDoctor")
public class SearchDoctor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchDoctor() {
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
		
		String name = request.getParameter("name");
		String hospital = request.getParameter("hospital");
		String address = request.getParameter("locality");
		String qualifications = request.getParameter("qualifications");
		
		String DoctorSearchQuery;
		String json;
		if(name.isEmpty() && hospital.isEmpty() && address.isEmpty() && qualifications.isEmpty()) {
			DoctorSearchQuery = 
					"select doctor_id,name,hospital,hospital_address,qualifications "
				+	"from doctors,users "
				+	"where doctors.doctor_id = users.userid";
			
			json = DbHelper.executeQueryJson(DoctorSearchQuery, 
					new DbHelper.ParamType[] {}, 
					new String[] {});
			
			System.out.println(json);
			response.getWriter().print(json);
		}
		else if(hospital.isEmpty() && address.isEmpty() && qualifications.isEmpty())
		{
			DoctorSearchQuery = 
					"select doctor_id,name,hospital,hospital_address,qualifications "
				+	"from doctors,users "
				+	"where doctors.doctor_id = users.userid and name = ?";
			
			json = DbHelper.executeQueryJson(DoctorSearchQuery, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING}, 
					new String[] {name});
			
			System.out.println(json);
			response.getWriter().print(json);
		}
		else if(name.isEmpty() && address.isEmpty() && qualifications.isEmpty())
		{
			DoctorSearchQuery = 
					"select doctor_id,name,hospital,hospital_address,qualifications "
				+	"from doctors,users "
				+	"where doctors.doctor_id = users.userid and hospital = ?;";
			
			json = DbHelper.executeQueryJson(DoctorSearchQuery,
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING}, 
					new String[] {hospital});
			response.getWriter().print(json);
		}
		else if(name.isEmpty() && hospital.isEmpty() && qualifications.isEmpty())
		{
			DoctorSearchQuery = 
					"select doctor_id,name,hospital,hospital_address,qualifications "
				+	"from doctors,users "
				+	"where doctors.doctor_id = users.userid and hospital_address = ?;";
			
			json = DbHelper.executeQueryJson(DoctorSearchQuery, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING}, 
					new String[] {address});
			response.getWriter().print(json);
		}
		else if(name.isEmpty() && hospital.isEmpty() && address.isEmpty())
		{
			DoctorSearchQuery = 
					"select doctor_id,name,hospital,hospital_address,qualifications "
				+	"from doctors,users "
				+	"where doctors.doctor_id = users.userid and hospital_address = ?;";
			
			json = DbHelper.executeQueryJson(DoctorSearchQuery, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING}, 
					new String[] {qualifications});
			response.getWriter().print(json);
		}
		else if(name.isEmpty() && hospital.isEmpty())
		{
			DoctorSearchQuery = 
					"select doctor_id,name,hospital,hospital_address,qualifications "
				+	"from doctors,users "
				+	"where doctors.doctor_id = users.userid and hospital_address = ? and qualifications = ?;";
			
			json = DbHelper.executeQueryJson(DoctorSearchQuery, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING,
							DbHelper.ParamType.STRING}, 
					new String[] {address, qualifications});
			response.getWriter().print(json);
		}
		else if(name.isEmpty()  && address.isEmpty() )
		{
			DoctorSearchQuery = 
					"select doctor_id,name,hospital,hospital_address,qualifications "
				+	"from doctors,users "
				+	"where doctors.doctor_id = users.userid and hospital = ? and qualifications = ?;";
			
			json = DbHelper.executeQueryJson(DoctorSearchQuery, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING,
							DbHelper.ParamType.STRING}, 
					new String[] {hospital,qualifications});
			response.getWriter().print(json);
		}
		else if(name.isEmpty() && qualifications.isEmpty())
		{
			DoctorSearchQuery = 
					"select doctor_id,name,hospital,hospital_address,qualifications "
				+	"from doctors,users "
				+	"where doctors.doctor_id = users.userid and hospital_address = ? and hospital = ?;";
			
			json = DbHelper.executeQueryJson(DoctorSearchQuery, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING,
							DbHelper.ParamType.STRING}, 
					new String[] {hospital, address});
			response.getWriter().print(json);
		}
		else if( hospital.isEmpty() && address.isEmpty() )
		{
			DoctorSearchQuery = 
					"select doctor_id,name,hospital,hospital_address,qualifications "
				+	"from doctors,users "
				+	"where doctors.doctor_id = users.userid and name = ? and qualifications = ?;";
			
			json = DbHelper.executeQueryJson(DoctorSearchQuery, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING,
							DbHelper.ParamType.STRING}, 
					new String[] {name, qualifications});
			response.getWriter().print(json);
		}
		else if(hospital.isEmpty()  && qualifications.isEmpty())
		{
			DoctorSearchQuery = 
					"select doctor_id,name,hospital,hospital_address,qualifications "
				+	"from doctors,users "
				+	"where doctors.doctor_id = users.userid and name = ? and hospital_address = ?;";
			
			json = DbHelper.executeQueryJson(DoctorSearchQuery, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING,
							DbHelper.ParamType.STRING}, 
					new String[] {name, address});
			response.getWriter().print(json);
		}
		else if(address.isEmpty() && qualifications.isEmpty())
		{
			DoctorSearchQuery = 
					"select doctor_id,name,hospital,hospital_address,qualifications "
				+	"from doctors,users "
				+	"where doctors.doctor_id = users.userid and name = ? and hospital = ?;";
			
			json = DbHelper.executeQueryJson(DoctorSearchQuery, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING,
							DbHelper.ParamType.STRING}, 
					new String[] {name, hospital});
			response.getWriter().print(json);
		}
		else if(name.isEmpty())
		{
			DoctorSearchQuery = 
					"select doctor_id,name,hospital,hospital_address,qualifications "
				+	"from doctors,users "
				+	"where doctors.doctor_id = users.userid and hospital_address = ? and hospital = ? and qualifications = ?;";
			
			json = DbHelper.executeQueryJson(DoctorSearchQuery, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING,
							DbHelper.ParamType.STRING,
							DbHelper.ParamType.STRING}, 
					new String[] {hospital, address, qualifications});
			response.getWriter().print(json);
		}
		else if(hospital.isEmpty())
		{
			DoctorSearchQuery = 
					"select doctor_id,name,hospital,hospital_address,qualifications "
				+	"from doctors,users "
				+	"where doctors.doctor_id = users.userid and hospital_address = ? and name = ? and qualifications = ?;";
			
			json = DbHelper.executeQueryJson(DoctorSearchQuery, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING,
							DbHelper.ParamType.STRING,
							DbHelper.ParamType.STRING}, 
					new String[] {name, address, qualifications});
			response.getWriter().print(json);
		}
		else if(address.isEmpty())
		{
			DoctorSearchQuery = 
					"select doctor_id,name,hospital,hospital_address,qualifications "
				+	"from doctors,users "
				+	"where doctors.doctor_id = users.userid and name = ? and hospital = ? and qualifications = ?;";
			
			json = DbHelper.executeQueryJson(DoctorSearchQuery, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING,
							DbHelper.ParamType.STRING,
							DbHelper.ParamType.STRING}, 
					new String[] {name, hospital, qualifications});
			response.getWriter().print(json);
		}
		else if(qualifications.isEmpty())
		{
			DoctorSearchQuery = 
					"select doctor_id,name,hospital,hospital_address,qualifications "
				+	"from doctors,users "
				+	"where doctors.doctor_id = users.userid and hospital_address = ? and hospital = ? and name = ?;";
			
			json = DbHelper.executeQueryJson(DoctorSearchQuery, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING,
							DbHelper.ParamType.STRING,
							DbHelper.ParamType.STRING}, 
					new String[] {name, hospital, address});
			response.getWriter().print(json);
		}
		else
		{
			
			DoctorSearchQuery = 
					"select doctor_id,name,hospital,hospital_address,qualifications "
				+	"from doctors,users "
				+	"where doctors.doctor_id = users.userid and hospital_address = ? and hospital = ? and qualifications = ? and name = ?;";
		
			json = DbHelper.executeQueryJson(DoctorSearchQuery, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING,
							DbHelper.ParamType.STRING,
							DbHelper.ParamType.STRING,
							DbHelper.ParamType.STRING}, 
					new String[] {name, hospital, address, qualifications});
			response.getWriter().print(json);
		}				
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
