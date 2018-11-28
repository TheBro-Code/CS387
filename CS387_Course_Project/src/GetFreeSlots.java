import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

/**
 * Servlet implementation class GetFreeSlots
 */
@WebServlet("/GetFreeSlots")
public class GetFreeSlots extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public static final String DATA_LABEL = "data";
	public static final String MSG_LABEL = "message";
	public static final String STATUS_LABEL = "status";    
    /**
     * @see HttpServlet#HttpServlet()
     */
	public static ObjectMapper mapper = new ObjectMapper();
    public GetFreeSlots() {
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
		String role = (String) session.getAttribute("role");
		String doctor_id = (String)request.getParameter("doctor_id");
		String date = (String)request.getParameter("date");
		String dayOfWeek = (String)request.getParameter("day");
		
//		System.out.println("Date: " + date);
		String query = "";
		ObjectNode res;

		if(dayOfWeek.equals("5") || dayOfWeek.equals("6")) {
			query = "Select weekend_hours as hours, slot_time from doctors where doctor_id = ?;";
			res = DbHelper.executeQueryJson1(query, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING}, 
					new String[] {doctor_id});
//			
//			System.out.println("Result" + res.toString());
//			System.out.println("Print" + res.get("data"));
//			PrintWriter out = response.getWriter();
//			out.print(res);
			
		}
		else {
			query = "Select weekday_hours as hours, slot_time from doctors where doctor_id = ?;";
			res = DbHelper.executeQueryJson1(query, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING}, 
					new String[] {doctor_id});
		}
			
		String workingHours = res.get("data").get(0).get("hours").toString();
		String slotTime = res.get("data").get(0).get("slot_time").toString();
		int slotTimeHrs = Integer.parseInt(slotTime.substring(23, 24));
		int slotTimeMin = Integer.parseInt(slotTime.substring(31, 33));
		int newSlotTime = slotTimeHrs*60 + slotTimeMin;
		ArrayNode arr = mapper.createArrayNode();
		
//			System.out.println(workingHours);
//			System.out.println(newSlotTime);
		
		workingHours = workingHours.substring(1, workingHours.length()-1);
	    String[] tokens = workingHours.split(",");
	    ObjectNode node = mapper.createObjectNode();
	    
	    for (String token : tokens) {
//		        System.out.println("[" + token + "]");
	    	String startTime = token.substring(0, 5);
	    	String endTime = token.substring(6);
	    	Calendar start = Calendar.getInstance();
	    	Calendar end = Calendar.getInstance();
	    	SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.ENGLISH);
	    	try {
				start.setTime(sdf.parse(startTime));
				end.setTime(sdf.parse(endTime));
//					System.out.println(start.get(Calendar.HOUR_OF_DAY));
//					System.out.println(end.get(Calendar.HOUR_OF_DAY));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}// all done
	    	
	    	Calendar currTime = Calendar.getInstance();
	    	currTime = start;
	    	
	    	
	    	while(true) {
	    		if(currTime.getTimeInMillis() >= end.getTimeInMillis()) {
//		    			System.out.println("1");
	    			break;
	    		}
	    		Timestamp currTimeTimestamp = new Timestamp(currTime.getTime().getTime());
//	    		System.out.println("Here I am");
//		    		System.out.println(currTimeTimestamp.toString());
	    		
	    		String query1 = "select * "
			    			  + "from treatment t, appointment a "
			    			  + "where t.doctor_id = ? and a.treatment_id = t.treatment_id "
			    			  + "and a.start_time::time = ?::time "
			    			  + "and a.start_time::date = TO_DATE(?,\'YYYY-MM-DD\')";
	    		
	    	    currTime.add(currTime.MINUTE, newSlotTime);
	    		
	    		try (Connection conn = DriverManager.getConnection(Config.url, Config.user, Config.password))
	            {
	                conn.setAutoCommit(false);
	                 
	                PreparedStatement pstmt1=conn.prepareStatement(query1);
	                pstmt1.setString(1, doctor_id);
	                pstmt1.setTimestamp(2, currTimeTimestamp);
	                pstmt1.setString(3, date);
	                
//	                System.out.println(doctor_id + " " 
//     					   + currTimeTimestamp.toString() + " "
//     					   + date);
	        
	                try(ResultSet rs = pstmt1.executeQuery()) 
	                {
	                	if(!rs.next()) 
	                	{
	                		ObjectNode obj = mapper.createObjectNode();
	                		
	            			obj.put("freeslot", currTimeTimestamp.toString());
	                		
	                		arr.add(obj);
	                	}
	                	
//	                	System.out.println("Yo");
	                    
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
	    		
	    		
	    	}    
	    }
	    
    	node.putArray(DATA_LABEL).addAll(arr);    	
    	node.put(STATUS_LABEL, true);
	    
	    response.getWriter().print(node.toString());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}
}
