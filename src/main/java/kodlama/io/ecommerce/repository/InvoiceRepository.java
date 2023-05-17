package kodlama.io.ecommerce.repository;

import kodlama.io.ecommerce.entities.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
}