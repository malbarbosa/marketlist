package br.com.marketlist.api.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class CategoryResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9014606199977832674L;
	
	private Integer id;
	private String name;
}
