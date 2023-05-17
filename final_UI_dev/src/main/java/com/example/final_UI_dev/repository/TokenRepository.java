package com.example.final_UI_dev.repository;

import com.example.final_UI_dev.entity.Tokens;
import com.example.final_UI_dev.entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface TokenRepository extends JpaRepository<Tokens,Integer> {
    @Query("SELECT t FROM Tokens t WHERE t.user = :user")
    Tokens getTokenByUser(Users user);
}
