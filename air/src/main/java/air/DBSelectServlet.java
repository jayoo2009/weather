package air;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


/**
 * Servlet implementation class DBSelectServlet
 */
@WebServlet("/AirSelect.do")
public class DBSelectServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DBSelectServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setCharacterEncoding("UTF-8");
		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession(); 
		String viewName = null;
		
		ServletContext application = request.getSession().getServletContext();
		//START - 데이터베이스 연결 준비 (web.xml) 
    	String JDBC_Driver = application.getInitParameter("jdbc_driver_name");
  	    String db_url = application.getInitParameter("db_url");
  	    String db_id = application.getInitParameter("db_id");
  	    String db_pw = application.getInitParameter("db_pw");
  	    //END - 데이터베이스 연결 준비 (web.xml)
  	    
  	    try {
  	    	
  	    	DBDAO dbdao = new DBDAO(JDBC_Driver, db_url, db_id, db_pw);
  	    	
  	    	List<DBDO> airlist = dbdao.getAirquality();
  	    	
  	    	if(airlist != null) {
  	    		session.setAttribute("airlist", airlist);
  	    		viewName = "air.jsp"; 
  	    	}
  	    	else {
  	    		System.out.println("Null");
  	    	}
  	    	
  	    }
  	    catch(Exception e){
  	    	System.out.println(e.getMessage()); 
  	    }
  	    
  	    if(viewName != null) {
  	    	RequestDispatcher view = request.getRequestDispatcher(viewName);
  	    	view.forward(request, response); 
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
