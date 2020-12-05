package br.com.marketlist.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Component;

import br.com.marketlist.api.model.Category;

@Component
public interface CategoryRepository extends CrudRepository<Category, String>,QueryByExampleExecutor<Category>{


	Optional<Category> findFirstByNameOrderByVersionDesc(String name);

	Optional<Category> findByName(String name);

	List<Category> findFistGroupByNameOrderByVersionDesc();

	@Query("{'id' : ?0 , 'deleted' : false}")
	Optional<Category> findByIdAndNotDeleted(String id);

	@Query("{'deleted' : false}")
	List<Category> findAllOrderByNameDesc();
	
}
