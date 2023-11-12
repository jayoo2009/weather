package air;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class DBInsertServlet
 */
@WebServlet("/airInsert.do")
public class DBInsertServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DBInsertServlet() {
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
		String viewName = null;
		
		String address = request.getParameter("ADDRESS");
		String basc_dt = request.getParameter("BASC_DT");
		String lat = request.getParameter("LAT");
		String lng = request.getParameter("LNG");
		String temp = request.getParameter("TEMP");
		String weather = request.getParameter("WEATHER");
		String aqius = request.getParameter("AQIUS");
		String asthma = request.getParameter("ASTHMA");
		
		ServletContext application = request.getSession().getServletContext();
		//START - 데이터베이스 연결 준비 (web.xml) 
    	String JDBC_Driver = application.getInitParameter("jdbc_driver_name");
  	    String db_url = application.getInitParameter("db_url");
  	    String db_id = application.getInitParameter("db_id");
  	    String db_pw = application.getInitParameter("db_pw");
  	    //END - 데이터베이스 연결 준비 (web.xml)
		
  	    try {
  	    	DBDO dbdo = new DBDO(address, basc_dt, lat, lng, temp, weather, aqius, asthma); 
  	    	DBDAO dbdao = new DBDAO(JDBC_Driver, db_url, db_id, db_pw);
  	    	
  	    	int result = dbdao.insertAir(dbdo);
  	    	
  	    	if(result != 0) {
  	    		System.out.println("Success");
  	    		viewName = "airquality.jsp"; 
  	    	}
  	    	else {
  	    		System.out.println("Error"); 
  	    	}
  	    }
  	    catch(Exception e) {
  	    	System.out.println(e.getMessage()); 
  	    }
  	    
  	    if(viewName != null) {
  	    	response.sendRedirect(viewName); 
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
