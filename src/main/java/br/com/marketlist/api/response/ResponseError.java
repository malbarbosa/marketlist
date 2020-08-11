package br.com.marketlist.api.response;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseError {
	
	private String message;
	private String dateHourError;


}
