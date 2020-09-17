package br.com.marketlist.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marketlist.api.model.Category;
import br.com.marketlist.api.repository.CategoryRepository;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	@Autowired
	private UserService userService;
	
	public Optional<Category> findLastVersionBy(String name) {
		log.info(String.format("BEGIN - Class=CategoryService, Method=findLastVersionBy, parameters= {name: %s}",name));
		log.info("Starting searching category last version");
		return repository.findFirstByNameOrderByVersionDesc(name);
		
	}
	
	public Category create(final Category category){
		log.info("BEGIN - Class=CategoryService, Method=create, parameters= category: {"+category.toString()+"}");
		Category createdCategory = save(category);
		log.info("END - createdCategory: {"+category.toString()+"}");
		return createdCategory;
	}
	
	public Category update(Category category) {
		log.info("BEGIN - Class=CategoryService, Method=update, parameters= category: {"+category.toString()+"}");
		log.info("Get a user from token");
		category.setId(null);
		Category updatedCategory = save(category);
		log.info("END - updatedCategory: {"+updatedCategory.toString()+"}");
		return updatedCategory;
	}

	
	
	public void delete(Category category) {
		log.info("BEGIN - Class=CategoryService, Method=delete, parameters= category: {"+category.toString()+"}");
		if(category != null && category.getId() != null) {
			category.setDeleted(true);
			save(category);
			log.info("END - deletedCategory: {"+category.toString()+"}");
		}
	}
	
	private Category save(final Category category) {
		category.nextVersion();
		category.setWhoAndWhenCreatedRegistry(userService.getUserFromToken());
		category.setCode(category.getCode() == 0?repository.count():category.getCode());
		return repository.save(category);
	}
	
}
