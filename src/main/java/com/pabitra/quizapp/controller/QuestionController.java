package com.pabitra.quizapp.controller;

import com.pabitra.quizapp.entity.Question;
import com.pabitra.quizapp.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/question")
public class QuestionController {

    @Autowired
    private QuestionService questionService;

    // Get all the question to the database
    @GetMapping("/all-question")
    public List<Question> getAllQuestion() {
        return questionService.getAllQuestion();
    }

    // Get all the question base on the category
    @GetMapping("/category/{category}")
    public List<Question> getByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }

    // Add the question to the database
    @PostMapping("/add")
    public Question addQuestion(@Valid @RequestBody Question question) {
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
