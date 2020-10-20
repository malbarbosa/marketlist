package br.com.marketlist.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marketlist.api.model.Item;
import br.com.marketlist.api.repository.ItemRepository;
import br.com.marketlist.api.service.ItemService;
import br.com.marketlist.api.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ItemServiceImpl implements ItemService{

	@Autowired
	private ItemRepository repository;
	
	@Autowired
	private UserService userService;
	
	
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
		item.setId(null);
		Item updateditem = save(item);
		log.info("END - updatedItem: {"+updateditem.toString()+"}");
		return updateditem;
	}

	
	@Override
	public Optional<Item> delete(final Item item) {
		log.info("BEGIN - Class=ItemServiceImpl, Method=delete, parameters= item: {"+item.toString()+"}");
		if(item != null && item.getId() != null) {
			item.setDeleted(true);
			log.info("END - deletedItem: {"+item.toString()+"}");
			return Optional.of(save(item));
		}
		return Optional.empty();
		
	}
	
	private Item save(final Item item) {
		item.nextVersion();
		item.setWhoAndWhenCreatedRegistry(userService.getUserFromToken());
		item.setCode(item.getCode() == 0?repository.count()+1:item.getCode());
		return repository.save(item);
	}
	
}
