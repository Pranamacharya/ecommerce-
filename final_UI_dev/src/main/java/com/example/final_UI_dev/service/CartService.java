package com.example.final_UI_dev.service;

import com.example.final_UI_dev.entity.Cart;
import com.example.final_UI_dev.entity.Products;
import com.example.final_UI_dev.entity.Users;
import com.example.final_UI_dev.repository.CartRepository;
import com.example.final_UI_dev.repository.ProductsRepository;
import com.example.final_UI_dev.repository.UsersRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Service
@Transactional
public class  CartService {

    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ProductsService productService;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ProductsRepository productsRepository;


    public List<Cart> getAllCarts() {
        return cartRepository.findAll();
    }


    /*public List<Map<String, Object>> getCartByUserId(int userId) {
        Users users = usersRepository.findById(userId).orElse(null);
        List<Cart> cartList = cartRepository.findByUser(users);
        List<Map<String, Object>> individualCart = new ArrayList<>();
        for (Cart cart : cartList) {
            if(cart.getProduct()!=null) {
                Map<String, Object> cartDetails = new HashMap<>();
                cartDetails.put("name", cart.getProduct().getName());
                cartDetails.put("Image",cart.getProduct().getImageUrl());
                cartDetails.put("price", cart.getProduct().getPrice());
                cartDetails.put("quantity", cart.getQuantity());
                cartDetails.put("totalPrice", cart.getTotalPrice());
                cartDetails.put("MaxQuantity",cart.getProduct().getStock());


                individualCart.add(cartDetails);
            }
        }
        return individualCart;
    }*/
    public List<Map<String, Object>> getCartByUserId(int userId) {
        Users users = usersRepository.findById(userId).orElse(null);
        List<Cart> cartList = cartRepository.findByUser(users);
        Map<String, Map<String, Object>> cartDetailsMap = new HashMap<>();
        for (Cart cart : cartList) {
            if (cart.getProduct() != null) {
                String productName = cart.getProduct().getName();
                if (!cartDetailsMap.containsKey(productName)) {
                    Map<String, Object> cartDetails = new HashMap<>();
                    cartDetails.put("name", productName);
                    cartDetails.put("id",cart.getProduct().getProductId());
                    cartDetails.put("image", cart.getProduct().getImageUrl());
                    cartDetails.put("price", cart.getProduct().getPrice());
                    cartDetails.put("quantity", cart.getQuantity());
                    cartDetails.put("totalPrice", cart.getTotalPrice());
                    cartDetails.put("maxQuantity", cart.getProduct().getStock());
                    cartDetailsMap.put(productName, cartDetails);
                } else {
                    Map<String, Object> cartDetails = cartDetailsMap.get(productName);
                    cartDetails.put("id",cart.getProduct().getProductId());
                    int currentQuantity = (int) cartDetails.get("quantity");
                    int currentMaxQuantity = (int) cartDetails.get("maxQuantity");
                    int newQuantity = currentQuantity + cart.getQuantity();
                    int newMaxQuantity = Math.max(currentMaxQuantity, cart.getProduct().getStock());
                    Long newTotalPrice = (Long) cartDetails.get("totalPrice") + cart.getTotalPrice();
                    cartDetails.put("quantity", newQuantity);
                    cartDetails.put("maxQuantity", newMaxQuantity);
                    cartDetails.put("totalPrice", newTotalPrice);
                }
            }
        }
        return new ArrayList<>(cartDetailsMap.values());
    }

    public Cart addProductToCart(int userId, int productId, int quantity) {
        Products product = productService.getProductById(productId).orElse(null);
        if (product == null) {
            throw new RuntimeException("Product not found");
        }

        Cart cart = new Cart();
        cart.setUser(usersRepository.findById(userId).orElse(null));
        cart.setProduct(product);
        cart.setQuantity(quantity);
        cart.setPrice(product.getPrice());
        Long totalPrice = product.getPrice() * quantity;  //total price
        cart.setTotalPrice(totalPrice);

        return cartRepository.save(cart);
    }



    public void deleteProduct(int userId, int productId) {
        Users users = usersRepository.findById(userId).orElse(null);
        List<Cart> cartList = cartRepository.findByUser(users);
        for (Cart cart : cartList) {
            if (cart.getProduct() != null && cart.getProduct().getProductId() == productId) {
                cartRepository.delete(cart);
            }
        }
    }


}












/*    @Autowired
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
}*/

