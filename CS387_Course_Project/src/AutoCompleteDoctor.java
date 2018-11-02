
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
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Servlet implementation class AutoCompleteDoctor
 */
@WebServlet("/AutoCompleteDoctor")
public class AutoCompleteDoctor extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	public static ObjectMapper mapper = new ObjectMapper();
	
    public AutoCompleteDoctor() {
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

        ArrayNode arr = mapper.createArrayNode();
		String str = request.getParameter("term");
		String input = request.getParameter("input");
		
		try (Connection conn = DriverManager.getConnection(Config.url, Config.user, Config.password))
        {
            conn.setAutoCommit(false);
            PreparedStatement pstmt1=conn.prepareStatement("");	
            
            if(input.equals("name")) {
            	pstmt1 = conn.prepareStatement("select name from doctors,users where users.userid = doctors.doctor_id and name like ?;");
            	pstmt1.setString(1,"%" + str + "%");
            }
            else if(input.equals("hospital")) {
            	pstmt1 = conn.prepareStatement("select hospital from doctors where hospital like ?;");
            	pstmt1.setString(1,"%" + str + "%");
            }
            else if(input.equals("locality")){
            	pstmt1 = conn.prepareStatement("select hospital_address from doctors where hospital_address like ?;");
            	pstmt1.setString(1,"%" + str + "%");
            }
            else if(input.equals("qualifications")) {
            	pstmt1 = conn.prepareStatement("select qualifications from doctors where qualifications like ?;");
            	pstmt1.setString(1,"%" + str + "%");
            }
       
            
            try(ResultSet rs = pstmt1.executeQuery()) 
            {
                
            	while(rs.next()) 
            	{
            		ObjectNode obj = mapper.createObjectNode();
            		if(input.equals("name")) {
            			String name = rs.getString("name");
            			obj.put("name", name);
            			obj.put("label", name);
            			obj.put("value", name);
            		}
            		else if(input.equals("hospital")) {
            			String hospital = rs.getString("hospital");
            			obj.put("hospital", hospital);
            			obj.put("label", hospital);
            			obj.put("value", hospital);
            		}
            		else if(input.equals("locality")) {
                		String address = rs.getString("hospital_address");
                		obj.put("address", address);
                		obj.put("label", address);
            			obj.put("value", address);
            		}
            		else if(input.equals("qualifications")){
                		String qualifications = rs.getString("qualifications");
                		obj.put("qualifications", qualifications);
                		obj.put("label", qualifications);
            			obj.put("value", qualifications);
            		}
            		            		
            		
            		arr.add(obj);
            	}
                
                conn.commit();
            }
            catch(Exception ex)
            {
                conn.rollback();
                throw ex;
            }
            finally{
                conn.setAutoCommit(true);
            }
        } catch (Exception e) {
        	DbHelper.errorJson(e.getMessage());
        }
		response.getWriter().print(arr.toString());

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
