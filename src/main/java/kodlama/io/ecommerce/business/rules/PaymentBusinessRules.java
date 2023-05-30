package kodlama.io.ecommerce.business.rules;

import kodlama.io.ecommerce.common.constants.Messages;
import kodlama.io.ecommerce.common.dto.CreateProductPaymentRequest;
import kodlama.io.ecommerce.core.exceptions.BusinessException;
import kodlama.io.ecommerce.repository.PaymentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class PaymentBusinessRules {

    private final PaymentRepository repository;

    public void checkIfPaymentIsValid(CreateProductPaymentRequest request)
    {
        if (!repository.existsByCardNumberAndCardHolderAndCardExpirationYearAndCardExpirationMonthAndCardCvv(request.getCardNumber(), request.getCardHolder(), request.getCardExpirationYear(), request.getCardExpirationMonth(), request.getCardCvv()))
        {
            throw new BusinessException(Messages.Payment.NotAValidPayment);
        }
    }

    public void checkIfBalanceIsEnough(double balance, double price)
    {
        if (balance < price)
            throw new BusinessException(Messages.Payment.NotEnoughMoney);
    }

    public void checkIfCardExists(String cardNumber)
    {
        if (repository.existsByCardNumber(cardNumber))
            throw new BusinessException(Messages.Payment.CardNumberAlreadyExists);
    }

    public void checkIfPaymentExists(UUID id) {
        if (!repository.existsById(id))
            throw new BusinessException(Messages.Payment.NotFound);
    }
}
