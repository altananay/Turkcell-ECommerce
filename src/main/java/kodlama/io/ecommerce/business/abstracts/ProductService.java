package kodlama.io.ecommerce.business.abstracts;

import kodlama.io.ecommerce.business.dto.requests.create.CreateProductRequest;
import kodlama.io.ecommerce.business.dto.requests.get.GetByCategoryNameRequest;
import kodlama.io.ecommerce.business.dto.requests.update.SetProductStateRequest;
import kodlama.io.ecommerce.business.dto.requests.update.UpdateProductRequest;
import kodlama.io.ecommerce.business.dto.responses.create.CreateProductResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetAllProductsResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetProductResponse;
import kodlama.io.ecommerce.business.dto.responses.update.UpdateProductResponse;
import kodlama.io.ecommerce.entities.Product;

import java.util.List;
import java.util.UUID;

public interface ProductService {
    CreateProductResponse add(CreateProductRequest product);
    void delete(UUID id);
    UpdateProductResponse update(UUID id, UpdateProductRequest product);
    List<GetAllProductsResponse> getAll(boolean state);
    GetProductResponse getById(UUID id);
    void addCategory(UUID id, GetByCategoryNameRequest categoryName);

    void setProductState(SetProductStateRequest request);
    void setProductQuantity(UUID id, int quantity);
}
