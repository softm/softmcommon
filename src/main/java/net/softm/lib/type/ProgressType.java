package net.softm.lib.type;
/**
 * ProgressType
 * 처리상태
 * @author softm 
 */
public enum ProgressType {
	START("시공사작성중"   ,"01"),
	ING1("감리단검측요청  ","02"),
	ING2("감리단검측"    ,"03"),
	END("검측통보완료"   ,"04");
	
	private String typeNm;
	private String typeCd;
	
	ProgressType(String typeNm, String typeCd) {
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