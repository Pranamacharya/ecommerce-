package com.example.final_UI_dev.service;

import com.example.final_UI_dev.entity.*;
import com.example.final_UI_dev.repository.*;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrdersService {
    @Autowired
    private OrdersRepository ordersRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ProductsRepository productsRepository;
    @Autowired
    private ShippingAddressRepository shippingAddressRepository;


    @Transactional
    public void placeOrder(int userId) {
        // Get all items in the cart for the given user_id
        List<Cart> cartList = cartRepository.findByUser(usersRepository.findById(userId).orElse(null));

        // Create an orders entry for each item in the cart
        for (Cart cart : cartList) {
            if(cart.getProduct()!=null) {
                Orders order = new Orders();
                order.setUser(usersRepository.findById(userId).orElse(null));
                //order.setUserId(userId);
                order.setProduct(productsRepository.findById(cart.getProduct().getProductId()).orElse(null));
                // order.setProductId(cart.getProduct().getId());
                order.setQuantity(cart.getQuantity());
                order.setPrice(cart.getPrice());
                order.setTotalPrice(cart.getTotalPrice());
                order.setOrderDate(LocalDateTime.now());
                order.setStatus("Processing");
                order.setShippingAddress(shippingAddressRepository.findByUserId(userId));
                ordersRepository.save(order);
            }
        }
        // Delete all items in the cart for the given user_id
        cartRepository.deleteAllByUser(usersRepository.findById(userId).orElse(null));
    }



  public List<Map<String, Object>> getOrdersByUser(int userId, Pageable pageable) {
      Users user = new Users();
      user.setId(userId);
      List<Orders> ordersList = ordersRepository.findByUser(user, pageable);
      List<Map<String, Object>> filteredOrders = new ArrayList<>();
      for (Orders order : ordersList) {
          Map<String, Object> filteredOrder = new HashMap<>();
          filteredOrder.put("orderId", order.getOrderId());
          filteredOrder.put("status", order.getStatus());
          filteredOrder.put("totalPrice", order.getTotalPrice());
          filteredOrder.put("orderDate", order.getOrderDate());
          filteredOrders.add(filteredOrder);
      }
      return filteredOrders;
  }

    public List<Map<String, Object>> getAllOrder(Pageable pageable) {
        Page<Orders> ordersList = ordersRepository.findAll(pageable);
        return ordersList.map(order -> {
            Map<String, Object> filteredOrder = new HashMap<>();
            filteredOrder.put("orderId", order.getOrderId());
            filteredOrder.put("status", order.getStatus());
            filteredOrder.put("totalPrice", order.getTotalPrice());
            filteredOrder.put("orderDate", order.getOrderDate());
            filteredOrder.put("userId",order.getUser().getId());
            return filteredOrder;
        }).getContent();
    }

/*public List<Orders> getOrdersByUser(int userId, Pageable pageable) {
    Users user = new Users();
    user.setId(userId);
    return ordersRepository.findByUser(user, pageable);
}*/



    public List<Orders> getOrdersByProduct(int productId) {
        Products product = new Products();
        product.setProductId(productId);
        return ordersRepository.findByProduct(product);
    }

    public List<Orders> getOrdersByShippingAddress(int shippingAddressId) {
        ShippingAddress shippingAddress = new ShippingAddress();
        shippingAddress.setId(shippingAddressId);
        return ordersRepository.findByShippingAddress(shippingAddress);
    }

    public Orders addOrder(Orders order) {
        return ordersRepository.save(order);
    }


    public List<Map<String, Object>> getOrdersByOrderIds(int orderId) {

        Orders ordersList = ordersRepository.findById(orderId).orElse(null);
        List<Map<String, Object>> filteredOrders = new ArrayList<>();

            Map<String, Object> filteredOrder = new HashMap<>();
            filteredOrder.put("orderId", ordersList.getOrderId());
            filteredOrder.put("status", ordersList.getStatus());
            filteredOrder.put("totalPrice", ordersList.getTotalPrice());
            filteredOrder.put("orderDate", ordersList.getOrderDate());
            filteredOrders.add(filteredOrder);

        return filteredOrders;
    }
}
