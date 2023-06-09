package kodlama.io.ecommerce.business.dto.requests.create;

import kodlama.io.ecommerce.business.dto.requests.PaymentRequest;
import kodlama.io.ecommerce.business.dto.requests.SaleProductRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateSaleRequest {
    List<SaleProductRequest> products;
    private String description;
    private PaymentRequest paymentRequest;
}