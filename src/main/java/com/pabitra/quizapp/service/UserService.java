package com.pabitra.quizapp.service;

import com.pabitra.quizapp.entity.User;
import com.pabitra.quizapp.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

   private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();


   // Used to save the new user
    public ResponseEntity<String> saveNewUser(User user){
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
            return new ResponseEntity<>("Register", HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Something went wrong", HttpStatus.BAD_REQUEST);
        }
    }


    // Used to find the user based on the email id;
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }


    // Used to delete the user
    @Transactional
    public void deleteByEmail(String email) {
        if (!userRepository.existsByEmail(email)) {
            throw new RuntimeException("User not found with username: " + email);
        }
        try {
            userRepository.deleteByEmail(email);
        } catch (Exception e){
            throw new RuntimeException("Error occurred while deleting the user", e);
        }
    }


    //User to get all the user from the db
    public ResponseEntity<List<User>> getAllUser() {
        try {
            List<User> allUser = userRepository.findAll();
            return new ResponseEntity<>(allUser, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }
}
