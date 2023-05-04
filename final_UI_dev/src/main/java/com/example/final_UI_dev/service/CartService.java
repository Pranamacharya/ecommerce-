package com.example.final_UI_dev.service;

import com.example.final_UI_dev.entity.Cart;
import com.example.final_UI_dev.entity.CartItem;
import com.example.final_UI_dev.entity.Products;
import com.example.final_UI_dev.entity.Users;
import com.example.final_UI_dev.repository.CartItemRepository;
import com.example.final_UI_dev.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class  CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;



}

