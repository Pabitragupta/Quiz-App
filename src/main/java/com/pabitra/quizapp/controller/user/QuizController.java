package com.pabitra.quizapp.controller.user;


import com.pabitra.quizapp.entity.QuestionWrapper;
import com.pabitra.quizapp.entity.Response;
import com.pabitra.quizapp.service.QuizService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
public class QuizController {

    @Autowired
    private QuizService quizService;


    // used to create the quiz which contains the questions
    @PostMapping("/create")
    public ResponseEntity<?> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title){
        return quizService.createQuiz(category, numQ, title);
    }


    // Used fetched the quiz
    @GetMapping("/get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable long id){
        return quizService.getQuizQuestions(id);
    }


    // Used to send the user answer
    @PostMapping("/submit/{id}")
    public ResponseEntity<?> submitQuiz(@PathVariable long id, @RequestBody List<Response> responses){
        return quizService.calculateResult(id, responses);
    }
}
