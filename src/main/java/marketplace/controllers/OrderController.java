package marketplace.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import marketplace.dto.request.OrderCreateRequest;
import marketplace.dto.response.OrderResponse;
import marketplace.entity.Order;
import marketplace.mapper.OrderMapper;
import marketplace.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest request) {
        Order order = orderService.create(request);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(@PathVariable Long id) {
        Order order = orderService.getById(id);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAll() {
        List<Order> orders = orderService.getAll();

        List<OrderResponse> responseOrders = orders.stream()
                .map(OrderMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responseOrders);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<OrderResponse> update(@PathVariable Long id,
//                                               @Valid @RequestBody OrderUpdateRequest request) {
//        Order order = orderService.update(id, request);
//        return ResponseEntity.ok(OrderMapper.toResponse(order));
//    }


}