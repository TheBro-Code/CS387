

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * Servlet implementation class BookTreatment
 */
@WebServlet("/BookTreatment")
public class BookTreatment extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String DATA_LABEL = "data";
	public static final String MSG_LABEL = "message";
	public static final String STATUS_LABEL = "status"; 
	
	public static ObjectMapper mapper = new ObjectMapper();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookTreatment() {
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
		
		String userid = (String) session.getAttribute("userid");
//		String role = (String) session.getAttribute("role");
		String doctor_id = (String)request.getParameter("doctor_id");
		String date = (String)request.getParameter("date");
		System.out.println(date);
		String startTime = (String)request.getParameter("startTime");
		System.out.println(startTime);
		String reasonForVisit = (String)request.getParameter("reasonForVisit");
		String sTime = date + " " + startTime;
		
		String query = "select * from treatment where patient_id = ? and doctor_id = ? and end_time is null;";
		try (Connection conn = DriverManager.getConnection(Config.url, Config.user, Config.password))
        {
            conn.setAutoCommit(false);
             
            PreparedStatement pstmt1=conn.prepareStatement(query);
            pstmt1.setString(1, userid);
            pstmt1.setString(2, doctor_id);
            try(ResultSet rs = pstmt1.executeQuery()) 
            {
            	int t_id = 0;
            	if(!rs.next()) 
            	{
            		String query1 = "Insert into Treatment(patient_id, doctor_id, start_time) values"
            				+ " (?, ?, to_timestamp(?, 'YYYY-MM-DD hh:mi:ss AM')::timestamp without time zone)";
            		PreparedStatement pstmt2=conn.prepareStatement(query1);
                    pstmt2.setString(1, userid);
                    pstmt2.setString(2, doctor_id);
                    pstmt2.setString(3, sTime);
                    
                    try
                    {
                    	int rs1 = pstmt2.executeUpdate();
                    	if(rs1 == 0) {
                    		System.out.println("Treatment Could not be created");
                    	}
//                    	conn.commit();
                    }
                    catch(Exception ex)
                    {
                        conn.rollback();
                        ex.printStackTrace();
                    }
                    
                    System.out.println(userid);
                    System.out.println(doctor_id);
                    String query5 = "select treatment_id from treatment where patient_id = ? "
                    		+ "and doctor_id = ? and end_time is null";
                    PreparedStatement pstmt5=conn.prepareStatement(query5);
                    pstmt5.setString(1, userid);
                    pstmt5.setString(2, doctor_id);
//                    pstmt5.setString(3, sTime);
                    
                    ResultSet rs5 = pstmt5.executeQuery();
                    if(rs5.next()) {
                    	t_id = rs5.getInt("treatment_id");
                    }
                    else {
                    	System.out.println("We are in Big Trouble");
                    }
                    
//                    ResultSet rs2 = pstmt2.getGeneratedKeys();
//                    t_id = rs2.getInt(1);
//                    System.out.println("new tid : " + t_id);
//                    
            	}
            	else {
            		t_id = rs.getInt("treatment_id");
            		
            	}
            	
            	String query2 = "Insert into appointment(treatment_id, reason_visit, start_time) values "
                		+ "(?,?,to_timestamp(?, 'YYYY-MM-DD hh:mi:ss AM')::timestamp without time zone)";
                PreparedStatement pstmt3=conn.prepareStatement(query2);
                pstmt3.setInt(1, t_id);
                System.out.println(reasonForVisit);
                pstmt3.setString(2, reasonForVisit);
                pstmt3.setString(3, sTime);
                
                int rs3 = pstmt3.executeUpdate();
            	if(rs3 == 0) {
            		System.out.println("Appointment Could not be created");
            	}
            	
            	String query7 = "Select * from conversations where patient_id = ? and doctor_id = ?";
            	PreparedStatement pstmt7=conn.prepareStatement(query7);
            	pstmt7.setString(1, userid);
            	pstmt7.setString(2, doctor_id);
            	ResultSet rs7 = pstmt7.executeQuery();
            	
            	if(!rs7.next()) {
            		String query6 = "Insert into conversations(patient_id, doctor_id) values(?, ?)";
                	PreparedStatement pstmt6=conn.prepareStatement(query6);
                	pstmt6.setString(1, userid);
                	pstmt6.setString(2, doctor_id);
                	int rs6 = pstmt6.executeUpdate();
                	if(rs6 == 0) {
                		System.out.println("Could not create a new Conversation");
                	}
            	}
            	
                conn.commit();
            }
            catch(Exception ex)
            {
                conn.rollback();
                ex.printStackTrace();
            }
            finally{
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
        	DbHelper.errorJson(e.getMessage());
        }
		
		response.getWriter().print("true");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
