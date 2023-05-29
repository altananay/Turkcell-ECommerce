package kodlama.io.ecommerce.business.dto.requests.update;

import kodlama.io.ecommerce.business.dto.requests.create.CreateProductRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateSaleRequest {
    private UUID id;
    private double price;
    private LocalDateTime saleDate;
    List<CreateProductRequest> products;
}
