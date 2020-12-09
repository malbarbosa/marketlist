package br.com.marketlist.api.model;

import java.io.Serializable;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Builder
@Document(collection = "marketlist")
@ToString
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class Marketlist extends AbstractModel implements Cloneable,Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1138925937957710237L;

	@Getter @Setter
	private String id;
	
	@Getter @Setter
	private String name;
	
	@Getter @Setter
	private List<Item> items;

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

	@Override
	public Marketlist clone(){
		try {
			return (Marketlist) super.clone();
		} catch (CloneNotSupportedException e) {
			return new Marketlist();
			
		}
		
	}
}
