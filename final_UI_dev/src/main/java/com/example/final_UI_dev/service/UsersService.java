package com.example.final_UI_dev.service;

import com.example.final_UI_dev.entity.Cart;
import com.example.final_UI_dev.entity.Users;
import com.example.final_UI_dev.repository.CartRepository;
import com.example.final_UI_dev.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import java.util.List;
import org.springframework.security.crypto.password.PasswordEncoder;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private EmailService emailService;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }


   @Autowired
   private CartRepository cartRepository;
    public ResponseEntity<?> saveNewUser(Users userEntity) {
        Users existingUser = usersRepository.findByEmailIgnoreCase(userEntity.getEmail());
        if(existingUser != null)
        {
            return ResponseEntity.ok("This email already exists!");

        }
        else
        {
            userEntity.setRoles("ROLE_CUSTOMER");
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
            usersRepository.save(userEntity);
            Users user = usersRepository.findByEmailIgnoreCase(userEntity.getEmail());
            Cart cart = new Cart();
            cart.setUser(user);
            cartRepository.save(cart);

            //  ConfirmationToken confirmationToken = new ConfirmationToken(userEntity);
            //  confirmationTokenRepository.save(confirmationToken);

            SimpleMailMessage mailMessage = new SimpleMailMessage();
            mailMessage.setTo(userEntity.getEmail());
            mailMessage.setSubject("Complete Registration!");
            mailMessage.setText("To confirm your account, please click here : "
                    +"http://localhost:8080/categories");
            emailService.sendEmail(mailMessage);
        }
        return ResponseEntity.ok("Registration successful. Please check your email (" + userEntity.getEmail() + ") to confirm your account.");

    }
}
