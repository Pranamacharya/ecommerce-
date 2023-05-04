package com.example.final_UI_dev.controller;

import com.example.final_UI_dev.entity.Cart;
import com.example.final_UI_dev.entity.CartItem;
import com.example.final_UI_dev.entity.Users;
import com.example.final_UI_dev.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;


    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable int userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }
    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<Cart> addProductToCart(@PathVariable int userId, @PathVariable int productId) {
        Cart cart = cartService.addProductToCart(userId, productId);
        return ResponseEntity.ok(cart);
    }


    @PostMapping("/{productId}")
    public ResponseEntity<Void> addProductToCart(@AuthenticationPrincipal User user, @PathVariable int productId) {
        cartService.addProductToCart(user, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> removeProductFromCart(@AuthenticationPrincipal User user, @PathVariable int productId) {
        cartService.removeProductFromCart(user, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping
    public ResponseEntity<Void> clearCart(@AuthenticationPrincipal User user) {
        cartService.clearCart(user);
        return ResponseEntity.ok().build();
    }


}

