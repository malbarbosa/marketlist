package br.com.marketlist.api.model;

import java.util.Optional;

public abstract class AbstractModel {
	
	public abstract void nextVersion();
	
	public abstract void setWhoAndWhenCreatedRegistry(Optional<UserApp> createdBy);
	
	public abstract void setWhoAndWhenDeletedRegistry(Optional<UserApp> createdBy);
	
	public abstract long getCode();
	public abstract void setCode(long code);
}
