package br.com.marketlist.api.service;

import java.util.List;
import java.util.Optional;

import br.com.marketlist.api.model.Item;

public interface ItemService {
	
	public List<Item> findAllByFilter(String name,String categoryId);
	
	public Item create(final Item item);
	
	public Item update(final Item item);
	
	public Optional<Item> delete(final Item item);

	List<Item> findAllLastVersion();

}
