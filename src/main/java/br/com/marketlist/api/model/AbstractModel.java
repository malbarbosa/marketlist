package br.com.marketlist.api.model;

import java.time.OffsetDateTime;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
public class AbstractModel {
	
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
