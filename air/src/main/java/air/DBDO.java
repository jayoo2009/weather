package air;

public class DBDO {
	private String ADDRESS;
	private String BASC_DT;
	private String LAT;
	private String LNG;
	private String TEMP;
	private String WEATHER;
	private String AQIUS;
	private String ASTHMA;

	public DBDO(String ADDRESS, String BASC_DT, String LAT, String LNG, String TEMP, String WEATHER, String AQIUS, String ASTHMA) {
		this.ADDRESS = ADDRESS;
		this.BASC_DT = BASC_DT;
		this.LAT = LAT;
		this.LNG = LNG;
		this.TEMP = TEMP;
		this.WEATHER = WEATHER;
		this.AQIUS = AQIUS;
		this.ASTHMA = ASTHMA;
	}
	
	public DBDO() {
		
	}
	
	public String getADDRESS() {
		return ADDRESS;
	}
	public void setADDRESS(String aDDRESS) {
		ADDRESS = aDDRESS;
	}
	public String getBASC_DT() {
		return BASC_DT;
	}

	public void setBASC_DT(String bASC_DT) {
		BASC_DT = bASC_DT;
	}
	public String getLAT() {
		return LAT;
	}
	public void setLAT(String lAT) {
		LAT = lAT;
	}
	public String getLNG() {
		return LNG;
	}
	public void setLNG(String lNG) {
		LNG = lNG;
	}
	public String getTEMP() {
		return TEMP;
	}
	public void setTEMP(String tEMP) {
		TEMP = tEMP;
	}
	public String getWEATHER() {
		return WEATHER;
	}
	public void setWEATHER(String wEATHER) {
		WEATHER = wEATHER;
	}
	public String getAQIUS() {
		return AQIUS;
	}
	public void setAQIUS(String aQIUS) {
		AQIUS = aQIUS;
	}
	public String getASTHMA() {
		return ASTHMA;
	}

	public void setASTHMA(String aSTHMA) {
		ASTHMA = aSTHMA;
	}
	
	
	
}
