package br.com.marketlist.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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
import br.com.marketlist.api.model.Marketlist;
import br.com.marketlist.api.repository.MarketlistRepository;
import br.com.marketlist.api.request.MarketlistRequest;
import br.com.marketlist.api.response.MarketlistResponse;
import br.com.marketlist.api.service.MarketlistService;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/v1/market-list")
@Slf4j
public class MarketlistController {
	
	@Autowired
	private MarketlistService service;
	
	@Autowired
	private MarketlistRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping
	public Page<MarketlistResponse> findAll(@RequestParam(name="name", required = false) String name){
		List<Marketlist> marketlist = service.findAllByFilter(name);
		return new PageImpl<>(marketlist.stream().map(item -> mapper.map(item, MarketlistResponse.class)).collect(Collectors.toList()));
	}
	
	@GetMapping("/{id}")
	public MarketlistResponse findById(@PathVariable("id") String id) {
		log.info("BEGIN - Class=MarketlistController, Method=findById, parameters= {id:"+id+"}");
		Optional<Marketlist> marketlistFound = repository.findById(id);
		if(!marketlistFound.isPresent()) {
			log.info("Marketlist not found");
			throw new EntityNotFound("Item not found!");
		}
		Marketlist marketlist = marketlistFound.get();
		log.info("END - Marketlist: {"+ marketlist.toString()+"}");
		return this.mapper.map(marketlist, MarketlistResponse.class);
		
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Item create!")
	public MarketlistResponse create(@Validated @RequestBody MarketlistRequest marketlistRequest) {
		log.info("BEGIN - Class=MarketlistController, Method=create, parameters= {ItemRequest:"+marketlistRequest.toString()+"}");
		Marketlist marketlist = mapper.map(marketlistRequest, Marketlist.class);
		marketlist = service.create(marketlist);
		log.info("END - Marketlist Created: {"+marketlist.toString()+"}");
		return mapper.map(marketlist, MarketlistResponse.class);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Item  updated!")
	public void update(@PathVariable(name = "id") String id, @Validated  @RequestBody MarketlistRequest marketlistRequest) {
		log.info("BEGIN - Class=MarketlistController, Method=update, parameters= {id:"+id+",ItemRequest:"+marketlistRequest.toString()+"}");
		var marketlist = mapper.map(marketlistRequest, Marketlist.class);
		var marketlistUpdated = service.update(marketlist);
		log.info("END - MarketList updated: {"+marketlistUpdated.toString()+"}");
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK, reason = "Item deleted!")
	public MarketlistResponse delete(@PathVariable("id") String id) {
		log.info("BEGIN - Class=MarketlistController, Method=delete, parameters= {id:"+id+"}");
		Optional<Marketlist> marketlistFound = repository.findById(id);
		if(!marketlistFound.isPresent()) {
			log.info("Marketlist not found");
			throw new EntityNotFound("Item not found!");
		}
		var marketlist = service.delete(marketlistFound.get());
		log.info("END - Marketlist deleted: {"+marketlist.toString()+"}");
		return mapper.map(marketlist.get(), MarketlistResponse.class);
	}
	

}
