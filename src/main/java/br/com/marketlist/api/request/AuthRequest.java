package br.com.marketlist.api.request;

import java.io.Serializable;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.Data;

@Data
public class AuthRequest implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -9014606199977832674L;
	@NotBlank
	@Email
	private String email;
	@NotBlank
	@Size(max = 20, message = "{user.max_length}")
	private String password;

}
