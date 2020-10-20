package br.com.marketlist.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import br.com.marketlist.api.model.Category;

@Component
public interface CategoryRepository extends CrudRepository<Category, String>{


	Optional<Category> findFirstByNameOrderByVersionDesc(String name);

	Optional<Category> findByName(String name);

	List<Category> findAllGroupByNameOrderByVersionDesc();
	
}
