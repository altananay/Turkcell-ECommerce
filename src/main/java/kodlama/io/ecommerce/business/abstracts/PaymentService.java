package kodlama.io.ecommerce.business.abstracts;

import kodlama.io.ecommerce.business.dto.requests.create.CreatePaymentRequest;
import kodlama.io.ecommerce.business.dto.requests.update.UpdatePaymentRequest;
import kodlama.io.ecommerce.business.dto.responses.create.CreatePaymentResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetAllPaymentsResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetPaymentResponse;
import kodlama.io.ecommerce.business.dto.responses.update.UpdatePaymentResponse;
import kodlama.io.ecommerce.common.dto.CreateProductPaymentRequest;

import java.util.List;
import java.util.UUID;

public interface PaymentService {
    List<GetAllPaymentsResponse> getAll();
    GetPaymentResponse getById(UUID id);
    CreatePaymentResponse add(CreatePaymentRequest request);
    UpdatePaymentResponse update(UUID id, UpdatePaymentRequest request);
    void delete(UUID id);
    void processSaleProductPayment(CreateProductPaymentRequest request);
}