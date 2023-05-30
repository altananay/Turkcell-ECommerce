package kodlama.io.ecommerce.business.concretes;

import kodlama.io.ecommerce.business.abstracts.PaymentService;
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
import kodlama.io.ecommerce.common.dto.CreateProductPaymentRequest;
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
    private final PaymentService paymentService;

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
        var totalPrice = getPrice(request.getProducts());
        var products = setProducts(request.getProducts());
        sale.setProducts(products);

        CreateProductPaymentRequest paymentRequest = new CreateProductPaymentRequest();
        mapper.map(request.getPaymentRequest(), paymentRequest);
        paymentRequest.setPrice(totalPrice);
        paymentService.processSaleProductPayment(paymentRequest);

        sale.setId(UUID.randomUUID());

        sale.setPrice(totalPrice);

        sale.setSaleDate(LocalDateTime.now());

        repository.save(sale);
        setProductQuantity(request.getProducts());
        CreateSaleResponse response = mapper.map(sale, CreateSaleResponse.class);
        return response;
    }

    private double getPrice(List<SaleProductRequest> products)
    {
        double price = 0;
        for (SaleProductRequest request: products)
        {
            var product = productService.getById(request.getId());
            price += request.getQuantity() * product.getUnitPrice();
        }
        return price;
    }

    private List<Product> setProducts(List<SaleProductRequest> requests)
    {
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < requests.size(); i++)
        {
            checkProductBusinessRules(requests.get(i).getId(), requests.get(i).getQuantity());
            var response = productService.getById(requests.get(i).getId());
            Product product = mapper.map(response, Product.class);
            products.add(product);
        }
        return products;
    }

    private void setProductQuantity(List<SaleProductRequest> requests)
    {
        for (SaleProductRequest request: requests)
        {
            productService.setProductQuantity(request.getId(), request.getQuantity());
        }
    }

    private void checkProductBusinessRules(UUID id, int quantity)
    {
        productBusinessRules.checkIfProductExists(id);
        saleBusinessRules.checkQuantityForProduct(id, quantity);
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
