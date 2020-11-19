package br.com.marketlist.api.request;

import java.io.Serializable;

import lombok.Data;

@Data
public class ImageRequest implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -2230890863118633112L;
	private String name;
	private String bytes;

}
