package kodlama.io.ecommerce.business.concretes;

import kodlama.io.ecommerce.business.abstracts.ProductService;
import kodlama.io.ecommerce.business.abstracts.SaleService;
import kodlama.io.ecommerce.business.dto.requests.SaleProductRequest;
import kodlama.io.ecommerce.business.dto.requests.create.CreateSaleRequest;
import kodlama.io.ecommerce.business.dto.requests.update.UpdateSaleRequest;
import kodlama.io.ecommerce.business.dto.responses.create.CreateSaleResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetAllSalesResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetSaleResponse;
import kodlama.io.ecommerce.business.dto.responses.update.UpdateSaleResponse;
import kodlama.io.ecommerce.business.rules.ProductBusinessRules;
import kodlama.io.ecommerce.business.rules.SaleBusinessRules;
import kodlama.io.ecommerce.entities.Product;
import kodlama.io.ecommerce.entities.Sale;
import kodlama.io.ecommerce.repository.SaleRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@Service
public class SaleManager implements SaleService {

    private final SaleRepository repository;
    private final ModelMapper mapper;
    private final ProductService productService;
    private final ProductBusinessRules productBusinessRules;
    private final SaleBusinessRules saleBusinessRules;

    @Override
    public List<GetAllSalesResponse> getAll() {
        List<Sale> sales = repository.findAll();
        List<GetAllSalesResponse> responses = sales.stream().map(sale -> mapper.map(sale, GetAllSalesResponse.class)).toList();
        return responses;
    }

    @Override
    public GetSaleResponse getById(UUID id) {
        var sale = repository.findById(id).orElseThrow();
        GetSaleResponse response = mapper.map(sale, GetSaleResponse.class);
        return response;
    }

    @Override
    public CreateSaleResponse add(CreateSaleRequest request) {
        var sale = mapper.map(request, Sale.class);
        sale.setId(UUID.randomUUID());
        var products = setProducts(request.getProducts());
        sale.setProducts(products);
        sale.setSaleDate(LocalDateTime.now());
        repository.save(sale);
        CreateSaleResponse response = mapper.map(sale, CreateSaleResponse.class);
        return response;
    }

    private List<Product> setProducts(List<SaleProductRequest> requests)
    {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < requests.size(); i++)
        {
            checkProductBusinessRules(requests.get(i).getId());
            productService.setProductQuantity(requests.get(i).getId(), requests.get(i).getQuantity());
            var response = productService.getById(requests.get(i).getId());
            Product product = mapper.map(response, Product.class);
            products.add(product);

        }
        return products;
    }

    private void checkProductBusinessRules(UUID id)
    {
        productBusinessRules.checkIfProductExists(id);
        saleBusinessRules.checkOutOfStock(id);
        saleBusinessRules.checkProductState(id);
    }

    @Override
    public UpdateSaleResponse update(UUID id, UpdateSaleRequest request) {
        var sale = mapper.map(request, Sale.class);
        sale.setId(id);
        repository.save(sale);
        UpdateSaleResponse response = mapper.map(sale, UpdateSaleResponse.class);
        return response;

    }

    @Override
    public void delete(UUID id) {
        repository.deleteById(id);
    }
}
