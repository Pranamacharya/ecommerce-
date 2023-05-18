package com.example.final_UI_dev.controller;

import com.example.final_UI_dev.entity.Orders;
import com.example.final_UI_dev.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/orders")
public class OrdersController {
    @Autowired
    private OrdersService ordersService;

    @PostMapping("/placeOrder/{userId}")
    public ResponseEntity<String> placeOrder(@PathVariable int userId) {
        try {
            ordersService.placeOrder(userId);
            return new ResponseEntity<>("Order placed successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*@GetMapping("/{userId}")
    public ResponseEntity<?> getOrdersByUser(@PathVariable int userId) {
        List<Map<String, Object>> orders = ordersService.getOrdersByUser(userId);
        return ResponseEntity.ok(orders);
    }*/
    @GetMapping("/{userId}")
    public ResponseEntity<?> getOrdersByUser(
            @PathVariable int userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        // Create a Pageable object for pagination
        Pageable pageable = PageRequest.of(page, size);

        // Fetch the orders using the service method
        List<Map<String, Object>> ordersPage = ordersService.getOrdersByUser(userId, pageable);

        return ResponseEntity.ok(ordersPage);
    }

    @GetMapping("/product/{productId}")
    public ResponseEntity<List<Orders>> getOrdersByProduct(@PathVariable int productId) {
        List<Orders> orders = ordersService.getOrdersByProduct(productId);
        return ResponseEntity.ok(orders);
    }

    @GetMapping("/shippingAddress/{shippingAddressId}")
    public ResponseEntity<List<Orders>> getOrdersByShippingAddress(@PathVariable int shippingAddressId) {
        List<Orders> orders = ordersService.getOrdersByShippingAddress(shippingAddressId);
        return ResponseEntity.ok(orders);
    }

    @PostMapping("/")
    public ResponseEntity<Orders> addOrder(@RequestBody Orders order) {
        Orders newOrder = ordersService.addOrder(order);
        return ResponseEntity.ok(newOrder);
    }

    @GetMapping("")
    public ResponseEntity<?> AllOrders() {
        List<Orders> newOrder = ordersService.getAllOrder();
        return ResponseEntity.ok(newOrder);
    }

}
