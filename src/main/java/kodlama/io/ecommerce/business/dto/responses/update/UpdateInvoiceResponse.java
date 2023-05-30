package kodlama.io.ecommerce.business.dto.responses.update;

import kodlama.io.ecommerce.business.dto.responses.get.GetProductResponse;
import kodlama.io.ecommerce.entities.Product;
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
public class UpdateInvoiceResponse {
    private UUID id;

    private String cardHolder;
    private double salePrice;
    private String saleDate;
    private String saleDescription;
    List<GetProductResponse> products;
    private LocalDateTime createdAt;
}