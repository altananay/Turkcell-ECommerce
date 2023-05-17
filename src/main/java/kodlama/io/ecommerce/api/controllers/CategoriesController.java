package kodlama.io.ecommerce.api.controllers;

import kodlama.io.ecommerce.business.abstracts.CategoryService;
import kodlama.io.ecommerce.business.abstracts.ProductService;
import kodlama.io.ecommerce.business.dto.requests.create.CreateCategoryRequest;
import kodlama.io.ecommerce.business.dto.requests.create.CreateProductRequest;
import kodlama.io.ecommerce.business.dto.requests.get.GetByCategoryNameRequest;
import kodlama.io.ecommerce.business.dto.requests.update.UpdateCategoryRequest;
import kodlama.io.ecommerce.business.dto.requests.update.UpdateProductRequest;
import kodlama.io.ecommerce.business.dto.responses.create.CreateCategoryResponse;
import kodlama.io.ecommerce.business.dto.responses.create.CreateProductResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetAllCategoriesResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetAllProductsResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetCategoryResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetProductResponse;
import kodlama.io.ecommerce.business.dto.responses.update.UpdateCategoryResponse;
import kodlama.io.ecommerce.business.dto.responses.update.UpdateProductResponse;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/categories")
@AllArgsConstructor
public class CategoriesController {
    private final CategoryService service;


    @GetMapping
    public List<GetAllCategoriesResponse> getAll()
    {
        return service.getAll();
    }

    @GetMapping("/{id}")
    public GetCategoryResponse getById(@PathVariable UUID id)
    {
        return service.getById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CreateCategoryResponse add(@RequestBody CreateCategoryRequest category)
    {
        return service.add(category);
    }

    @PutMapping("/{id}")
    public UpdateCategoryResponse update(@PathVariable UUID id, @RequestBody UpdateCategoryRequest product)
    {
        return service.update(id, product);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable UUID id)
    {
        service.delete(id);
    }

    @PostMapping("/getbyname")
    public GetCategoryResponse getByName(@RequestBody GetByCategoryNameRequest categoryName)
    {
        return service.getByName(categoryName);
    }
}
