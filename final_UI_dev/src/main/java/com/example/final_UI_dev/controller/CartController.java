package com.example.final_UI_dev.controller;

import com.example.final_UI_dev.entity.Cart;
import com.example.final_UI_dev.entity.Users;
import com.example.final_UI_dev.repository.CartRepository;
import com.example.final_UI_dev.repository.UsersRepository;
import com.example.final_UI_dev.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/carts")
public class CartController {

    @Autowired
    private CartService cartService;

    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private CartRepository cartRepository;

    @PostMapping("/{userId}")
    public ResponseEntity<String> createCartByUser(@PathVariable Integer userId) {
        Optional<Users> userOptional = usersRepository.findById(userId);
        if (!userOptional.isPresent()) {
            return ResponseEntity.badRequest().body("User with ID " + userId + " does not exist.");
        }
        Users user = userOptional.get();
        Cart cart = new Cart();
        cart.setUser(user);
        cartRepository.save(cart);
        return ResponseEntity.ok("Cart created successfully for user with ID " + userId);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<Cart> getCartByUserId(@PathVariable int userId) {
        Cart cart = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(cart);
    }

    @PostMapping("/{userId}/{productId}")
    public ResponseEntity<Void> addProductToCart( @PathVariable int userId, @PathVariable int productId) {
        cartService.addProductToCart(userId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}/{productId}")
    public ResponseEntity<Void> removeProductFromCart( @PathVariable int userId, @PathVariable int productId) {
        cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> clearCart( @PathVariable int userId) {
        cartService.clearCart(userId);
        return ResponseEntity.ok().build();
    }


}

