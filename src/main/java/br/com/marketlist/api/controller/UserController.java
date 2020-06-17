package br.com.marketlist.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.marketlist.api.exception.UserExists;
import br.com.marketlist.api.exception.UserNotFound;
import br.com.marketlist.api.model.User;
import br.com.marketlist.api.repository.UserRepository;
import br.com.marketlist.api.request.UserRequest;
import br.com.marketlist.api.service.UserService;
import br.com.marketlist.api.utils.MapperUtil;

@RestController
@RequestMapping("/v1/users")
public class UserController extends AbstractController{
	
	@Autowired
	private UserService service;
	
	@Autowired
	private UserRepository repository;
	
	
	@GetMapping
	public List<User> findAll(){
		return (List<User>) repository.findAll();
	}
	
	@GetMapping("/{id}")
	public User findById(@RequestParam("id") Long id) {
		Optional<User> category = repository.findById(id);
		if(!category.isPresent()) {
			throw new UserNotFound();
		}
		return category.get();
		
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED, reason = "User create!")
	public User create(@RequestBody UserRequest userRequest) {
		Optional<User> user = service.findByEmail(userRequest.getEmail());
		if(!user.isPresent()) {
			throw new UserExists();
		}
		return MapperUtil.map(userRequest, User.class);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "User updated!")
	public User update(@RequestParam("id") Long id, @RequestBody UserRequest userRequest) {
		Optional<User> user = repository.findById(id);
		if(!user.isPresent()) {
			throw new UserNotFound();
		}
		return MapperUtil.map(userRequest, User.class);
	}
}
