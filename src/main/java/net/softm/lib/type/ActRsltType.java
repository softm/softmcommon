package net.softm.lib.type;
/**
 * ActRsltType
 * 조치결과
 * @author softm 
 */
public enum ActRsltType {
	TYPE1("재작업" , "1" ),
	TYPE2("폐기"  ,  "2" ),
	TYPE3("수리"  ,  "3" ),
	TYPE4("특채"  ,  "4" ),
	TYPE5("기타"  ,  "5" );
	
	private String typeNm;
	private String typeCd;
	
	ActRsltType(String typeNm, String typeCd) {
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