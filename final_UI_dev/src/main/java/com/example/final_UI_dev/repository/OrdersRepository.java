package com.example.final_UI_dev.repository;

import com.example.final_UI_dev.entity.Orders;
import com.example.final_UI_dev.entity.Products;
import com.example.final_UI_dev.entity.ShippingAddress;
import com.example.final_UI_dev.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrdersRepository extends JpaRepository<Orders,Integer> {
    List<Orders> findByUser(Users user);

    List<Orders> findByProduct(Products product);

    List<Orders> findByShippingAddress(ShippingAddress shippingAddress);
}
