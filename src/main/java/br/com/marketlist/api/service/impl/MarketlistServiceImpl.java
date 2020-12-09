package br.com.marketlist.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.stereotype.Service;

import br.com.marketlist.api.model.Marketlist;
import br.com.marketlist.api.repository.MarketlistRepository;
import br.com.marketlist.api.service.AbstractService;
import br.com.marketlist.api.service.MarketlistService;
import br.com.marketlist.api.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MarketlistServiceImpl extends AbstractService<Marketlist>  implements MarketlistService{
	
	@Autowired
	private MarketlistRepository repository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public List<Marketlist> findAllByFilter(String name) {
		log.info(String.format("BEGIN - Class=MarketlistServiceImpl, Method=findAllByFilter, parameters= {name: %s}",name));
		log.info("Starting searching item last version");
		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnoreCase()
				.withIgnorePaths("code","version")
				.withMatcher("name", match -> match.regex())
				.withMatcher("deleted", match -> match.exact())
				.withIgnoreNullValues();
		Example<Marketlist> exampleQuery = Example.of(Marketlist.builder().name(name).deleted(false).build(), matcher);
		Iterable<Marketlist> results = repository.findAll(exampleQuery);
		return (List<Marketlist>) results;
	}

	@Override
	public Marketlist create(Marketlist marketlist) {
		log.info(String.format("BEGIN - Class=MarketlistServiceImpl, Method=create, parameters= {marketlist: %s}",marketlist.toString()));
		return repository.save(marketlist);
	}

	@Override
	public Optional<Marketlist> delete(Marketlist marketlistFound) {
		final Marketlist itemToDeleted = marketlistFound.clone();
		log.info("BEGIN - Class=MarketlistServiceImpl, Method=delete, parameters= item: {"+itemToDeleted.toString()+"}");
		if(itemToDeleted != null && itemToDeleted.getId() != null) {
			log.info("END - deletedItem: {"+itemToDeleted.toString()+"}");
			itemToDeleted.setWhoAndWhenDeletedRegistry(userService.getUserFromToken());
			repository.save(itemToDeleted);
			return Optional.of(marketlistFound);
		}
		return Optional.empty();
		
	}

	@Override
	public Optional<Marketlist> update(Marketlist marketlist) {
		log.info("BEGIN - Class=MarketlistServiceImpl, Method=update, parameters= marketlist: {"+marketlist.toString()+"}");
		this.delete(marketlist);
		marketlist.setId(null);
		Marketlist updatedMarketList = save(marketlist);
		log.info("END - updatedCategory: {"+updatedMarketList.toString()+"}");
		return Optional.of(updatedMarketList);
	}

	@Override
	protected long getCount() {
		return repository.count();
	}
	@Override
	protected Marketlist persist(Marketlist entity) {
		return repository.save(entity);
	}

}
