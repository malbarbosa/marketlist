package br.com.marketlist.api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.marketlist.api.exception.EntityExists;
import br.com.marketlist.api.exception.EntityNotFound;
import br.com.marketlist.api.model.UserApp;
import br.com.marketlist.api.repository.UserRepository;
import br.com.marketlist.api.request.UserRequest;
import br.com.marketlist.api.response.UserResponse;
import br.com.marketlist.api.service.UserService;
import br.com.marketlist.api.utils.MapperUtil;

@RestController
@RequestMapping("/api/v1/users")
public class UserController extends AbstractController{
	
	@Autowired
	private UserService service;
	
	@Autowired
	private UserRepository repository;
	
	@GetMapping
	public List<UserApp> findAll(){
		return (List<UserApp>) repository.findAll();
	}
	
	@GetMapping("/{id}")
	public UserApp findById(@RequestParam("id") String id) {
		Optional<UserApp> category = repository.findById(id);
		if(!category.isPresent()) {
			throw new EntityNotFound();
		}
		return category.get();
		
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED, reason = "UserApp create!")
	public UserResponse create(@Validated @RequestBody UserRequest userRequest) {
		UserApp userCreated = null;
		Optional<UserApp> userApp = service.findByEmail(userRequest.getEmail());
		if(userApp.isPresent()) {
			throw new EntityExists();
		}else{
			userCreated = service.create(MapperUtil.map(userRequest, UserApp.class));
		}
		return MapperUtil.map(userCreated, UserResponse.class);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "UserApp updated!")
	public UserResponse update(@PathVariable(name = "id") String id, @Validated  @RequestBody UserRequest userRequest) {
		Optional<UserApp> userApp = repository.findById(id);
		if(!userApp.isPresent()) {
			throw new EntityNotFound();
		}
		UserApp userFound = userApp.get();
		userRequest.setEmail(userFound.getEmail());
		MapperUtil.mapTo(userRequest, userFound);
		UserApp userUpdated = service.update(userFound);
		return MapperUtil.map(userUpdated, UserResponse.class);
	}
}
