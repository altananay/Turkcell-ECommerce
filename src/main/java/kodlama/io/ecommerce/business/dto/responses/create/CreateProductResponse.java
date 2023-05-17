package kodlama.io.ecommerce.business.dto.responses.create;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateProductResponse {
    private UUID id;
    private String name;
    private int quantity;
    private double unitPrice;
    private String description;
    private String state;
}