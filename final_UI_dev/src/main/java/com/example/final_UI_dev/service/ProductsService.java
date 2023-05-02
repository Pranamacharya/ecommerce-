package com.example.final_UI_dev.service;

import com.example.final_UI_dev.entity.Products;
import com.example.final_UI_dev.repository.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProductsService {

    @Autowired
    private ProductsRepository productsRepository;

    public List<Products> getAllProducts() {
        return productsRepository.findAll();
    }

    public Optional<Products> getProductById(int productId) {
        return productsRepository.findById(productId);
    }

    public Products addProduct(Products product) {
        return productsRepository.save(product);
    }

    public void deleteProduct(int productId) {
        productsRepository.deleteById(productId);
    }

/*    public Products updateProduct(int productId, Products updatedProduct) {
        Optional<Products> existingProduct = productsRepository.findById(productId);
        if (existingProduct.isPresent()) {
            Products product = existingProduct.get();
            product.setName(updatedProduct.getName());
            product.setDescription(updatedProduct.getDescription());
            product.setPrice(updatedProduct.getPrice());
            product.setCategory(updatedProduct.getCategory());
            product.setImageUrl(updatedProduct.getImageUrl());
            product.setStock(updatedProduct.getStock());

            return productsRepository.save(product);
        }
        return null;
    }*/

}
