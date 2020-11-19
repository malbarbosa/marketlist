package br.com.marketlist.api.model;

import java.io.Serializable;

import lombok.Data;

@Data
public class ItemImage implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1914952193793865790L;
	private String name;
	private String bytes;
}
