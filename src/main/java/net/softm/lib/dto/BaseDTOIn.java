package net.softm.lib.dto;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * BaseDTOIn
 * @author softm 
 */
public class BaseDTOIn {
	protected String sysRegId; // 작성자 : 로그인ID
	
	public BaseDTOIn() {
		super();
	}
	public String getSysRegId() {
		return sysRegId;
	}
	public void setSysRegId(String sysRegId) {
		this.sysRegId = sysRegId;
	}

	@Override
	public String toString() {
       return ToStringBuilder.reflectionToString(this).toString();		
	}
//	
}
