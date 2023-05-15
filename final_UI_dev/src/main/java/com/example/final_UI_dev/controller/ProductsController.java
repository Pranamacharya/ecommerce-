package com.example.final_UI_dev.controller;

import com.example.final_UI_dev.entity.Products;
import com.example.final_UI_dev.repository.ProductsRepository;
import com.example.final_UI_dev.service.ProductsService;
import com.example.final_UI_dev.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductsController {

    @Autowired
    private ProductsService productsService;

    @GetMapping
    public ResponseEntity<List<Products>> getAllProducts() {
        List<Products> products = productsService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/category/{categoriesId}")
    public ResponseEntity<List<Products>> getAllProductsByCategoriesId(@PathVariable int categoriesId) {
        List<Products> products = productsService.getAllProductsByCategories(categoriesId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
/*    @GetMapping("/{productId}")
    public ResponseEntity<Products> getProductById(@PathVariable("productId") int productId) {
        Optional<Products> product = productsService.getProductById(productId);
        if (product.isPresent()) {
            return new ResponseEntity<>(product.get(), HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }*/
@Autowired
private ProductsRepository productsRepository;
@Autowired
private ReviewService reviewService;
    @GetMapping("/{productId}")
    public ResponseEntity<Map<String, Object>> getProductById(@PathVariable int productId) {
        Map<String, Object> response = new HashMap<>();
        Optional<Products> optionalProduct = productsRepository.findById(productId);

        if (optionalProduct.isPresent()) {
            Products product = optionalProduct.get();

            response.put("productId", product.getProductId());
            response.put("name", product.getName());
            response.put("description", product.getDescription());
            response.put("price", product.getPrice());
            response.put("imageUrl", product.getImageUrl());
            response.put("stock", product.getStock());
            response.put("brand", product.getBrand().getName());
            response.put("rating", reviewService.getAverageRatingForProduct(productId));

            return ResponseEntity.ok(response);
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @PostMapping
    public ResponseEntity<Products> addProduct(@RequestBody Products product) {
        Products addedProduct = productsService.addProduct(product);
        return new ResponseEntity<>(addedProduct, HttpStatus.CREATED);
    }

    @DeleteMapping("/{productId}")
    public ResponseEntity<Void> deleteProduct(@PathVariable("productId") int productId) {
        productsService.deleteProduct(productId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @GetMapping("/search")          //search bar
    public List<Products> searchProductsByName(@RequestParam("name") String name) {
        return productsService.searchProductsByName(name);
    }
    @GetMapping("/names")       // return list of product names for filtering in search bar
    public ResponseEntity<List<String>> getAllProductNames() {
        List<String> productNames = productsService.getAllProductNames();
        return ResponseEntity.ok(productNames);
    }
}

