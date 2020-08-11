package br.com.marketlist.api.repository;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;

import br.com.marketlist.api.model.Category;

@EnableScan
@Component
public interface CategoryRepository extends CrudRepository<Category, String>{
	
}
