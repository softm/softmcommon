package net.softm.lib.dto;

import java.util.ArrayList;

/**
 * BaseDataDTO
 * @param <T>
 * @author softm
 */
public class BaseDataDTO<T> extends BaseDTO {
//	private ArrayList<CodeDTOData> data; 
	private ArrayList<T> data; 
	public BaseDataDTO() {
		super();
	}
//	public ArrayList<CodeDTOData> getData() {
	public ArrayList<T> getData() {
		return data;
	}

//	public void setData(ArrayList<CodeDTOData> data) {
	public void setData(ArrayList<T> data) {
		this.data = data;
	}
}
