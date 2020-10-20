package br.com.marketlist.api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import br.com.marketlist.api.Application;
import br.com.marketlist.api.exception.EntityExists;
import br.com.marketlist.api.exception.EntityNotFound;
import br.com.marketlist.api.model.UserApp;
import br.com.marketlist.api.repository.UserRepository;
import br.com.marketlist.api.request.UserRequest;
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

	@BeforeEach
	private void setup() {
		userFake = new UserApp();
		userFake = new UserApp();
		userFake.setName("Teste");
		userFake.setPassword("123");
		userFake.setEmail("teste@teste.com.br");
	}
	
	@Test
	void mustReturnFindAll() {
		List<UserApp> list = new ArrayList<UserApp>();
		list.add(userFake);
		Mockito.when(repository.findAll()).thenReturn(list);
		List<UserApp> findAll = controller.findAll();
		assertEquals(1, findAll.size());
		
	}
	
	@Test
	void mustReturnExceptionWhenCreateAnUser() {
		UserRequest request = new UserRequest();
		request.setEmail("teste@teste.com.br");
		request.setName("teste");
		request.setPassword("teste123");
		Mockito.when(service.findByEmail(Mockito.anyString())).thenReturn(Optional.of(userFake));
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
		Mockito.when(service.findByEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
		Mockito.when(service.create(Mockito.any(UserApp.class))).thenReturn(userFake);
		assertEquals(true, controller.create(request) != null);
	}
	
	@Test
	void mustReturnExceptionWhenUpdateAnUser() {
		UserRequest request = new UserRequest();
		request.setEmail("teste@teste.com.br");
		request.setName("teste");
		request.setPassword("teste123");
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
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
		Mockito.when(repository.findById(Mockito.anyString())).thenReturn(Optional.of(userFake));
		Mockito.when(service.update(Mockito.any(UserApp.class))).thenReturn(userFake);
		assertEquals(true, controller.update("131231231",request) != null);
	}

}
