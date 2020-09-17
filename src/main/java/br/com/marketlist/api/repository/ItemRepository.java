package br.com.marketlist.api.repository;

import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import br.com.marketlist.api.model.Item;

@EnableScan
@Component
public interface ItemRepository extends CrudRepository<Item, String>{

	Optional<Item> findFirstByNameOrderByVersionDesc(String name);
	
}
