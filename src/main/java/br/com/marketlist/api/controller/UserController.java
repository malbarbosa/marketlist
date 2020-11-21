package br.com.marketlist.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.marketlist.api.exception.EntityExists;
import br.com.marketlist.api.exception.EntityNotFound;
import br.com.marketlist.api.model.UserApp;
import br.com.marketlist.api.repository.UserRepository;
import br.com.marketlist.api.request.UserRequest;
import br.com.marketlist.api.response.UserResponse;
import br.com.marketlist.api.service.impl.UserServiceImpl;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/users")
public class UserController extends AbstractController{
	
	@Autowired
	private UserServiceImpl service;
	
	@Autowired
	private UserRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping
	public Page<UserResponse> findAll(){
		Iterable<UserApp> iterable = repository.findAll();
		List<UserApp> content = StreamSupport.stream(iterable.spliterator(), false)
	    .collect(Collectors.toList());
		return new PageImpl<>(content.stream().map(item -> mapper.map(item, UserResponse.class)).collect(Collectors.toList()));
	}
	
	@GetMapping("/{id}")
	public UserResponse findById(@PathVariable(name = "id") String id) {
		log.info("BEGIN - Class=UserController, Method=findById, parameters= {id:"+id+"}");
		log.info("BEGIN - repository.findById ");
		Optional<UserApp> user = repository.findById(id);
		log.info("END - repository.findById ");
		log.info("user.isPresent()="+user.isPresent());
		if(!user.isPresent()) {
			log.info("EntityNotFound - id:"+id);
			throw new EntityNotFound("User not found!");
		}
		UserApp userApp = user.get();
		log.info("user:{"+userApp.toString()+"}");
		return mapper.map(userApp, UserResponse.class);
		
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED, reason = "UserApp create!")
	public UserResponse create(@Validated @RequestBody UserRequest userRequest) {
		log.info("BEGIN - Class=UserController, Method=create, parameters= userRequest:{"+userRequest.toString()+"}");
		UserApp userCreated = null;
		Optional<UserApp> userApp = service.findByEmail(userRequest.getEmail());
		if(userApp.isPresent()) {
			log.info("BEGIN - Class=UserController, Method=create, EntityExists");
			throw new EntityExists("User is already exists!");
		}else{
			log.info("Create User ");
			userCreated = service.create(mapper.map(userRequest, UserApp.class));
		}
		return mapper.map(userCreated, UserResponse.class);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "UserApp updated!")
	public UserResponse update(@PathVariable(name = "id") String id, @Validated  @RequestBody UserRequest userRequest) {
		log.info("BEGIN - Class=UserController, Method=update, parameters= {id:"+id+",userRequest:{"+userRequest.toString()+"}}");
		Optional<UserApp> userApp = repository.findById(id);
		if(!userApp.isPresent()) {
			log.info("User not found");
			throw new EntityNotFound("User not found!");
		}
		UserApp userFound = userApp.get();
		log.info("userFound: {"+userFound.toString()+"}");
		userRequest.setEmail(userFound.getEmail());
		userRequest.setId(id);
		mapper.map(userRequest, userFound);
		UserApp userUpdated = service.update(userFound);
		log.info("END - Update user ");
		return mapper.map(userUpdated, UserResponse.class);
	}
	
}
