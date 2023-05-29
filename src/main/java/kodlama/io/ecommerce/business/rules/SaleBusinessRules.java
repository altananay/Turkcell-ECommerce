package kodlama.io.ecommerce.business.rules;

import kodlama.io.ecommerce.business.abstracts.ProductService;
import kodlama.io.ecommerce.common.constants.Messages;
import kodlama.io.ecommerce.core.exceptions.BusinessException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class SaleBusinessRules {
    private final ProductService productService;

    public void checkOutOfStock(UUID id)
    {
        var product = productService.getById(id);
        if (product.getQuantity() < 1)
            throw new BusinessException(Messages.Product.OutOfStock);
    }

    public void checkQuantityForProduct(UUID id, int quantity)
    {
        var product = productService.getById(id);
        if (quantity > product.getQuantity())
            throw new BusinessException(Messages.Product.OutOfStock);

    }

    public void checkProductState(UUID id)
    {
        var product = productService.getById(id);
        if (!product.getState().equals("Active"))
            throw new BusinessException(Messages.Product.IsNotActive);
    }
}
