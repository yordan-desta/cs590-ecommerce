package edu.miu.cs590.sa.ecommercce.PaymentService.repository;

import edu.miu.cs590.sa.ecommercce.PaymentService.domain.OrderPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<OrderPayment, Long> {
}
