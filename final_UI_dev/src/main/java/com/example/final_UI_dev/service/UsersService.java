package com.example.final_UI_dev.service;

import com.example.final_UI_dev.entity.Users;
import com.example.final_UI_dev.repository.UsersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsersService {
    @Autowired
    private UsersRepository usersRepository;

    @Autowired
    private EmailService emailService;

    public List<Users> getAllUsers() {
        return usersRepository.findAll();
    }

   /* public Object saveNewUser(Users users) {
        return usersRepository.save(users);
    }*/

    public ResponseEntity<?> saveNewUser(Users userEntity) {
        Users existingUser = usersRepository.findByEmailIgnoreCase(userEntity.getEmail());
        if(existingUser != null)
        {
            return ResponseEntity.ok("This email already exists!");

        }
        else
        {
            usersRepository.save(userEntity);
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
