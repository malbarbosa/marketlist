package br.com.marketlist.api.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;

import br.com.marketlist.api.model.UserApp;

@Component
public interface UserRepository extends CrudRepository<UserApp, String>{
	
	Optional<UserApp> findByEmail(@Param("email") String email);
	
	Optional<UserApp> findByEmailAndPassword(@Param("email") String email, @Param("password") String password);

}
