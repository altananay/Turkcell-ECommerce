package kodlama.io.ecommerce.business.concretes;

import kodlama.io.ecommerce.business.abstracts.BrandService;
import kodlama.io.ecommerce.business.dto.requests.create.CreateBrandRequest;
import kodlama.io.ecommerce.business.dto.requests.update.UpdateBrandRequest;
import kodlama.io.ecommerce.business.dto.responses.create.CreateBrandResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetAllBrandsResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetBrandResponse;
import kodlama.io.ecommerce.business.dto.responses.update.UpdateBrandResponse;
import kodlama.io.ecommerce.business.rules.BrandBusinessRules;
import kodlama.io.ecommerce.entities.Brand;
import kodlama.io.ecommerce.repository.BrandRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class BrandManager implements BrandService {
    private final BrandRepository repository;
    private final ModelMapper mapper;
    private final BrandBusinessRules rules;

    @Override
    @Cacheable(value = "brand_list")
    public List<GetAllBrandsResponse> getAll() {
        List<Brand> brands = repository.findAll();
        List<GetAllBrandsResponse> response = brands.stream().map(brand -> mapper.map(brand, GetAllBrandsResponse.class)).toList();

        return response;
    }

    @Override
    public GetBrandResponse getById(UUID id) {
        rules.checkIfBrandExists(id);
        Brand brand = repository.findById(id).orElseThrow();
        GetBrandResponse response = mapper.map(brand, GetBrandResponse.class);
        return response;
    }

    @Override
    @CacheEvict(value = "brand_list", allEntries = true)
    public CreateBrandResponse add(CreateBrandRequest request) {
        rules.checkIfBrandExistsByName(request.getName());
        Brand brand = mapper.map(request, Brand.class);
        brand.setId(UUID.randomUUID());
        Brand createdBrand = repository.save(brand);
        CreateBrandResponse response = mapper.map(createdBrand, CreateBrandResponse.class);
        return response;
    }

    @Override
    public UpdateBrandResponse update(UUID id, UpdateBrandRequest request) {
        rules.checkIfBrandExists(request.getId());
        Brand brand = mapper.map(request, Brand.class);
        brand.setId(id);
        repository.save(brand);
        UpdateBrandResponse response = mapper.map(brand, UpdateBrandResponse.class);
        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfBrandExists(id);
        repository.deleteById(id);
    }
}