package net.softm.lib.type;
/**
 * FileAsrtType
 * 파일 구분
 * @author softm 
 */
public enum FileAsrtType {
	RQSTS("조치요구"  ,"RQSTS"),
	RSLT("조치결과","RSLT");
	
	private String typeNm;
	private String typeCd;
	
	FileAsrtType(String typeNm, String typeCd) {
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