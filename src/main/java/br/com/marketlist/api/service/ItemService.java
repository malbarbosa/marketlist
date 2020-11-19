package br.com.marketlist.api.service;

import java.util.List;
import java.util.Optional;

import br.com.marketlist.api.model.Item;

public interface ItemService {
	
	public Optional<Item> findLastVersionBy(String name);
	
	public Item create(final Item item);
	
	public Item update(final Item item);
	
	public Optional<Item> delete(final Item item);

	List<Item> findAllLastVersion();

}
