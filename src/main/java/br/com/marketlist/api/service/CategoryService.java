package br.com.marketlist.api.service;

import java.time.OffsetDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.marketlist.api.model.Category;
import br.com.marketlist.api.repository.CategoryRepository;

@Service
public class CategoryService {

	@Autowired
	private CategoryRepository repository;
	
	@Transactional(timeout = 2000, 
			noRollbackFor = Exception.class, 
			readOnly = true,
			propagation = Propagation.REQUIRED)
	public Category create(final Category category){
		category.setDateCreated(OffsetDateTime.now());
		return repository.save(category);
	}
	
	@Transactional(timeout = 2000, 
			noRollbackFor = Exception.class, 
			readOnly = true,
			propagation = Propagation.REQUIRED)
	public Category update(Category category) {
		category.setDateUpdated(OffsetDateTime.now());
		return repository.save(category);
	}
	
	@Transactional(timeout = 2000, 
			noRollbackFor = Exception.class, 
			readOnly = true,
			propagation = Propagation.REQUIRED)
	public void delete(Category category) {
		if(category != null && category.getId() != null) {
			repository.delete(category);
		}
	}
	
}
