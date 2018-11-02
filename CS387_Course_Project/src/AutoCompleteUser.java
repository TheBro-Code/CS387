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
 * Servlet implementation class AutoCompleteUser
 */
@WebServlet("/AutoCompleteUser")
public class AutoCompleteUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	    /**
	     * @see HttpServlet#HttpServlet()
	     */
	public static ObjectMapper mapper = new ObjectMapper();
	
    public AutoCompleteUser() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		
		HttpSession session = request.getSession();
		if(session.getAttribute("id") == null) { //not logged in
			response.sendRedirect("LoginServlet");
		}

        ArrayNode arr = mapper.createArrayNode();
		String str = request.getParameter("term");
		try (Connection conn = DriverManager.getConnection(Config.url, Config.user, Config.password))
        {
            conn.setAutoCommit(false);
            
            PreparedStatement pstmt1 = conn.prepareStatement("select uid, name, phone from users where uid like ? or name like ? or phone like ?;");
            pstmt1.setString(1, str+"%");
            pstmt1.setString(2, str+"%");
            pstmt1.setString(3, str+"%");
       
            
            try(ResultSet rs = pstmt1.executeQuery()) 
            {
                
            	while(rs.next()) 
            	{
            		ObjectNode obj = mapper.createObjectNode();
            		
            		String uid1 = rs.getString("uid");
            		String name1 = rs.getString("name");
            		String phone1 = rs.getString("phone");

            		obj.put("uid", uid1);
            		obj.put("name", name1);
            		obj.put("phone", phone1);
            		obj.put("label", uid1 + "  " + name1 + "  " + phone1);
            		obj.put("value", uid1);
            		
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