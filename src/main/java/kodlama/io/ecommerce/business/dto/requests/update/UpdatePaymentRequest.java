package kodlama.io.ecommerce.business.dto.requests.update;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import kodlama.io.ecommerce.business.dto.requests.PaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentRequest extends PaymentRequest {
    @NotNull
    @Min(value = 1)
    private double balance;
}