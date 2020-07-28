package br.com.marketlist.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND)
public class EntityNotFound extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2208878752415238464L;

}
