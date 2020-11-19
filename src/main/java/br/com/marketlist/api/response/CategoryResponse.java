package br.com.marketlist.api.response;

import java.io.Serializable;
import java.time.OffsetDateTime;

import lombok.Data;

@Data
public class CategoryResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9014606199977832674L;
	
	private String id;
	private String name;
	private String code;
	private OffsetDateTime createdAt;
	private UserResponse createdFor;
}
