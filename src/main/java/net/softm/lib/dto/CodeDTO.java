package net.softm.lib.dto;

/**
 * CodeDTO
 * @author softm
 */
public class CodeDTO {
	private String code;
	private String value;
	public CodeDTO() {
		super();
	}		
	public CodeDTO(String code, String value) {
		this.code = code;
		this.value = value;
	}		
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
	

}
