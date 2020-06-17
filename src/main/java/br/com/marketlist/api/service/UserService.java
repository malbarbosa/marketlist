package br.com.marketlist.api.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.marketlist.api.model.User;
import br.com.marketlist.api.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	
	@Transactional(timeout = 2000, 
			noRollbackFor = Exception.class, 
			readOnly = true,
			propagation = Propagation.REQUIRED)
	public User create(final User user){
		return repository.save(user);
	}
	
	@Transactional(timeout = 2000, 
			noRollbackFor = Exception.class, 
			readOnly = true,
			propagation = Propagation.NOT_SUPPORTED)
	public Optional<User> findByEmail(final String email) {
		return repository.findByEmail(email);
	}

	@Transactional(timeout = 2000, 
			noRollbackFor = Exception.class, 
			readOnly = true,
			propagation = Propagation.REQUIRED)
	public User update(User user) {
		return repository.save(user);
	}
	
}
