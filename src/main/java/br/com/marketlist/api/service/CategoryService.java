package br.com.marketlist.api.service;

import java.util.List;
import java.util.Optional;

import br.com.marketlist.api.model.Category;

public interface CategoryService {

	Category create(Category category);

	Category update(Category category);

	void delete(Category category);

	Optional<Category> findByName(String name);

	List<Category> findAllByFilter(String name);

}
