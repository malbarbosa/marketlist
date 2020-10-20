package br.com.marketlist.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class IdCategoryRequest implements Serializable{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3897316214897667988L;
	@NotNull
	private String id;
	

}
