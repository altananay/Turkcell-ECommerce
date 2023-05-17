package kodlama.io.ecommerce.business.rules;

import kodlama.io.ecommerce.common.constants.Messages;
import kodlama.io.ecommerce.core.exceptions.BusinessException;
import kodlama.io.ecommerce.repository.ProductRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class ProductBusinessRules {
    private final ProductRepository repository;

    public void checkIfProductExists(UUID id)
    {
        if (!repository.existsById(id))
            throw new BusinessException(Messages.Product.NotExists);
    }

}
