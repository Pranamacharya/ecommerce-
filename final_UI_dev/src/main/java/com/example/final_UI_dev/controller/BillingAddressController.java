package com.example.final_UI_dev.controller;
import com.example.final_UI_dev.entity.BillingAddress;
import com.example.final_UI_dev.entity.ShippingAddress;
import com.example.final_UI_dev.entity.Users;
import com.example.final_UI_dev.service.BillingAddressService;
import com.example.final_UI_dev.service.ShippingAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/billing")
public class BillingAddressController {
    @Autowired
    private BillingAddressService billingAddressService;
    @PutMapping("/user/{userId}")
    public ResponseEntity<BillingAddress> updateByUser(@PathVariable int userId, @RequestBody BillingAddress newAddress) throws Exception {
        BillingAddress updatedAddress = billingAddressService.updateByUser(userId, newAddress);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }
    @PostMapping("/user/{userId}")
    public ResponseEntity<BillingAddress> save(@PathVariable int userId,@RequestBody BillingAddress shippingAddress) {
        BillingAddress savedAddress = billingAddressService.save(userId,shippingAddress);
        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }

    @GetMapping("/")
    public ResponseEntity<List<BillingAddress>> getAll() {
        List<BillingAddress> addresses = billingAddressService.getAll();
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }



    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BillingAddress>> getByUser(@PathVariable int userId) {
        Users user = new Users();
        user.setId(userId);
        List<BillingAddress> addresses = billingAddressService.getByUser(user);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable int id) {
        billingAddressService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
