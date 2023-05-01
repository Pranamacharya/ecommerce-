package com.example.final_UI_dev.service;

import com.example.final_UI_dev.entity.Users;
import com.example.final_UI_dev.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;


    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }
}
