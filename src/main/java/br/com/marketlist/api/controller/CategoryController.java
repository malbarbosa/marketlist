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
import br.com.marketlist.api.model.Category;
import br.com.marketlist.api.repository.CategoryRepository;
import br.com.marketlist.api.request.CategoryRequest;
import br.com.marketlist.api.response.CategoryResponse;
import br.com.marketlist.api.service.CategoryService;
import br.com.marketlist.api.utils.MapperUtil;

@RestController
@RequestMapping("/api/v1/categories")
public class CategoryController extends AbstractController{
	
	@Autowired
	private CategoryService service;
	
	@Autowired
	private CategoryRepository repository;
	
	@GetMapping
	public List<Category> findAll(){
		return (List<Category>) repository.findAll();
	}
	
	@GetMapping("/{id}")
	public Category findById(@RequestParam("id") String id) {
		Optional<Category> category = repository.findById(id);
		if(!category.isPresent()) {
			throw new EntityNotFound("Category not found");
		}
		return category.get();
		
	}
	
	@PostMapping
	@ResponseStatus(code = HttpStatus.CREATED, reason = "Category create!")
	public CategoryResponse create(@Validated @RequestBody CategoryRequest categoryRequest) {
		Category userCreated = null;
		Optional<Category> userApp = repository.findById(categoryRequest.getId());
		if(userApp.isPresent()) {
			throw new EntityExists();
		}else{
			userCreated = service.create(MapperUtil.map(categoryRequest, Category.class));
		}
		return MapperUtil.map(userCreated, CategoryResponse.class);
	}
	
	@PutMapping("/{id}")
	@ResponseStatus(code = HttpStatus.NO_CONTENT, reason = "Category  updated!")
	public CategoryResponse update(@PathVariable(name = "id") String id, @Validated  @RequestBody CategoryRequest categoryRequest) {
		Optional<Category> category = repository.findById(id);
		if(!category.isPresent()) {
			throw new EntityNotFound("Category not found");
		}
		Category categoryFound = category.get();
		MapperUtil.mapTo(categoryRequest, categoryFound);
		Category categoryUpdated = service.update(categoryFound);
		return MapperUtil.map(categoryUpdated, CategoryResponse.class);
	}
}
