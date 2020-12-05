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
import lombok.ToString;

@Document(collection = "items")
@ToString
@EqualsAndHashCode(callSuper = false)
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Item extends AbstractModel implements Serializable,Cloneable{

	
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
	private String brand;

	private long code;

	@Getter 
	protected long version;
	
	@Getter 
	private boolean deleted;
	
	@Getter 
	private UserApp deletedFor;
	
	@Getter 
	private OffsetDateTime deletedAt;
	
	@Getter @Setter
	private OffsetDateTime createdAt;
	@Getter @Setter
	private UserApp createdFor;
	

	@Override
	public void nextVersion() {
		this.version += 1;
	}
	
	@Override
	public void setWhoAndWhenCreatedRegistry(Optional<UserApp> createdBy) {
		setCreatedAt(OffsetDateTime.now());
		setCreatedFor(createdBy.get());
	}
	@Override
	public Item clone() {
		try {
			return (Item) super.clone();
		} catch (CloneNotSupportedException e) {
			return null;
			
		}
	}

	@Override
	public void setWhoAndWhenDeletedRegistry(Optional<UserApp> createdBy) {
		this.deletedAt = OffsetDateTime.now();
		this.deletedFor= createdBy.get();
		this.deleted = true;
		
	}
	@Override
	public long getCode() {
		return this.code;
	}

	@Override
	public void setCode(long code) {
		this.code=code;
		
	}
	
}
