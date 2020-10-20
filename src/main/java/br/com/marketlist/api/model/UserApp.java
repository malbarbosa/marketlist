package br.com.marketlist.api.model;

import java.time.OffsetDateTime;

import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;

@Data
@Document(collection = "userApp")
public class UserApp{
	
	private String id;
	private String name;
	private String email;
	@JsonIgnore(value = true)
	private String password;
	private OffsetDateTime dateCreated;
	private OffsetDateTime dateUpdated;
	
	
	
}
