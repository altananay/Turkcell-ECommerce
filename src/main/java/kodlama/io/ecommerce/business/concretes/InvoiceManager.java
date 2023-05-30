package kodlama.io.ecommerce.business.concretes;

import kodlama.io.ecommerce.business.abstracts.InvoiceService;
import kodlama.io.ecommerce.business.abstracts.SaleService;
import kodlama.io.ecommerce.business.dto.requests.create.CreateInvoiceRequest;
import kodlama.io.ecommerce.business.dto.requests.update.UpdateInvoiceRequest;
import kodlama.io.ecommerce.business.dto.responses.create.CreateInvoiceResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetAllInvoicesResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetInvoiceResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetProductResponse;
import kodlama.io.ecommerce.business.dto.responses.update.UpdateInvoiceResponse;
import kodlama.io.ecommerce.business.rules.InvoiceBusinessRules;
import kodlama.io.ecommerce.entities.Invoice;
import kodlama.io.ecommerce.entities.Product;
import kodlama.io.ecommerce.repository.InvoiceRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@AllArgsConstructor(onConstructor = @__(@Lazy))
@Service
public class InvoiceManager implements InvoiceService {
    private final InvoiceRepository repository;
    private final ModelMapper mapper;
    private final InvoiceBusinessRules rules;
    @Lazy
    private final SaleService saleService;

    @Override
    public List<GetAllInvoicesResponse> getAll() {
        List<Invoice> invoices = repository.findAll();
        List<GetAllInvoicesResponse> response = invoices
                .stream()
                .map(invoice -> mapper.map(invoice, GetAllInvoicesResponse.class))
                .toList();

        for (int i = 0; i < response.size(); i++)
        {
            var products = getProducts(invoices.get(i).getSale().getId());
            response.get(i).setProducts(products);
        }

        return response;
    }

    private List<GetProductResponse> getProducts(UUID saleId)
    {
        var sale = saleService.getById(saleId);
        return sale.getProducts();
    }

    @Override
    public GetInvoiceResponse getById(UUID id) {
        rules.checkIfInvoiceExists(id);
        Invoice invoice = repository.findById(id).orElseThrow();
        var sale = saleService.getById(invoice.getSale().getId());
        List<GetProductResponse> responses = sale.getProducts();
        GetInvoiceResponse response = mapper.map(invoice, GetInvoiceResponse.class);
        response.setProducts(responses);
        return response;
    }

    @Override
    public CreateInvoiceResponse add(CreateInvoiceRequest request) {
        Invoice invoice = mapper.map(request, Invoice.class);
        invoice.setId(UUID.randomUUID());
        invoice.setCreatedAt(LocalDateTime.now());
        repository.save(invoice);
        var sale = saleService.getById(invoice.getSale().getId());
        List<GetProductResponse> responses = sale.getProducts();
        CreateInvoiceResponse response = mapper.map(invoice, CreateInvoiceResponse.class);
        response.setProducts(responses);
        return response;
    }

    @Override
    public UpdateInvoiceResponse update(UUID id, UpdateInvoiceRequest request) {
        rules.checkIfInvoiceExists(id);
        Invoice invoice = mapper.map(request, Invoice.class);
        invoice.setId(id);
        repository.save(invoice);
        var sale = saleService.getById(invoice.getSale().getId());
        List<GetProductResponse> responses = sale.getProducts();
        UpdateInvoiceResponse response = mapper.map(invoice, UpdateInvoiceResponse.class);
        response.setProducts(responses);
        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfInvoiceExists(id);
        repository.deleteById(id);
    }

}