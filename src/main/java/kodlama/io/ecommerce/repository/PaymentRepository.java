package kodlama.io.ecommerce.repository;

import kodlama.io.ecommerce.entities.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PaymentRepository extends JpaRepository<Payment, UUID> {
    Payment findByCardNumber(String cardNumber);
    boolean existsByCardNumber(String cardNumber);
    boolean existsByCardNumberAndCardHolderAndCardExpirationYearAndCardExpirationMonthAndCardCvv(String cardNumber,
                                                                                                 String cardHolder,
                                                                                                 int cardExprationYear,
                                                                                                 int cardExprationMonth,
                                                                                                 String cardCvv);
}