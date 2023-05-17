package kodlama.io.ecommerce.repository;

import kodlama.io.ecommerce.entities.Category;
import kodlama.io.ecommerce.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {

}
