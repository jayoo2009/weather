package air;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.http.HttpServletRequest;

import air.DBDO;

@SuppressWarnings("unused")
public class DBDAO {
	private PreparedStatement stmt = null;
	public Connection conn = null;
	private ResultSet rs = null;
	
	private final String INSERT_AIR = 
			"insert into AIR_BASE_HIST(ADDRESS, BASC_DT, LAT, LNG, TEMP, WEATHER, AQIUS, ASTHMA) values(?, ?, ?, ?, ?, ?, ?, ?)";
	
	private String jdbc_driver;
	private String db_url;
	private String db_id;
	private String db_pw;
	   
	public DBDAO(String jdbc_driver, String db_url, String db_id, String db_pw)
	{
		this.jdbc_driver = jdbc_driver;
		this.db_url = db_url;
		this.db_id = db_id;
		this.db_pw = db_pw;
	}
	   
	//DB Connection 
	public void connectDB() throws SQLException, ClassNotFoundException
	{
		Class.forName(jdbc_driver);
		conn = DriverManager.getConnection(db_url, db_id, db_pw);
	}
	
	//DB Disconnection
	public void disconnectDB() throws SQLException
	{
		if(conn != null)
		{
			conn.close();
			conn = null;
		}	
	}
	

	public int insertAir(DBDO Airquality) throws ClassNotFoundException, SQLException {
		int result = 0;
		
		connectDB();
		
		try {
			stmt = conn.prepareStatement(INSERT_AIR);
			stmt.setString(1, Airquality.getADDRESS());
			stmt.setString(2, Airquality.getBASC_DT());
			stmt.setString(3, Airquality.getLAT());
			stmt.setString(4, Airquality.getLNG());
			stmt.setString(5, Airquality.getTEMP());
			stmt.setString(6, Airquality.getWEATHER());
			stmt.setString(7, Airquality.getAQIUS());
			stmt.setString(8, Airquality.getASTHMA());
			result = stmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			disconnectDB();
		}
			
		return result;
	}
	
	
	public List<DBDO> getAirquality() throws ClassNotFoundException, SQLException  {
		DBDO result = null;
		List<DBDO> airlist = new ArrayList<DBDO>(); 
		
		connectDB();
		
		try {
			stmt = conn.prepareStatement("select * from AIR_BASE_HIST");
			rs = stmt.executeQuery();
			if(rs.isBeforeFirst()) {
				while(rs.next()) {
					result = new DBDO();
					result.setADDRESS(rs.getString("ADDRESS"));
					result.setBASC_DT(rs.getString("BASC_DT"));
					result.setLAT(rs.getString("LAT"));
					result.setLNG(rs.getString("LNG"));
					result.setTEMP(rs.getString("TEMP"));
					result.setWEATHER(rs.getString("WEATHER"));
					result.setAQIUS(rs.getString("AQIUS"));
					result.setASTHMA(rs.getString("ASTHMA"));
					airlist.add(result); 
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			disconnectDB();
		}

		return airlist;
	}

}
