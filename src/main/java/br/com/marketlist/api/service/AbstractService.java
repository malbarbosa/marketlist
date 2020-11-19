package br.com.marketlist.api.service;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.marketlist.api.model.AbstractModel;

public abstract class AbstractService<T> {
	
	@Autowired
	protected UserService userService;
	
	protected T save(AbstractModel model) {
		model.nextVersion();
		model.setWhoAndWhenCreatedRegistry(userService.getUserFromToken());
		long count = this.getCount();
		if(model.getCode() == 0) {
			model.setCode(count == 0?1:++count);	
		}
		return this.persist((T)model);
	}

	protected abstract long getCount();
	
	protected abstract T persist(T object);

}
