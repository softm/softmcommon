package net.softm.lib.type;
/**
 * RprtType
 * 보고서 구분
 * @author softm 
 */
public enum RprtType {
	NCR("부적합"  ,"NCR"),
	CAR("시정조치","CAR");
	
	private String typeNm;
	private String typeCd;
	
	RprtType(String typeNm, String typeCd) {
		this.typeNm = typeNm;
		this.typeCd = typeCd;
	}

	public String getTypeNm() {
		return typeNm;
	}
	
	public String getTypeCd() {
		return typeCd;
	}


	public void setTypeNm(String typeNm) {
		this.typeNm = typeNm;
	}
	
	public void setTypeCd(String typeCd) {
		this.typeCd = typeCd;
	}

}	