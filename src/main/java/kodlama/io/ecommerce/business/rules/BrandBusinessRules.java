package kodlama.io.ecommerce.business.rules;

import kodlama.io.ecommerce.common.constants.Messages;
import kodlama.io.ecommerce.core.exceptions.BusinessException;
import kodlama.io.ecommerce.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class BrandBusinessRules {
    private final BrandRepository repository;

    public void checkIfBrandExists(UUID id)
    {
        if (!repository.existsById(id))
            throw new BusinessException(Messages.Brand.NotExists);
    }

    public void checkIfBrandExistsByName(String name)
    {
        if (repository.existsByNameIgnoreCase(name))
        {
            throw new BusinessException(Messages.Brand.Exists);
        }
    }
}
