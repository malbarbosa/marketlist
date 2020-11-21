package br.com.marketlist.api.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
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
import br.com.marketlist.api.model.Category;
import br.com.marketlist.api.repository.CategoryRepository;
import br.com.marketlist.api.request.CategoryRequest;
import br.com.marketlist.api.response.CategoryResponse;
import br.com.marketlist.api.service.CategoryService;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController extends AbstractController{
	
	@Autowired
	private CategoryService service;
	
	@Autowired
	private CategoryRepository repository;
	
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping
	public Page<CategoryResponse> findAll(){
		List<Category> list = service.findAllLastVersion();
		return new PageImpl<>(list.stream().map(category -> mapper.map(category, CategoryResponse.class)).collect(Collectors.toList()));
	}
	
	@GetMapping("/{id}")
	public CategoryResponse findById(@PathVariable("id") String id) {
		log.info("BEGIN - Class=CategoryController, Method=findById, parameters= {id:"+id+"}");
		Optional<Category> categoryFound = repository.findById(id);
		if(!categoryFound.isPresent()) {
			log.info("Category not found");
			throw new EntityNotFound("Category not found!");
		}
		Category category = categoryFound.get();
		log.info("END - category: {"+ category.toString()+"}");
		return this.mapper.map(category, CategoryResponse.class);
		
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Category create!")
	public CategoryResponse create(@Validated @RequestBody CategoryRequest categoryRequest) {
		log.info("BEGIN - Class=CategoryController, Method=create, parameters= {categoryRequest:"+categoryRequest.toString()+"}");
		Optional<Category> categoryFound = service.findByName(categoryRequest.getName());
		if(categoryFound.isPresent()) {
			throw new EntityExists(String.format("Category %s already exists",categoryRequest.getName()));	
		}
		Category categoryCreated = service.create(mapper.map(categoryRequest, Category.class));
		log.info("END - categoryCreated: {"+categoryCreated.toString()+"}");
		return mapper.map(categoryCreated, CategoryResponse.class);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Category  updated!")
	public void update(@PathVariable(name = "id") String id, @Validated  @RequestBody CategoryRequest categoryRequest) {
		log.info("BEGIN - Class=CategoryController, Method=update, parameters= {id:"+id+",categoryRequest:"+categoryRequest.toString()+"}");
		Optional<Category> category = repository.findByIdAndNotDeleted(id);
		if(!category.isPresent()) {
			log.info(String.format("Category id %s not found",id));
			throw new EntityNotFound(String.format("Category id %s not found",id));
		}
		
        Category categoryFound = category.get();
		log.info("categoryFound: {"+ categoryFound.toString()+"}");
		mapper.map(categoryRequest, categoryFound);
		Category categoryUpdated = service.update(categoryFound);
		log.info("END - categoryUpdated: {"+categoryUpdated.toString()+"}");
	}
	
	@DeleteMapping("/{id}")
	@ResponseStatus(code = HttpStatus.OK, reason = "Category  deleted!")
	public void delete(@PathVariable(name = "id") String id, @Validated  @RequestBody CategoryRequest categoryRequest) {
		log.info("BEGIN - Class=CategoryController, Method=delete, parameters= {id:"+id+",categoryRequest:"+categoryRequest.toString()+"}");
		Optional<Category> category = repository.findById(id);
		if(!category.isPresent()) {
			log.info("Category not found");
			throw new EntityNotFound("Category not found!");
		}
		Category categoryFound = category.get();
		log.info("categoryFound: {"+ categoryFound.toString()+"}");
		mapper.map(categoryRequest, categoryFound);
		service.delete(categoryFound);
		log.info("END - categoryDeleted: {"+categoryFound.toString()+"}");
	}
}
