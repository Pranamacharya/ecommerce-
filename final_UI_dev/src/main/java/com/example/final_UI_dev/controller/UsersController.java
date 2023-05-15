package com.example.final_UI_dev.controller;

import com.example.final_UI_dev.entity.LoginRequest;
import com.example.final_UI_dev.entity.LoginResponse;
import com.example.final_UI_dev.entity.Users;
import com.example.final_UI_dev.repository.UsersRepository;
import com.example.final_UI_dev.service.JwtService;
import com.example.final_UI_dev.service.UsersService;
import jakarta.mail.MessagingException;
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

import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Optional;

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

    @Autowired
    UsersRepository usersRepository;



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
    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestParam("email") String email,
            @RequestParam("otp") String otp,
            @RequestParam("newPassword") String newPassword) {
       Users user = usersRepository.findByEmailIgnoreCase(email);
        if (user == null) {
            // Handle user not found error
        } else {
            try {
               if(usersService.resetPassword(user, otp, newPassword))
                return ResponseEntity.ok("Password reset successful");
                else
                    return ResponseEntity.ok("failed");
            } catch (UnsupportedEncodingException | MessagingException e) {
                // Handle email sending or other errors
            }
        }

        // Handle any other errors and return an appropriate response
        return null;
    }
    @PostMapping("/send-otp")
    public void otp(@RequestParam("email") String email){
    Users user = usersRepository.findByEmailIgnoreCase(email);
        if (user == null) {
        // Handle user not found error
    } else {
        try {
            usersService.generateOneTimePassword(user);
        } catch (UnsupportedEncodingException | MessagingException e) {
            // Handle email sending or other errors
        }
    }

    // Handle any other errors and return an appropriate response
}


    @PostMapping("/add")
    public ResponseEntity<?> addUser(@RequestBody Users users){
        usersService.saveNewUser(users);
      // return ResponseEntity.ok("Signup successfull");
        return ResponseEntity.ok().body("{\"message\": \"Signup successful\"}");
    }

@PostMapping("/change")
    public ResponseEntity<?> changepassword(
        @RequestParam("email") String email,
        @RequestParam("old") String old,
        @RequestParam("nw") String nw) throws MessagingException, UnsupportedEncodingException {
       if(usersService.changePassword(email,old,nw))
          return  ResponseEntity.ok("Password changed successfully");
       else
          return ResponseEntity.ok("Either email or password is incorrect");
}
}

