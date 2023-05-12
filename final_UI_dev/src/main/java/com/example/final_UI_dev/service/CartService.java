package com.example.final_UI_dev.service;

import com.example.final_UI_dev.entity.Cart;
import com.example.final_UI_dev.entity.Products;
import com.example.final_UI_dev.repository.CartRepository;
import com.example.final_UI_dev.repository.ProductsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class  CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private  ProductsService productService;

    @Autowired
    private ProductsRepository productsRepository;

    public Cart getCartByUserId(int userId) {
        return cartRepository.findById(userId)
                .orElseThrow(null);
    }

    public void addProductToCart(int userId, int productId) {
        Cart cart = getCartByUserId(userId);
        Products product = productService.getProductById(productId).orElse(null);
        cart.getProducts().add(product);
        cartRepository.save(cart);
    }

    public void removeProductFromCart(int userId ,int productId) {
        Cart cart = getCartByUserId(userId);
        Products product = productService.getProductById(productId).orElse(null);
        cart.getProducts().remove(product);
        cartRepository.save(cart);
    }

    public void clearCart(int userId) {
        Cart cart = getCartByUserId(userId);
        cart.getProducts().clear();
        cartRepository.save(cart);
    }

}

