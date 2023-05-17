package kodlama.io.ecommerce.business.concretes;

import kodlama.io.ecommerce.business.abstracts.CategoryService;
import kodlama.io.ecommerce.business.dto.requests.create.CreateCategoryRequest;
import kodlama.io.ecommerce.business.dto.requests.get.GetByCategoryNameRequest;
import kodlama.io.ecommerce.business.dto.requests.update.UpdateCategoryRequest;
import kodlama.io.ecommerce.business.dto.responses.create.CreateCategoryResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetAllCategoriesResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetCategoryResponse;
import kodlama.io.ecommerce.business.dto.responses.update.UpdateCategoryResponse;
import kodlama.io.ecommerce.business.rules.CategoryBusinessRules;
import kodlama.io.ecommerce.entities.Category;
import kodlama.io.ecommerce.repository.CategoryRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class CategoryManager implements CategoryService {

    private final CategoryRepository repository;
    private final ModelMapper mapper;
    private final CategoryBusinessRules rules;

    @Override
    public List<GetAllCategoriesResponse> getAll() {
        List<Category> categories = repository.findAll();
        List<GetAllCategoriesResponse> responses = categories.stream().map(category -> mapper.map(category, GetAllCategoriesResponse.class)).toList();
        return responses;
    }

    @Override
    public GetCategoryResponse getById(UUID id) {
        rules.checkIfCategoryExists(id);
        var category = repository.findById(id).orElseThrow();
        GetCategoryResponse response = mapper.map(category, GetCategoryResponse.class);
        return response;
    }

    @Override
    public CreateCategoryResponse add(CreateCategoryRequest request) {
        var category = mapper.map(request, Category.class);
        category.setId(UUID.randomUUID());
        var createdCategory = repository.save(category);
        CreateCategoryResponse response = mapper.map(createdCategory, CreateCategoryResponse.class);
        return response;
    }

    @Override
    public UpdateCategoryResponse update(UUID id, UpdateCategoryRequest request) {
        rules.checkIfCategoryExists(id);
        var category = mapper.map(request, Category.class);
        category.setId(id);
        repository.save(category);
        UpdateCategoryResponse response = mapper.map(category, UpdateCategoryResponse.class);
        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfCategoryExists(id);
        repository.deleteById(id);
    }

    @Override
    public GetCategoryResponse getByName(GetByCategoryNameRequest request) {
        var category = repository.getByNameIgnoreCase(request.getName());
        GetCategoryResponse response = mapper.map(category, GetCategoryResponse.class);
        return response;
    }
}