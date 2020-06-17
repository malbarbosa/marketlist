package br.com.marketlist.api.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

public abstract class AbstractController {
	
	@Autowired
	protected MessageSource messages;
	

}
