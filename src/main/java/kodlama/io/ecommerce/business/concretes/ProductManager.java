package kodlama.io.ecommerce.business.concretes;

import kodlama.io.ecommerce.business.abstracts.CategoryService;
import kodlama.io.ecommerce.business.abstracts.ProductService;
import kodlama.io.ecommerce.business.dto.requests.create.CreateProductRequest;
import kodlama.io.ecommerce.business.dto.requests.get.GetByCategoryNameRequest;
import kodlama.io.ecommerce.business.dto.requests.update.UpdateProductRequest;
import kodlama.io.ecommerce.business.dto.responses.create.CreateProductResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetAllProductsResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetProductResponse;
import kodlama.io.ecommerce.business.dto.responses.update.UpdateProductResponse;
import kodlama.io.ecommerce.business.rules.ProductBusinessRules;
import kodlama.io.ecommerce.entities.Category;
import kodlama.io.ecommerce.entities.Product;
import kodlama.io.ecommerce.entities.enums.State;
import kodlama.io.ecommerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class ProductManager implements ProductService {

    private final ProductRepository productRepository;
    private final CategoryService categoryService;
    private final ModelMapper mapper;
    private final ProductBusinessRules rules;

    @Override
    public CreateProductResponse add(CreateProductRequest request) {
        var product = mapper.map(request, Product.class);
        product.setId(UUID.randomUUID());
        product.setState(State.Active);
        var createdProduct = productRepository.save(product);
        CreateProductResponse response = mapper.map(createdProduct, CreateProductResponse.class);
        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfProductExists(id);
        productRepository.deleteById(id);
    }

    @Override
    public UpdateProductResponse update(UUID id, UpdateProductRequest request) {
        rules.checkIfProductExists(id);
        var product = mapper.map(request, Product.class);
        product.setId(id);
        productRepository.save(product);
        UpdateProductResponse response = mapper.map(product, UpdateProductResponse.class);
        return response;
    }

    @Override
    public List<GetAllProductsResponse> getAll() {
        List<Product> products = productRepository.findAll();
        List<GetAllProductsResponse> responses = products.stream().map(product -> mapper.map(product, GetAllProductsResponse.class)).toList();

        return responses;
    }

    @Override
    public GetProductResponse getById(UUID id) {
        rules.checkIfProductExists(id);
        var product = productRepository.findById(id).orElseThrow();
        GetProductResponse response = mapper.map(product, GetProductResponse.class);
        return response;
    }

    @Override
    public void addCategory(UUID id, GetByCategoryNameRequest categoryName) {
        var product = mapper.map(getById(id), Product.class);
        product.setId(id);
        List<Category> categories = new ArrayList<Category>();
        Category category = mapper.map(categoryService.getByName(categoryName), Category.class);
        categories.add(category);
        product.setCategory(categories);
        productRepository.save(product);
    }
}