package marketplace.services;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import marketplace.dto.request.OrderCreateRequest;
import marketplace.dto.request.OrderProductRequest;
import marketplace.entity.Order;
import marketplace.entity.OrderProduct;
import marketplace.entity.Product;
import marketplace.entity.User;
import marketplace.entity.enums.OrderStatusType;
import marketplace.exceptions.*;
import marketplace.repositories.OrderRepository;
import marketplace.repositories.ProductRepository;
import marketplace.repositories.UserRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.userdetails.UserDetails;
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
    private final UserRepository userRepository;

    @Transactional
    public Order create(OrderCreateRequest request, UserDetails userDetails) {
        Order order = new Order();
        User user = userRepository.findByUsername(userDetails.getUsername())
                .orElseThrow(() -> new UserNotFoundException(userDetails.getUsername()));
        order.setUser(user);
        order.setCreatedAt(LocalDateTime.now());
        order.setStatus(OrderStatusType.CREATED);

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

    public List<Order> getAllByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException(username));
        return orderRepository.findAllByUser(user);
    }


    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
    }

    public Order getById(Long id, UserDetails userDetails) {
        Order order = getById(id);

        boolean isAdmin = userDetails.getAuthorities().stream()
                .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

        boolean isOwner = userDetails.getUsername().equals(order.getUser().getUsername());

        if (!isAdmin && !isOwner) throw new AccessDeniedException("You do not have access to this order");

        return order;
    }

    @Transactional
    public Order cancel(Long id, UserDetails userDetails) {
        Order order = getById(id, userDetails);
        if (order.getStatus() != OrderStatusType.CREATED
                && order.getStatus() != OrderStatusType.PAID) {
            throw new IllegalOrderStateException(order.getStatus());
        }
        order.setStatus(OrderStatusType.CANCELLED);
        for (OrderProduct orderProduct : order.getItems()) {
            Product product = productRepository.findByIdForUpdate(orderProduct.getProduct().getId())
                    .orElseThrow(() -> new ProductNotFoundException(orderProduct.getProduct().getId()));
            product.setQuantity(product.getQuantity() + orderProduct.getQuantity());
        }
        return order;
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
//    }

}
