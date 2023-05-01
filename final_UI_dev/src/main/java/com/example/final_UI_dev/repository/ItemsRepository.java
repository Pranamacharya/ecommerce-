package com.example.final_UI_dev.repository;

import com.example.final_UI_dev.entity.Items;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ItemsRepository extends JpaRepository<Items,Integer> {
}
