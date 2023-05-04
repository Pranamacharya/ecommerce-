package com.example.final_UI_dev.service;

import com.example.final_UI_dev.entity.Cart;
import com.example.final_UI_dev.entity.CartItem;
import com.example.final_UI_dev.entity.Products;
import com.example.final_UI_dev.entity.Users;
import com.example.final_UI_dev.repository.CartItemRepository;
import com.example.final_UI_dev.repository.CartRepository;
import com.example.final_UI_dev.repository.ProductsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

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



    public Cart addProductToCart(int userId, int productId) {
        // Fetch the user's cart from the database, or create a new one if it doesn't exist
        Cart cart = cartRepository.findById(userId)
                .orElseGet(() -> {
            Cart newCart = new Cart();
            newCart.setUser(new Users(userId));
            return cartRepository.save(newCart);
        });

        // Fetch the product from the database
        Products product = productsRepository.findById(productId)
                .orElseThrow(null);

        // Check if the product is already in the cart
        Optional<CartItem> existingItem = cart.getItems().stream()
                .filter(item -> item.getProduct().getId().equals(productId))
                .findFirst();

        // If the product is already in the cart, increment its quantity
        if (existingItem.isPresent()) {
            CartItem item = existingItem.get();
            item.setQuantity(item.getQuantity() + 1);
            return cartRepository.save(cart);
        }

        // Otherwise, create a new cart item and add it to the cart
        CartItem newItem = new CartItem(product, 1);
        cart.getItems().add(newItem);
        return cartRepository.save(cart);
    }


    public void addProductToCart(Users user, int productId) {
        Cart cart = getCartByUser(user);
        Products product = productService.getProductById(productId).orElse(null);
        cart.getProducts().add(product);
        cartRepository.save(cart);
    }

    public void removeProductFromCart(Users user, int productId) {
        Cart cart = getCartByUserId(userId)
        Products product = productService.getProductById(productId).orElse(null);
        cart.getProducts().remove(product);
        cartRepository.save(cart);
    }

    public void clearCart(Users user) {
        Cart cart = getCartByUser(user);
        cart.getProducts().clear();
        cartRepository.save(cart);
    }

}

