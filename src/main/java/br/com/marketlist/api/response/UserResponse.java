package br.com.marketlist.api.response;

import java.io.Serializable;

import lombok.Data;

@Data
public class UserResponse implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9014606199977832674L;
	
	private String id;
	private String name;
	private String email;
}
