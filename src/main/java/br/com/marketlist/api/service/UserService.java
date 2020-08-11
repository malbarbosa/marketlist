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

import br.com.marketlist.api.model.UserApp;
import br.com.marketlist.api.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
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
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		log.info("BEGIN - Class=UserService, Method=create, parameters= user: {"+user.toString()+"}");
		user.setDateCreated(OffsetDateTime.now());
		UserApp createdUser = repository.save(user);
		log.info("END - Created User. ID="+createdUser.getId());
		return createdUser;
	}
	
	@Transactional(timeout = 2000, 
			noRollbackFor = Exception.class, 
			readOnly = true,
			propagation = Propagation.NOT_SUPPORTED)
	public Optional<UserApp> findByEmail(final String email) {
		log.debug("BEGIN - Class=UserService, Method=findByEmail, parameters= {email="+email+"}");
		Optional<UserApp> userApp = repository.findByEmail(email);
		log.debug("END - User found: "+userApp.toString());
		return userApp;
	}
	
	@Transactional(timeout = 2000, 
			noRollbackFor = Exception.class, 
			readOnly = true,
			propagation = Propagation.REQUIRED)
	public UserApp update(UserApp user) {
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		log.debug("BEGIN - Class=UserService, Method=update, parameters= user: {"+user.toString()+"}");
		user.setDateUpdated(OffsetDateTime.now());
		log.debug("BEGIN - setting new Password");
		log.debug("END - new Password set");
		UserApp updatedUser = repository.save(user);
		log.debug("END - Updated User. updatedUser: {"+updatedUser.toString()+"}");
		return updatedUser; 
	}
	
	@Transactional(timeout = 2000, 
			noRollbackFor = Exception.class, 
			readOnly = true,
			propagation = Propagation.REQUIRED)
	public void delete(UserApp user) {
		log.debug("BEGIN - Class=UserService, Method=delete, parameters= user: {"+user.toString()+"}");
		if(user != null && user.getId() != null) {
			log.debug("BEGIN - Removing user: {"+user.getName()+"}");
			repository.delete(user);
			log.debug("END - User removed");
		}
	}

	@Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		log.debug("BEGIN - Class=UserService, Method=loadUserByUsername, parameters= {email="+email+"}");
		log.debug("BEGIN - repository.findByEmail ");
    	Optional<UserApp> userFound = repository.findByEmail(email);
    	log.debug("END - userFound: {"+userFound.toString()+"}");
		if (!userFound.isPresent()) {
		log.debug("UsernameNotFoundException - Email: "+email);
	      throw new UsernameNotFoundException(email);
	    }
		log.debug("Success - User founded.");
    	return new User(userFound.get().getEmail(), userFound.get().getPassword(), emptyList());
    }
	
}
