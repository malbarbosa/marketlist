package br.com.marketlist.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.marketlist.api.Application;
import br.com.marketlist.api.exception.EntityExists;
import br.com.marketlist.api.exception.EntityNotFound;
import br.com.marketlist.api.model.UserApp;
import br.com.marketlist.api.repository.UserRepository;
import br.com.marketlist.api.request.UserRequest;
import br.com.marketlist.api.response.UserResponse;
import br.com.marketlist.api.service.impl.UserServiceImpl;

@SpringBootTest(classes = Application.class)
@ExtendWith(MockitoExtension.class)
class UserControllerTest {
	
	@Mock
	private UserRepository repository;
	@Mock
	private UserServiceImpl service;
	@InjectMocks
	private UserController controller;
	private UserApp userFake;
	@MockBean
	private ModelMapper mapper;

	@BeforeEach
	private void setup() {
		userFake = UserApp.builder()
				.name("Teste")
				.password("123")
				.email("teste@teste.com.br").build();
	}
	
	@Test
	void mustReturnFindAll() {
		List<UserApp> list = new ArrayList<UserApp>();
		list.add(userFake);
		when(repository.findAll()).thenReturn(list);
		List<UserApp> findAll = controller.findAll();
		assertEquals(1, findAll.size());
		
	}
	
	@Test
	void mustReturnExceptionWhenCreateAnUser() {
		UserRequest request = new UserRequest();
		request.setEmail("teste@teste.com.br");
		request.setName("teste");
		request.setPassword("teste123");
		when(service.findByEmail(anyString())).thenReturn(Optional.of(userFake));
		assertThrows(
				EntityExists.class,
				() -> controller.create(request));
	}
	
	@Test
	void mustReturnCreateAnUser() {
		UserRequest request = new UserRequest();
		request.setEmail("teste@teste.com.br");
		request.setName("teste");
		request.setPassword("teste123");
		when(service.findByEmail(anyString())).thenReturn(Optional.ofNullable(null));
		
		UserApp userBuild = doWhenToUserApp();
		when(service.create(any(UserApp.class)))
				.thenReturn(userBuild);
		assertEquals(true, controller.create(request) != null);
	}

	private UserApp doWhenToUserApp() {
		UserApp userBuild = UserApp
				.builder().email("teste@teste.com.br").name("teste").password("teste123").build();
		when(mapper.map(any(UserRequest.class), any())).thenReturn(userBuild);
		when(mapper.map(any(UserApp.class), any())).thenReturn(new UserResponse());
		return userBuild;
	}
	
	@Test
	void mustReturnExceptionWhenUpdateAnUser() {
		UserRequest request = new UserRequest();
		request.setEmail("teste@teste.com.br");
		request.setName("teste");
		request.setPassword("teste123");
		when(repository.findById(anyString())).thenReturn(Optional.ofNullable(null));
		assertThrows(
				EntityNotFound.class,
				() -> controller.update("1231231231313", request));
	}
	
	@Test
	void mustReturnUpdateAnUser() {
		UserRequest request = new UserRequest();
		request.setEmail("teste@teste.com.br");
		request.setName("teste");
		request.setPassword("teste123");
		UserApp userBuild = doWhenToUserApp();
		when(repository.findById(anyString())).thenReturn(Optional.of(userFake));
		when(service.update(any(UserApp.class))).thenReturn(userBuild);
		assertEquals(true, controller.update("131231231",request) != null);
	}

}
