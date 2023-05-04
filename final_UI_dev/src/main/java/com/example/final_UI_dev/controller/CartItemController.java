package com.example.final_UI_dev.controller;
import com.example.final_UI_dev.entity.Cart;
import com.example.final_UI_dev.entity.CartItem;
import com.example.final_UI_dev.service.CartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/carts/{cartId}/items")
public class CartItemController {

    @Autowired
    private CartService cartService;



}
