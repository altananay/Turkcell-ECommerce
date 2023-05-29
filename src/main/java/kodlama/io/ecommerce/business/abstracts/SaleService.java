package kodlama.io.ecommerce.business.abstracts;

import kodlama.io.ecommerce.business.dto.requests.create.CreateSaleRequest;
import kodlama.io.ecommerce.business.dto.requests.update.UpdateSaleRequest;
import kodlama.io.ecommerce.business.dto.responses.create.CreateSaleResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetAllSalesResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetSaleResponse;
import kodlama.io.ecommerce.business.dto.responses.update.UpdateSaleResponse;

import java.util.List;
import java.util.UUID;

public interface SaleService {
    List<GetAllSalesResponse> getAll();
    GetSaleResponse getById(UUID id);
    CreateSaleResponse add(CreateSaleRequest request);
    UpdateSaleResponse update(UUID id, UpdateSaleRequest request);
    void delete(UUID id);
}
