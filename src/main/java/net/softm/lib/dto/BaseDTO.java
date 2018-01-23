package net.softm.lib.dto;
//       net.softm.lib.dto.BaseDTO.setMsgCode(java.lang.String)
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * BaseDTO
 * @author softm
 */
public class BaseDTO {
	private String msg;
	private String msgCode;
	
	public BaseDTO() {
		super();
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsgCode() {
		return msgCode;
	}

	public void setMsgCode(String msgCode) {
		this.msgCode = msgCode;
	}

	@Override
	public String toString() {
       return ToStringBuilder.reflectionToString(this).toString();
	}
//	
}
