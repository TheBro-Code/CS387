
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
 * Servlet implementation class AutoCompleteMed
 */
@WebServlet("/AutoCompleteMed")
public class AutoCompleteMed extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public static ObjectMapper mapper = new ObjectMapper();
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AutoCompleteMed() {
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
            	pstmt1 = conn.prepareStatement("select name from medicine where name like ?;");
            	pstmt1.setString(1,"%" + str + "%");
            }
            else if(input.equals("disease")) {
            	pstmt1 = conn.prepareStatement("select disease from medicine where disease like ?;");
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
            		else if(input.equals("disease")) {
            			String disease = rs.getString("disease");
            			obj.put("disease", disease);
            			obj.put("label", disease);
            			obj.put("value", disease);
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
