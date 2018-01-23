package net.softm.lib.dto;

/**
 * BaseOneDTO
 * @param <T>
 * @author softm
 */
public class BaseOneDTO<T> extends BaseDTO {
//	private ArrayList<CodeDTOData> data; 
	private T data; 
	public BaseOneDTO() {
		super();
	}
//	public ArrayList<CodeDTOData> getData() {
	public T getData() {
		return data;
	}

//	public void setData(ArrayList<CodeDTOData> data) {
	public void setData(T data) {
		this.data = data;
	}
}
