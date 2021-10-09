package edu.miu.cs590.SA.Ecommerce.repository;

import edu.miu.cs590.SA.Ecommerce.domain.PaymentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PaymentTypeRepository extends JpaRepository<PaymentType,Long> {
    Optional<PaymentType> getPaymentTypeByPaymentName(String name);
}
