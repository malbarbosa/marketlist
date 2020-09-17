package br.com.marketlist.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.marketlist.api.exception.EntityNotFound;
import br.com.marketlist.api.model.Item;
import br.com.marketlist.api.repository.ItemRepository;
import br.com.marketlist.api.request.ItemRequest;
import br.com.marketlist.api.response.ItemResponse;
import br.com.marketlist.api.service.ItemService;
import br.com.marketlist.api.utils.MapperUtil;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/items")
public class ItemController extends AbstractController{
	
	@Autowired
	private ItemService service;
	
	@Autowired
	private ItemRepository repository;
	
	@GetMapping
	public List<Item> findAll(){
		return (List<Item>) repository.findAll();
	}
	
	@GetMapping("/{id}")
	public Item findById(@RequestParam("id") String id) {
		log.info("BEGIN - Class=ItemController, Method=findById, parameters= {id:"+id+"}");
		Optional<Item> ItemFound = repository.findById(id);
		if(!ItemFound.isPresent()) {
			log.info("Item not found");
			throw new EntityNotFound("Item not found!");
		}
		Item Item = ItemFound.get();
		log.info("END - Item: {"+ Item.toString()+"}");
		return Item;
		
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Item create!")
	public ItemResponse create(@Validated @RequestBody ItemRequest ItemRequest) {
		log.info("BEGIN - Class=ItemController, Method=create, parameters= {ItemRequest:"+ItemRequest.toString()+"}");
		Item ItemCreated = service.create(MapperUtil.map(ItemRequest, Item.class));
		log.info("END - ItemCreated: {"+ItemCreated.toString()+"}");
		return MapperUtil.map(ItemCreated, ItemResponse.class);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Item  updated!")
	public void update(@PathVariable(name = "id") String id, @Validated  @RequestBody ItemRequest ItemRequest) {
		log.info("BEGIN - Class=ItemController, Method=update, parameters= {id:"+id+",ItemRequest:"+ItemRequest.toString()+"}");
		Optional<Item> Item = repository.findById(id);
		if(!Item.isPresent()) {
			log.info("Item not found");
			throw new EntityNotFound("Item not found!");
		}
		
        Item ItemFound = Item.get();
		log.info("ItemFound: {"+ ItemFound.toString()+"}");
		MapperUtil.mapTo(ItemRequest, ItemFound);
		Item ItemUpdated = service.update(ItemFound);
		log.info("END - ItemUpdated: {"+ItemUpdated.toString()+"}");
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK, reason = "Item  deleted!")
	public void delete(@PathVariable(name = "id") String id, @Validated  @RequestBody ItemRequest ItemRequest) {
		log.info("BEGIN - Class=ItemController, Method=delete, parameters= {id:"+id+",ItemRequest:"+ItemRequest.toString()+"}");
		Optional<Item> Item = repository.findById(id);
		if(!Item.isPresent()) {
			log.info("Item not found");
			throw new EntityNotFound("Item not found!");
		}
		Item ItemFound = Item.get();
		log.info("ItemFound: {"+ ItemFound.toString()+"}");
		ItemRequest.setId(id);
		MapperUtil.mapTo(ItemRequest, ItemFound);
		service.delete(ItemFound);
		log.info("END - ItemDeleted: {"+ItemFound.toString()+"}");
	}
}
