package com.pabitra.quizapp.controller;

import com.pabitra.quizapp.entity.User;
import com.pabitra.quizapp.repository.UserRepository;
import com.pabitra.quizapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/login")
public class LoginController {

    @Autowired
    private UserService userService;



    //Used to update the user password
    @PutMapping("/update-password")
    public ResponseEntity<String> updatePassword(@RequestBody User newUser){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            User oldUser = userService.findByEmail(email);
            if(oldUser == null){
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            oldUser.setPassword(newUser.getPassword());

            userService.saveNewUser(oldUser);

            return new ResponseEntity<>("Details update", HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Something is wrong", HttpStatus.BAD_REQUEST);
        }
    }


    //used to delete the user from the database
    @DeleteMapping("/delete-user")
    public ResponseEntity<String> deleteUser(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            userService.deleteByEmail(email);

            return new ResponseEntity<>("User Successfully delete from the database", HttpStatus.OK);
        } catch (Exception e){
            return new ResponseEntity<>("Something is wrong!!", HttpStatus.BAD_REQUEST);
        }
    }
}
