package br.com.marketlist.api.service;

import java.util.Optional;

import br.com.marketlist.api.model.UserApp;

public interface AuthService {
	
	public Optional<UserApp> authenticate(final String email, final String password);

}
