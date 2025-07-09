package com.pabitra.quizapp.controller.admin;

import com.pabitra.quizapp.entity.Question;
import com.pabitra.quizapp.response.AllQuestionsSeen;
import com.pabitra.quizapp.response.QuestionsBasedOnTheCategory;
import com.pabitra.quizapp.response.QuestionsBasedOnTheDifficulty;
import com.pabitra.quizapp.service.QuestionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin")
public class QuestionController {

    @Autowired
    private QuestionService questionService;


    // Get all the question based on the login user
    @GetMapping("/all-question")
    public ResponseEntity<List<AllQuestionsSeen>> getAllQuestion() {
        return questionService.getAllQuestion();
    }


    // Get all the question base on the category
    @GetMapping("/category/{category}")
    public ResponseEntity<List<QuestionsBasedOnTheCategory>> getByCategory(@PathVariable String category) {
        return questionService.getQuestionsByCategory(category);
    }


    // Get all the question based on the difficulty level
    @GetMapping("/difficulty/{difficultyLevel}")
    public ResponseEntity<List<QuestionsBasedOnTheDifficulty>> getByDifficulty(@PathVariable String difficultyLevel) {
        return questionService.getQuestionsByDifficulty(difficultyLevel);
    }


    // Get Question based on the difficulty and category
    @GetMapping("/difficulty/{difficultyLevel}/category/{category}")
    public ResponseEntity<List<Question>> getByDifficultyAndCategory(@PathVariable String difficultyLevel, @PathVariable String category){
        return questionService.getQuestionByDifficultyAndCategory(difficultyLevel, category);
    }


    // Add the question to the database
    @PostMapping("/add")
    public ResponseEntity<Question> addQuestion(@Valid @RequestBody Question question) {
        return questionService.saveQuestion(question);
    }


    // Update the question into the database
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateQuestion(@PathVariable long id, @Valid @RequestBody Question question){
        return questionService.saveUpdateQuestion(id, question);
    }


    // Delete the question to the database
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteQuestion(@PathVariable long id){
        return questionService.deleteQuestion(id);
    }
}
