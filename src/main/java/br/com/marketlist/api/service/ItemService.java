package br.com.marketlist.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marketlist.api.model.Item;
import br.com.marketlist.api.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ItemService {

	@Autowired
	private ItemRepository repository;
	
	@Autowired
	private UserService userService;
	
	public Optional<Item> findLastVersionBy(String name) {
		log.info(String.format("BEGIN - Class=ItemService, Method=findLastVersionBy, parameters= {name: %s}",name));
		log.info("Starting searching item last version");
		return repository.findFirstByNameOrderByVersionDesc(name);
	}
	
	public Item create(final Item item){
		log.info("BEGIN - Class=ItemService, Method=create, parameters= item: {"+item.toString()+"}");
		Item createditem = save(item);
		log.info("END - createdItem: {"+item.toString()+"}");
		return createditem;
	}
	
	public Item update(final Item item) {
		log.info("BEGIN - Class=ItemService, Method=update, parameters= item: {"+item.toString()+"}");
		log.info("Get a user from token");
		item.setId(null);
		Item updateditem = save(item);
		log.info("END - updatedItem: {"+updateditem.toString()+"}");
		return updateditem;
	}

	
	
	public void delete(final Item item) {
		log.info("BEGIN - Class=ItemService, Method=delete, parameters= item: {"+item.toString()+"}");
		if(item != null && item.getId() != null) {
			item.setDeleted(true);
			save(item);
			log.info("END - deletedItem: {"+item.toString()+"}");
		}
	}
	
	private Item save(final Item item) {
		item.nextVersion();
		item.setWhoAndWhenCreatedRegistry(userService.getUserFromToken());
		item.setCode(item.getCode() == 0?repository.count():item.getCode());
		return repository.save(item);
	}
	
}
