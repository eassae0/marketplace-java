package marketplace.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import marketplace.dto.request.OrderCreateRequest;
import marketplace.dto.request.OrderProductRequest;
import marketplace.entity.Order;
import marketplace.entity.OrderProduct;
import marketplace.entity.Product;
import marketplace.entity.User;
import marketplace.entity.enums.OrderStatus;
import marketplace.exceptions.InsufficientProductQuantityException;
import marketplace.exceptions.OrderNotFoundException;
import marketplace.exceptions.ProductNotFoundException;
import marketplace.repositories.OrderRepository;
import marketplace.repositories.ProductRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final ProductRepository productRepository;
    private final UserService userService;

    @Transactional
    public Order create(OrderCreateRequest request) {
        Order order = new Order();
        User user = userService.getById(request.userId());
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatus.CREATED);

        BigDecimal totalPrice = BigDecimal.ZERO;

        for (OrderProductRequest orderProductRequest : request.products()) {
            Long productId = orderProductRequest.productId();
            Long productQuantity = orderProductRequest.quantity();
            Product product = productRepository.findByIdForUpdate(productId)
                    .orElseThrow(() -> new ProductNotFoundException(productId));

            if (product.getQuantity() < productQuantity) {
                throw new InsufficientProductQuantityException("Not enough product '" + product.getTitle() + "' in stock! " +
                        "Available: " + product.getQuantity());
            }

            BigDecimal purchasePrice = product.getPrice();
            totalPrice = totalPrice.add(purchasePrice.multiply(BigDecimal.valueOf(productQuantity)));

            product.setQuantity(product.getQuantity() - productQuantity);

            OrderProduct orderProduct = new OrderProduct(null, purchasePrice, order, product, productQuantity);
            order.getItems().add(orderProduct);
        }
        order.setTotalPrice(totalPrice);
        orderRepository.save(order);
        return order;
    }


    public List<Order> getAll() {
        return orderRepository.findAll();
    }

    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    @Transactional
    public void delete(Long id) {
        orderRepository.delete(getById(id));
    }

//    @Transactional
//    public Order update(Long id, OrderUpdateRequest request) {
//        Order order = getById(id);
//        order.setItems(request.products());
//
//        product.setTitle(request.title());
//        product.setPrice(request.price());
//        product.setQuantity(request.quantity());
//        product.setCategory(category);
//        return product;
//    }

}
