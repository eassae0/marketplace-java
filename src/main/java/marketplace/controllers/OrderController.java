package marketplace.controllers;

import lombok.RequiredArgsConstructor;
import marketplace.entity.Order;
import marketplace.entity.User;
import marketplace.services.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {
    private  final OrderService orderService;

    @PostMapping
    public ResponseEntity createOrder(@RequestBody Map<Long, Long> cart) {

        User tempUser = new User();
        tempUser.setId(1L);

        Order order = orderService.createOrder(tempUser, cart);
        return ResponseEntity.ok(order);
    }
}
