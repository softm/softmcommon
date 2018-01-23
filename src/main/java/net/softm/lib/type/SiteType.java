package net.softm.lib.type;
/**
 * SiteType
 * 로그인타입
 * @author softm 
 */
public enum SiteType {
	SIGONG("시공사","C"),
	GAMRI("감리사","S");
	
	private String typeNm;
	private String typeCd;
	
	SiteType(String typeNm, String typeCd) {
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