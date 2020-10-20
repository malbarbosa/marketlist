package br.com.marketlist.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marketlist.api.model.Category;
import br.com.marketlist.api.repository.CategoryRepository;
import br.com.marketlist.api.service.CategoryService;
import br.com.marketlist.api.service.UserService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServiceImpl implements CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	@Autowired
	private UserService userService;
	
	@Override
	public Optional<Category> findLastVersionBy(String name) {
		log.info(String.format("BEGIN - Class=CategoryServiceImpl, Method=findLastVersionBy, parameters= {name: %s}",name));
		log.info("Starting searching category last version");
		return repository.findFirstByNameOrderByVersionDesc(name);
		
	}
	
	@Override
	public Category create(final Category category){
		log.info("BEGIN - Class=CategoryServiceImpl, Method=create, parameters= category: {"+category.toString()+"}");
		Category createdCategory = save(category);
		log.info("END - createdCategory: {"+category.toString()+"}");
		return createdCategory;
	}
	
	@Override
	public Category update(Category category) {
		log.info("BEGIN - Class=CategoryServiceImpl, Method=update, parameters= category: {"+category.toString()+"}");
		
		category.setId(null);
		Category updatedCategory = save(category);
		log.info("END - updatedCategory: {"+updatedCategory.toString()+"}");
		return updatedCategory;
	}

	
	@Override
	public void delete(Category category) {
		log.info("BEGIN - Class=CategoryServiceImpl, Method=delete, parameters= category: {"+category.toString()+"}");
		if(category != null && category.getId() != null) {
			category.setDeleted(true);
			save(category);
			log.info("END - deletedCategory: {"+category.toString()+"}");
		}
	}
	
	private Category save(Category category) {
		category.nextVersion();
		log.info("Get a user from token");
		category.setWhoAndWhenCreatedRegistry(userService.getUserFromToken());
		category.setCode(category.getCode() == 0?repository.count():category.getCode());
		return repository.save(category);
	}

	@Override
	public Optional<Category> findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public List<Category> findAllLastVersion() {
		log.info("BEGIN - Class=CategoryServiceImpl, Method=findAllLastVersion");
		log.info("Starting searching category last version");
		return repository.findAllGroupByNameOrderByVersionDesc();
	}
	
}
