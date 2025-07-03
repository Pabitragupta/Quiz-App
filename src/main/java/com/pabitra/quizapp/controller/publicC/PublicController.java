package com.pabitra.quizapp.controller.publicC;

import com.pabitra.quizapp.entity.User;
import com.pabitra.quizapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/public")
public class PublicController {

    @Autowired
    private UserService userService;


    //create the user account
    @PostMapping("/signup")
    public ResponseEntity<String> signup(@RequestBody User user){
        return userService.saveNewUser(user);
    }


    //used to see all the user
    @GetMapping("/see")
    public ResponseEntity<List<User>> getAllUser(){
        return userService.getAllUser();
    }
}
