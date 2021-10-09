package edu.miu.cs590.sa.ecommerce.service;

import edu.miu.cs590.sa.ecommerce.domain.OrderStatus;
import edu.miu.cs590.sa.ecommerce.domain.PaymentInfo;
import edu.miu.cs590.sa.ecommerce.domain.PaymentType;
import edu.miu.cs590.sa.ecommerce.dto.OrderDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface OrderService {

    OrderDTO get(Long id);
    List<OrderDTO> getAll();
    OrderDTO save(OrderDTO order);
    OrderDTO updateStatus(Long orderId, OrderStatus status);
    OrderDTO pay(Long orderId, PaymentInfo paymentInfo);

}
