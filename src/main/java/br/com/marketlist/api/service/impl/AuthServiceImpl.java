package br.com.marketlist.api.service.impl;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.marketlist.api.model.UserApp;
import br.com.marketlist.api.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService{

	@Autowired
	private UserServiceImpl service;
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	
	
	@Transactional(timeout = 2000, 
			noRollbackFor = Exception.class, 
			readOnly = true,
			propagation = Propagation.NOT_SUPPORTED)
	@Override
	public Optional<UserApp> authenticate(final String email, final String password) {
		Optional<UserApp> user = service.findByEmail(email);
		if(!user.isPresent()) {
			return null;
		}
		boolean authenticated = passwordEncoder.matches(password, user.get().getPassword());
		if(authenticated) {
			return user;
		}else {
			return null;
		}
		
	}

	
	
}
