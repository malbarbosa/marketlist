package br.com.marketlist.api.request;

import java.io.Serializable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class CategoryRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9014606199977832674L;
	
	@NotBlank
	@Size(max = 30)
	private String name;

}
