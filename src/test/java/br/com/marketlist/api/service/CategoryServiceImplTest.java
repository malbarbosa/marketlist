package br.com.marketlist.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.OffsetDateTime;
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
import br.com.marketlist.api.model.Category;
import br.com.marketlist.api.model.UserApp;
import br.com.marketlist.api.repository.CategoryRepository;
import br.com.marketlist.api.service.impl.CategoryServiceImpl;
import br.com.marketlist.api.service.impl.UserServiceImpl;

@SpringBootTest(classes = Application.class)
@ExtendWith(MockitoExtension.class)
class CategoryServiceImplTest {
	
	@InjectMocks
	private CategoryServiceImpl service;
	@Mock
	private CategoryRepository repository;
	@Mock
	private UserServiceImpl userServiceImpl;
	private Category categoryFake;
	private UserApp userFake;
	
	@BeforeEach
	public void setup() {
		categoryFake = new Category();
		categoryFake.setName("Canned");
		categoryFake.setId(String.valueOf(categoryFake.hashCode()));
		categoryFake.setCreatedAt(OffsetDateTime.now());
		categoryFake.nextVersion();
		userFake = new UserApp();
		userFake.setName("Teste");
		userFake.setPassword("123");
		userFake.setEmail("teste@teste.com.br");
	}

	@Test
	public void mustCreateCategory() {
		var category = new Category();
		category.setName("Canned");
		Mockito.when(userServiceImpl.getUserFromToken()).thenReturn(Optional.of(userFake));
		Mockito.when(repository.save(Mockito.any(Category.class))).thenReturn(categoryFake);
		category = service.create(category);
		assertEquals(true, (category.getCreatedAt() != null));
	}
	
	@Test
	public void mustUpdateCategory() {
		Mockito.when(userServiceImpl.getUserFromToken()).thenReturn(Optional.of(userFake));
		Mockito.when(repository.save(Mockito.any(Category.class))).thenReturn(categoryFake);
		Category category = service.update(categoryFake);
		assertEquals(true, (category.getCreatedAt() != null));
	}
	
	@Test
	public void mustFindCategoryLastVersion() {
		categoryFake.nextVersion();
		Mockito.when(repository.findFirstByNameOrderByVersionDesc(Mockito.anyString())).thenReturn(Optional.of(categoryFake));
		Optional<Category> categoryReturn = service.findLastVersionBy("Canned");
		assertEquals(categoryReturn.get().getVersion(), 2);
		
	}

}
