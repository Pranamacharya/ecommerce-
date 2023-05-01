package com.example.final_UI_dev.service;

import com.example.final_UI_dev.entity.Items;
import com.example.final_UI_dev.repository.ItemsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemsService {
    @Autowired
    private ItemsRepository itemsRepository;


    public List<Items> getAllItems() {
        return itemsRepository.findAll();
    }
}
