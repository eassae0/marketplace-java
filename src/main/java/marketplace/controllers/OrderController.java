package marketplace.controllers;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import marketplace.dto.request.OrderCreateRequest;
import marketplace.dto.response.OrderResponse;
import marketplace.entity.Order;
import marketplace.mapper.OrderMapper;
import marketplace.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(@Valid @RequestBody OrderCreateRequest request,
                                                     @AuthenticationPrincipal UserDetails userDetails) {
        Order order = orderService.create(request, userDetails);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderResponse> getById(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        Order order = orderService.getById(id, userDetails);

        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponse>> getAll() {
        List<Order> orders = orderService.getAll();

        List<OrderResponse> responseOrders = orders.stream()
                .map(OrderMapper::toResponse)
                .toList();

        return ResponseEntity.ok(responseOrders);
    }

    @GetMapping("/user/{username}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<OrderResponse>> getAllByUsername(
            @PathVariable String username) {
        List<Order> orders = orderService.getAllByUsername(username);

        return ResponseEntity.ok(orders.stream()
                .map(OrderMapper::toResponse)
                .toList());
    }

    @GetMapping
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<List<OrderResponse>> getMyOrders(
            @AuthenticationPrincipal UserDetails userDetails) {
        List<Order> orders = orderService.getAllByUsername(userDetails.getUsername());
        return ResponseEntity.ok(orders.stream()
                .map(OrderMapper::toResponse)
                .toList());
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        orderService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/cancel")
    @PreAuthorize("hasAnyRole('ADMIN', 'USER')")
    public ResponseEntity<OrderResponse> cancel(
            @PathVariable Long id,
            @AuthenticationPrincipal UserDetails userDetails) {
        System.out.println("Cancel endpoint");
        Order order = orderService.cancel(id, userDetails);
        return ResponseEntity.ok(OrderMapper.toResponse(order));
    }

//    @PutMapping("/{id}")
//    public ResponseEntity<OrderResponse> update(@PathVariable Long id,
//                                               @Valid @RequestBody OrderUpdateRequest request) {
//        Order order = orderService.update(id, request);
//        return ResponseEntity.ok(OrderMapper.toResponse(order));
//    }


}