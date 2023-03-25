package kodlama.io.ecommerce.business.concretes;

import kodlama.io.ecommerce.business.abstracts.ProductService;
import kodlama.io.ecommerce.business.validators.ProductValidator;
import kodlama.io.ecommerce.entities.Product;
import kodlama.io.ecommerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProductManager implements ProductService {

    private final ProductRepository productRepository;

    @Override
    public Product add(Product product) {
        ProductValidator.checkIfDescriptionLengthValid(product);
        ProductValidator.checkIfQuantityValid(product);
        ProductValidator.checkIfUnitPriceValid(product);
        return productRepository.save(product);
    }

    @Override
    public void delete(int id) {
        productRepository.deleteById(id);
    }

    @Override
    public Product update(int id, Product product) {
        ProductValidator.checkIfDescriptionLengthValid(product);
        ProductValidator.checkIfQuantityValid(product);
        ProductValidator.checkIfUnitPriceValid(product);
        product.setId(id);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product getById(int id) {
        checkIfExistsById(id);
        return productRepository.findById(id).orElseThrow();
    }

    private void checkIfExistsById(int id)
    {
        if (!productRepository.existsById(id)) throw new RuntimeException("Ürün bulunamadı");
    }
}