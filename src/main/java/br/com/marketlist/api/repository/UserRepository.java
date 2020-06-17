package br.com.marketlist.api.repository;

import java.util.Optional;

import org.socialsignin.spring.data.dynamodb.repository.EnableScan;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import br.com.marketlist.api.model.User;

@EnableScan
@Component
public interface UserRepository extends CrudRepository<User, Long>{
	
	Optional<User> findByEmail(@Param("email") String email);

}
