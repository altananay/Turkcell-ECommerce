package kodlama.io.ecommerce.business.concretes;

import kodlama.io.ecommerce.business.abstracts.PaymentService;
import kodlama.io.ecommerce.business.abstracts.PosService;
import kodlama.io.ecommerce.business.dto.requests.create.CreatePaymentRequest;
import kodlama.io.ecommerce.business.dto.requests.update.UpdatePaymentRequest;
import kodlama.io.ecommerce.business.dto.responses.create.CreatePaymentResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetAllPaymentsResponse;
import kodlama.io.ecommerce.business.dto.responses.get.GetPaymentResponse;
import kodlama.io.ecommerce.business.dto.responses.update.UpdatePaymentResponse;
import kodlama.io.ecommerce.business.rules.PaymentBusinessRules;
import kodlama.io.ecommerce.common.dto.CreateProductPaymentRequest;
import kodlama.io.ecommerce.entities.Payment;
import kodlama.io.ecommerce.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;


@AllArgsConstructor
@Service
public class PaymentManager implements PaymentService {

    private final PaymentRepository repository;
    private final ModelMapper mapper;
    private final PosService posService;
    private final PaymentBusinessRules rules;

    @Override
    public List<GetAllPaymentsResponse> getAll() {
        List<Payment> payments = repository.findAll();
        List<GetAllPaymentsResponse> responses = payments.stream().map(payment -> mapper.map(payment, GetAllPaymentsResponse.class)).toList();
        return responses;
    }

    @Override
    public GetPaymentResponse getById(UUID id) {
        rules.checkIfPaymentExists(id);
        Payment payment = repository.findById(id).orElseThrow();
        GetPaymentResponse response = mapper.map(payment, GetPaymentResponse.class);
        return response;
    }

    @Override
    public CreatePaymentResponse add(CreatePaymentRequest request) {
        rules.checkIfCardExists(request.getCardNumber());
        Payment payment = mapper.map(request, Payment.class);
        payment.setId(UUID.randomUUID());
        repository.save(payment);
        CreatePaymentResponse response = mapper.map(payment, CreatePaymentResponse.class);
        return response;
    }

    @Override
    public UpdatePaymentResponse update(UUID id, UpdatePaymentRequest request) {
        rules.checkIfPaymentExists(id);
        Payment payment = mapper.map(request, Payment.class);
        payment.setId(id);
        repository.save(payment);
        UpdatePaymentResponse response = mapper.map(payment, UpdatePaymentResponse.class);
        return response;
    }

    @Override
    public void delete(UUID id) {
        rules.checkIfPaymentExists(id);
        repository.deleteById(id);
    }


    @Override
    public void processSaleProductPayment(CreateProductPaymentRequest request) {
        rules.checkIfPaymentIsValid(request);
        Payment payment = repository.findByCardNumber(request.getCardNumber());
        rules.checkIfBalanceIsEnough(payment.getBalance(), request.getPrice());
        posService.pay();
        payment.setBalance(payment.getBalance() - request.getPrice());
        repository.save(payment);
    }
}
