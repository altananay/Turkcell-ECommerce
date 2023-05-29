package kodlama.io.ecommerce.business.dto.responses.update;

import kodlama.io.ecommerce.business.dto.responses.get.GetProductResponse;
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
public class UpdateSaleResponse {
    private UUID id;
    private double price;
    private LocalDateTime saleDate;
    private List<GetProductResponse> products;
}