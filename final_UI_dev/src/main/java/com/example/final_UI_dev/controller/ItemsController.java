package com.example.final_UI_dev.controller;

import com.example.final_UI_dev.entity.Items;
import com.example.final_UI_dev.service.ItemsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/items")
public class ItemsController {
    @Autowired
    private ItemsService itemsService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAllItems(){
        try {
            List<Items> items = itemsService.getAllItems();
            return ResponseEntity.ok(items);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
}
