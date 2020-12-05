package br.com.marketlist.api.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.QueryByExampleExecutor;
import org.springframework.stereotype.Component;

import br.com.marketlist.api.model.Item;

@Component
public interface ItemRepository extends CrudRepository<Item, String>, QueryByExampleExecutor<Item>  {
	
	@Query("{'id' : ?0 , 'deleted' : false}")
	Optional<Item> findFirstByNameOrderByVersionDesc(String name);

	@Query("{'deleted' : false}")
	List<Item> findAllOrderByNameDesc();

}
