package br.com.marketlist.api.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document(collection = Category.COLLECTION_NAME)
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category extends AbstractModel implements Cloneable,Serializable, Comparable<Category>{
	
	
	public static final String COLLECTION_NAME = "categories";
	/**
	 * 
	 */
	private static final long serialVersionUID = -1066568526996715965L;
	@Getter @Setter
	private String id;
	
	@Getter @Setter
	private String name;
	
	
	private long code;
	
	@Getter 
	protected long version;
	
	@Getter 
	private boolean deleted;
	@Getter 
	private UserApp deletedFor;
	
	@Getter 
	private OffsetDateTime deletedAt;
	
	@Getter 
	private OffsetDateTime createdAt;
	@Getter 
	private UserApp createdFor;
	

	@Override
	public void nextVersion() {
		this.version += 1;
	}
	
	@Override
	public void setWhoAndWhenCreatedRegistry(Optional<UserApp> createdBy) {
		this.createdAt = OffsetDateTime.now();
		this.createdFor= createdBy.get();
	}
	
	@Override
	public void setWhoAndWhenDeletedRegistry(Optional<UserApp> createdBy) {
		this.deletedAt = OffsetDateTime.now();
		this.deletedFor = createdBy.get();
		this.deleted = true;
	}
	
	
	
	@Override
	public Category clone(){
		
		try {
			return (Category) super.clone();
		} catch (CloneNotSupportedException e) {
			return new Category();
			
		}
	}

	@Override
	public int compareTo(Category c1) {
		return (this.version <= c1.getVersion()?1:-1);
	}

	@Override
	public long getCode() {
		return this.code;
	}

	@Override
	public void setCode(long code) {
		this.code = code;
	}


	
	
	
}
