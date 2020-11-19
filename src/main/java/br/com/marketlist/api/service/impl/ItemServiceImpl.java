package br.com.marketlist.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marketlist.api.model.Item;
import br.com.marketlist.api.repository.ItemRepository;
import br.com.marketlist.api.service.AbstractService;
import br.com.marketlist.api.service.ItemService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ItemServiceImpl extends AbstractService<Item> implements ItemService{
	
	@Autowired
	protected ItemRepository repository;

	@Override
	public Optional<Item> findLastVersionBy(String name) {
		log.info(String.format("BEGIN - Class=ItemServiceImpl, Method=findLastVersionBy, parameters= {name: %s}",name));
		log.info("Starting searching item last version");
		return repository.findFirstByNameOrderByVersionDesc(name);
	}
	@Override
	public Item create(final Item item){
		log.info("BEGIN - Class=ItemServiceImpl, Method=create, parameters= item: {"+item.toString()+"}");
		Item createditem = save(item);
		log.info("END - createdItem: {"+item.toString()+"}");
		return createditem;
	}
	
	@Override
	public Item update(final Item item) {
		log.info("BEGIN - Class=ItemServiceImpl, Method=update, parameters= item: {"+item.toString()+"}");
		log.info("Get a user from token");
		this.delete(item);
		item.setId(null);
		Item updateditem = save(item);
		log.info("END - updatedItem: {"+updateditem.toString()+"}");
		return updateditem;
	}

	
	@Override
	public Optional<Item> delete(final Item item) {
		final Item itemToDeleted = item.clone();
		log.info("BEGIN - Class=ItemServiceImpl, Method=delete, parameters= item: {"+itemToDeleted.toString()+"}");
		if(itemToDeleted != null && itemToDeleted.getId() != null) {
			log.info("END - deletedItem: {"+itemToDeleted.toString()+"}");
			itemToDeleted.setWhoAndWhenDeletedRegistry(userService.getUserFromToken());
			repository.save(itemToDeleted);
			return Optional.of(item);
		}
		return Optional.empty();
		
	}

	@Override
	public List<Item> findAllLastVersion() {
		log.info("BEGIN - Class=CategoryServiceImpl, Method=findAllLastVersion");
		log.info("Starting searching category last version");
		return repository.findAllOrderByNameDesc();
	}
	@Override
	protected long getCount() {
		return repository.count();
	}
	@Override
	protected Item persist(Item entity) {
		return repository.save(entity);
	}

	
}
