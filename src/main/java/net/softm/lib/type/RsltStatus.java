package net.softm.lib.type;
/**
 * RsltStatus
 * 조치결과확인
 * @author softm 
 */
public enum RsltStatus {
	TRUE("적합","T"),
	FALSE("부적합","F"),
	ING("~","I"),
	NONE("-","9");
	
	private String typeNm;
	private String typeCd;
	
	RsltStatus(String typeNm, String typeCd) {
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