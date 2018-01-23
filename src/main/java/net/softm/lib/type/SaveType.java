package net.softm.lib.type;
/**
 * SaveType
 * 저장상태
 * @author softm 
 */
public enum SaveType {
	SAVE("임시저장","S"),
	END("확정","C");

	private String typeNm;
	private String typeCd;
	
	SaveType(String typeNm, String typeCd) {
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