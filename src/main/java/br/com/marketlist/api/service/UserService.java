package br.com.marketlist.api.service;

import java.util.Optional;

import br.com.marketlist.api.model.UserApp;

public interface UserService {

	UserApp create(UserApp user);

	Optional<UserApp> findByEmail(String email);

	UserApp update(UserApp user);

	void delete(UserApp user);

	Optional<UserApp> getUserFromToken();

}
