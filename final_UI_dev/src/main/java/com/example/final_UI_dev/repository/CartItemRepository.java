package com.example.final_UI_dev.repository;

import com.example.final_UI_dev.entity.Cart;
import com.example.final_UI_dev.entity.CartItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CartItemRepository extends JpaRepository<CartItem, Integer> {
    List<CartItem> findByCart(Cart cart);
}

