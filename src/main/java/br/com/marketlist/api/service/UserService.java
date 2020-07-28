package br.com.marketlist.api.service;

import static java.util.Collections.emptyList;

import java.time.OffsetDateTime;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import br.com.marketlist.api.exception.EntityNotFound;
import br.com.marketlist.api.model.UserApp;
import br.com.marketlist.api.repository.UserRepository;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Transactional(timeout = 2000, 
			noRollbackFor = Exception.class, 
			readOnly = true,
			propagation = Propagation.REQUIRED)
	public UserApp create(final UserApp user){
		user.setDateCreated(OffsetDateTime.now());
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return repository.save(user);
	}
	
	@Transactional(timeout = 2000, 
			noRollbackFor = Exception.class, 
			readOnly = true,
			propagation = Propagation.NOT_SUPPORTED)
	public Optional<UserApp> findByEmail(final String email) {
		Optional<UserApp> userApp = repository.findByEmail(email);
		userApp.orElseThrow(()-> new EntityNotFound());
		return userApp;
	}
	
	@Transactional(timeout = 2000, 
			noRollbackFor = Exception.class, 
			readOnly = true,
			propagation = Propagation.REQUIRED)
	public UserApp update(UserApp user) {
		user.setDateUpdated(OffsetDateTime.now());
		return repository.save(user);
	}
	
	@Transactional(timeout = 2000, 
			noRollbackFor = Exception.class, 
			readOnly = true,
			propagation = Propagation.REQUIRED)
	public void delete(UserApp user) {
		if(user != null && user.getId() != null) {
			repository.delete(user);
		}
	}

	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
    	Optional<UserApp> userFound = repository.findByEmail(email); 
		 if (!userFound.isPresent()) {
	            throw new UsernameNotFoundException(email);
	        }
    	 return new User(userFound.get().getEmail(), userFound.get().getPassword(), emptyList());
    }
	
}
