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
import br.com.marketlist.api.model.Item;
import br.com.marketlist.api.model.UserApp;
import br.com.marketlist.api.repository.ItemRepository;

@SpringBootTest(classes = Application.class)
@ExtendWith(MockitoExtension.class)
class ItemServiceTest {

	@InjectMocks
	private ItemService service;
	@Mock
	private ItemRepository repository;
	@Mock
	private UserService userService;
	private Item itemFake;
	
	@BeforeEach
	public void setup() {
		itemFake = new Item();
		itemFake.setName("Canned");
		itemFake.setId(String.valueOf(itemFake.hashCode()));
		itemFake.setCreatedAt(OffsetDateTime.now());
		itemFake.nextVersion();
		UserApp userFake = new UserApp();
		userFake.setName("Teste");
		userFake.setPassword("123");
		userFake.setEmail("teste@teste.com.br");
	}

	@Test
	public void mustCreateItem() {
		var item = new Item();
		item.setName("Canned");
		Mockito.when(repository.save(Mockito.any(Item.class))).thenReturn(itemFake);
		item = service.create(item);
		assertEquals(true, (item.getCreatedAt() != null));
	}
	
	@Test
	public void mustUpdateItem() {
		Mockito.when(repository.save(Mockito.any(Item.class))).thenReturn(itemFake);
		Item item = service.update(itemFake);
		assertEquals(true, (item.getCreatedAt() != null));
	}
	
	@Test
	public void mustFindCategoryLastVersion() {
		itemFake.nextVersion();
		Mockito.when(repository.findFirstByNameOrderByVersionDesc(Mockito.anyString())).thenReturn(Optional.of(itemFake));
		Optional<Item> itemReturn = service.findLastVersionBy("Canned");
		assertEquals(itemReturn.get().getVersion(), 2);
		
	}

}
