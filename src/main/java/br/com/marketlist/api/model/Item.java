package br.com.marketlist.api.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Getter;
import lombok.Setter;

@Document(collection = "items")
public class Item implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5262854310996863136L;
	@Getter @Setter
	private String id;
	
	@Getter @Setter
	private String name;
	
	@Getter @Setter
	private Category category;

	@Getter @Setter
	private long code;

	@Getter 
	protected long version;
	
	@Getter @Setter
	private boolean deleted;
	
	@Getter @Setter
	private OffsetDateTime createdAt;
	@Getter @Setter
	private Optional<UserApp> createdFor;
	

	public void nextVersion() {
		this.version += 1;
	}
	
	public void setWhoAndWhenCreatedRegistry(Optional<UserApp> createdBy) {
		setCreatedAt(OffsetDateTime.now());
		setCreatedFor(createdBy);
	}

	
}
