package br.com.marketlist.api.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Document(collection = "categories")
public class Category implements Cloneable,Serializable, Comparable<Category>{
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1066568526996715965L;
	@Getter @Setter
	private String id;
	
	@Getter @Setter
	private String name;
	
	@Getter @Setter
	private long code;
	
	@Getter 
	protected long version;
	
	@Getter @Setter
	private boolean deleted;
	
	@Getter @Setter
	private OffsetDateTime createdAt;
	@Getter @Setter
	private UserApp createdFor;
	

	public void nextVersion() {
		this.version += 1;
	}
	
	public void setWhoAndWhenCreatedRegistry(Optional<UserApp> createdBy) {
		setCreatedAt(OffsetDateTime.now());
		setCreatedFor(createdBy.get());
	}
	
	
	
	@Override
	public Category clone(){
		
		try {
			return (Category) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
			
		}
	}

	@Override
	public int compareTo(Category c1) {
		return (this.version <= c1.getVersion()?1:-1);
	}

	
	
}
