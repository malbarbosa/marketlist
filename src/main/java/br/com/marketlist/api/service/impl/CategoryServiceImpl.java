package br.com.marketlist.api.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.marketlist.api.model.Category;
import br.com.marketlist.api.repository.CategoryRepository;
import br.com.marketlist.api.service.AbstractService;
import br.com.marketlist.api.service.CategoryService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class CategoryServiceImpl extends AbstractService<Category> implements CategoryService {

	@Autowired
	private CategoryRepository repository;
	
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
		
		this.delete(category);
		category.setId(null);
		Category updatedCategory = save(category);
		log.info("END - updatedCategory: {"+updatedCategory.toString()+"}");
		return updatedCategory;
	}

	
	@Override
	public void delete(Category category) {
		Category categoryToDelete = category.clone();
		log.info("BEGIN - Class=CategoryServiceImpl, Method=delete, parameters= category: {"+categoryToDelete.toString()+"}");
		if(categoryToDelete != null && categoryToDelete.getId() != null) {
			categoryToDelete.setWhoAndWhenDeletedRegistry(userService.getUserFromToken());
			repository.save(categoryToDelete);
			log.info("END - deletedCategory: {"+categoryToDelete.toString()+"}");
		}
	}
	

	@Override
	public Optional<Category> findByName(String name) {
		return repository.findByName(name);
	}

	@Override
	public List<Category> findAllLastVersion() {
		log.info("BEGIN - Class=CategoryServiceImpl, Method=findAllLastVersion");
		log.info("Starting searching category last version");
		return repository.findAllOrderByNameDesc();
	}

	@Override
	protected long getCount() {
		return repository.count();
	}

	@Override
	protected Category persist(Category entity) {
		return repository.save(entity);
	}
	
}
