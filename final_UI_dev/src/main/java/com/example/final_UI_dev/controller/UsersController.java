package com.example.final_UI_dev.controller;

import com.example.final_UI_dev.entity.LoginRequest;
import com.example.final_UI_dev.entity.LoginResponse;
import com.example.final_UI_dev.entity.Users;
import com.example.final_UI_dev.service.JwtService;
import com.example.final_UI_dev.service.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/users")
public class
UsersController {
    @Autowired
    private UsersService usersService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    JwtService jwtService;

    @GetMapping("/getAll")
    public ResponseEntity<?> getAll(){
        try {
            List<Users> users = usersService.getAllUsers();
            return ResponseEntity.ok(users);
        }catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    /*
    @PostMapping("/login")
    public ResponseEntity<String> authenticateAndGetToken(@RequestBody LoginRequest request) {
        try {
            System.out.println(request);
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getFirstname(), request.getPassword()));
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(request.getFirstname());
                System.out.println(token);
                return ResponseEntity.ok(token);

            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password", e);
        }
    }
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> authenticateAndGetToken(@RequestBody LoginRequest request) {
        try {
            System.out.println(request);
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getFirstname(), request.getPassword()));
            if (authentication.isAuthenticated()) {
                String token = jwtService.generateToken(request.getFirstname());
                System.out.println(token);
                LoginResponse response = new LoginResponse(token);
                return ResponseEntity.ok(response);
            } else {
                throw new UsernameNotFoundException("Invalid user request!");
            }
        } catch (AuthenticationException e) {
            throw new BadCredentialsException("Invalid username or password", e);
        }
    }


    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody Users users){
            return usersService.saveNewUser(users);
}

    @PatchMapping("/updateName/{userId}")
    public ResponseEntity<?> updateProfile(@PathVariable int userId,@RequestParam String name){
        return usersService.updateName(userId,name);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> profileUser(@PathVariable int userId){
        return usersService.getProfile(userId);
    }
}
