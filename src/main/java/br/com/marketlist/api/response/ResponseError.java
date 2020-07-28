package br.com.marketlist.api.response;

import org.joda.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ResponseError {
	
	private String message;
	private LocalDateTime dateHour;


}
