package com.pabitra.quizapp.controller;

import com.pabitra.quizapp.entity.Question;
import com.pabitra.quizapp.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    // Get all the question to the database
    @GetMapping("/all-question")
    public ResponseEntity<List<Question>> getAllQuestion() {
        return questionService.getAllQuestion();
    }


    // Get all the question base on the category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<Question>> getByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }


    // Get all the question based on the difficulty level
    @GetMapping("/difficulty/{difficultyLevel}")
    public List<Question> getByDifficulty(@PathVariable String difficultyLevel) {
        return questionService.getQuestionsByDifficulty(difficultyLevel);
    }


    // Get Question based on the difficulty and category
    @GetMapping("/difficulty/{difficultyLevel}/category/{category}")
    public List<Question> getByDifficultyAndCategory(@PathVariable String difficultyLevel, @PathVariable String category){
        return questionService.getQuestionByDifficultyAndCategory(difficultyLevel, category);
    }


    // Add the question to the database
    @PostMapping("/add")
    public ResponseEntity<Question> addQuestion(@Valid @RequestBody Question question) {
        return questionService.saveQuestion(question);
    }


    // Update the question into the database
    @PutMapping("/update/{id}")
    public Question updateQuestion(@PathVariable long id, @Valid @RequestBody Question question){
        return questionService.saveUpdateQuestion(id, question);
    }


    // Delete the question to the database
    @DeleteMapping("/delete/{id}")
    public String deleteQuestion(@PathVariable long id){
        return questionService.deleteQuestion(id);
    }
}
