package marketplace.services;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import marketplace.entity.Order;
import marketplace.entity.OrderProduct;
import marketplace.entity.Product;
import marketplace.entity.User;
import marketplace.repositories.OrderProductRepository;
import marketplace.repositories.OrderRepository;
import marketplace.repositories.ProductRepository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final OrderProductRepository orderProductRepository;

    @Transactional
    public Order createOrder(User user, Map<Long, Long> cart) {
        Order order = new Order();
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());

        BigDecimal totalPrice = BigDecimal.ZERO;

        orderRepository.save(order);

        for (Map.Entry<Long, Long> entry : cart.entrySet()) {
            Long productId = entry.getKey();
            Long productQuantity = entry.getValue();
            Product product = productRepository.findByIdForUpdate(productId)
                    .orElseThrow(() -> new RuntimeException("Product with ID: " + productId + " not found!"));

            if (product.getQuantity() < productQuantity) {
                throw new RuntimeException("Not enough product '" + product.getTitle() + "' in stock! " +
                        "Available: " + product.getQuantity() + ", you requested: " + productQuantity);
            }

            BigDecimal purchasePrice = product.getPrice();
            totalPrice = totalPrice.add(purchasePrice.multiply(BigDecimal.valueOf(productQuantity)));

            product.setQuantity(product.getQuantity() - productQuantity);

            OrderProduct orderProduct = new OrderProduct(0L, purchasePrice, order, product, productQuantity);
            order.getItems().add(orderProduct);
            orderProductRepository.save(orderProduct);
        }
        order.setTotalPrice(totalPrice);
        return order;
    }
}
