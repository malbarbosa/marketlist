package br.com.markelist.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.crypto.password.PasswordEncoder;

import br.com.marketlist.api.Application;
import br.com.marketlist.api.exception.EntityNotFound;
import br.com.marketlist.api.model.UserApp;
import br.com.marketlist.api.repository.UserRepository;
import br.com.marketlist.api.service.UserService;

@SpringBootTest(classes = Application.class)
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	
	@InjectMocks
	private UserService service;
	@Mock
	private UserRepository repository;
	@MockBean
	private PasswordEncoder passwordEncoder;
	private UserApp userFake;

	@BeforeEach
	public void setup() throws Exception {
			userFake = new UserApp();
			userFake.setName("Teste");
			userFake.setPassword("123");
			userFake.setEmail("teste@teste.com.br");
	}
	
	@Test
	public void mustReturnAnUser() {
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.of(userFake));
		Optional<UserApp> userReturn = service.findByEmail("teste@teste.com.br");
		assertEquals(true, userReturn.isPresent());
	}
	
	@Test
	public void mustReturnAnException_EntityNotFound() {
		Mockito.when(repository.findByEmail(Mockito.anyString())).thenReturn(Optional.ofNullable(null));
		assertThrows(
				EntityNotFound.class,
				() -> service.findByEmail("teste@teste.com.br"));
		
	}
	
	@Test
	public void mustCreateAnUser() {
		userFake.setId(String.valueOf(userFake.hashCode()));
		Mockito.when(repository.save(Mockito.any(UserApp.class))).thenReturn(userFake);
		UserApp userApp = service.create(userFake);
		assertEquals(true, (userApp.getDateCreated() != null));
	}
	
	@Test
	public void mustUpdateAnUser() {
		userFake.setId(String.valueOf(userFake.hashCode()));
		Mockito.when(repository.save(Mockito.any(UserApp.class))).thenReturn(userFake);
		UserApp userApp = service.update(userFake);
		assertEquals(true, (userApp.getDateUpdated() != null));
	}

}
