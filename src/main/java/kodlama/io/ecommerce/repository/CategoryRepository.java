package kodlama.io.ecommerce.repository;

import kodlama.io.ecommerce.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {
    Category getByNameIgnoreCase(String name);
}