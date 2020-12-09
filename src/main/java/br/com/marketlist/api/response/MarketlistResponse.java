package br.com.marketlist.api.response;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MarketlistResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4843211832636062269L;
	private String id;
	private String name;
	private List<ItemResponse> items = new ArrayList<>();

}
