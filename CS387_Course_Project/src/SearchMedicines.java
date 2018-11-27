import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SearchMedicines
 */
@WebServlet("/SearchMedicines")
public class SearchMedicines extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SearchMedicines() {
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
		String retailer = request.getParameter("disease");
		
		String MedicineSearchQuery;
		String json;
		if(name.isEmpty() && retailer.isEmpty()) {
			MedicineSearchQuery = 
					"select medicine_id,name,retailer,price_per_unit,side_effects,disease,chronic_diseases,prescription_required "
				+	"from medicine;";
			
			json = DbHelper.executeQueryJson(MedicineSearchQuery, 
					new DbHelper.ParamType[] {}, 
					new String[] {});
			
//			System.out.println(json);
			response.getWriter().print(json);
		}
		
		else if(name.isEmpty()) {
			MedicineSearchQuery = 
					"select medicine_id,name,retailer,price_per_unit,side_effects,disease,chronic_diseases,prescription_required "
							+	"from medicine where medicine.retailer = ?;";
			
			json = DbHelper.executeQueryJson(MedicineSearchQuery, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING}, 
					new String[] {retailer});
			
//			System.out.println(json);
			response.getWriter().print(json);
		}
		else if(retailer.isEmpty()){
			MedicineSearchQuery = 
					"select medicine_id,name,retailer,price_per_unit,side_effects,disease,chronic_diseases,prescription_required "
							+	"from medicine where medicine.name = ?;";
			
			json = DbHelper.executeQueryJson(MedicineSearchQuery, 
					new DbHelper.ParamType[] {DbHelper.ParamType.STRING}, 
					new String[] {name});
			
//			System.out.println(json);
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
