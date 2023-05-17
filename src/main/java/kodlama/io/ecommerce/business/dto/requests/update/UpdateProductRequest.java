package kodlama.io.ecommerce.business.dto.requests.update;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UpdateProductRequest {
    private UUID id;
    private String name;
    private int quantity;
    private double unitPrice;
    private String description;
    private String state;
}
