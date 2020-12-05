package br.com.marketlist.api.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import br.com.marketlist.api.model.Category;
import br.com.marketlist.api.model.Item;
import br.com.marketlist.api.repository.ItemRepository;
import br.com.marketlist.api.service.impl.ItemServiceImpl;

@SpringBootTest(classes = ItemServiceImpl.class)
@ExtendWith(MockitoExtension.class)
class ItemServiceImplTest {

	@InjectMocks
	private ItemServiceImpl service;
	@MockBean
	private ItemRepository repository;
	@MockBean
	private UserService userService;
	private Item itemFake;
	
	@BeforeEach
	public void setup() {
		itemFake = new Item();
		itemFake.setName("Corn");
		itemFake.setId(String.valueOf(itemFake.hashCode()));
		itemFake.setCreatedAt(OffsetDateTime.now());
		itemFake.nextVersion();
		
		Category categoryFake = Category.builder().name("Canned")
				.createdAt(OffsetDateTime.now())
				.build();
		categoryFake.nextVersion();
		itemFake.setCategory(categoryFake);
	}

	@Test
	public void mustCreateItem() {
		var item = new Item();
		item.setName("Corn");
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
	public void mustDeleteItem() {
		Mockito.when(repository.save(Mockito.any(Item.class))).thenReturn(itemFake);
		Optional<Item> item = service.delete(itemFake);
		assertEquals(true, item.isPresent()?item.get().isDeleted():false);
	}
	
	@Test
	public void mustFindItemLastVersion() {
		itemFake.nextVersion();
		Mockito.when(repository.findFirstByNameOrderByVersionDesc(Mockito.anyString())).thenReturn(Optional.of(itemFake));
		List<Item> itemReturn = service.findAllByFilter("Canned",null);
		assertEquals(itemReturn.get(0).getVersion(), 2);
		
	}

}
