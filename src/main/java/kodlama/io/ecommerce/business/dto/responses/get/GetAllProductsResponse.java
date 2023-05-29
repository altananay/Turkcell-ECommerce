package kodlama.io.ecommerce.business.dto.responses.get;

import kodlama.io.ecommerce.entities.Category;
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
public class GetAllProductsResponse {
    private UUID id;
    private UUID brandId;
    private String name;
    private int quantity;
    private double unitPrice;
    private String description;
    private String state;
}
