package com.pabitra.quizapp.service;

import com.pabitra.quizapp.entity.Question;
import com.pabitra.quizapp.repository.QuestionRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;


    // Get all question
    public ResponseEntity<List<Question>> getAllQuestion(){
        try {
            return new ResponseEntity<>(questionRepository.findAll(), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }


    // Get question based on category
    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try {
            return new ResponseEntity<>(questionRepository.findByCategory(category), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }


    // Get Question based on Difficult of that question
    public ResponseEntity<List<Question>> getQuestionsByDifficulty(String difficultyLevel) {
        try {
            return new ResponseEntity<>(questionRepository.findByDifficultyLevel(difficultyLevel), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }


    // Get Question based on the difficulty and category
    public ResponseEntity<List<Question>> getQuestionByDifficultyAndCategory(String difficultyLevel, String category){
        try {
            return new ResponseEntity<>(questionRepository.findByDifficultyLevelAndCategory(difficultyLevel, category), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }


    // Add question to the database
    public ResponseEntity<Question> saveQuestion(Question question) {
        return new ResponseEntity<>(questionRepository.save(question), HttpStatus.CREATED);
    }


    // Update Question into the database
    public ResponseEntity<?> saveUpdateQuestion(long id, Question updatedQuestion) {
        try {
            Question existingQuestion = questionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));

            existingQuestion.setQuestionTitle(updatedQuestion.getQuestionTitle());
            existingQuestion.setOption1(updatedQuestion.getOption1());
            existingQuestion.setOption2(updatedQuestion.getOption2());
            existingQuestion.setOption3(updatedQuestion.getOption3());
            existingQuestion.setOption4(updatedQuestion.getOption4());
            existingQuestion.setRightAnswer(updatedQuestion.getRightAnswer());
            existingQuestion.setDifficultyLevel(updatedQuestion.getDifficultyLevel());
            existingQuestion.setCategory(updatedQuestion.getCategory());


            return new ResponseEntity<>(questionRepository.save(existingQuestion), HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    // Delete the question to the database
    @Transactional
    public ResponseEntity<?> deleteQuestion(long id) {
        try {
            Question existingQuestion = questionRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));

            questionRepository.delete(existingQuestion);
            return new ResponseEntity<>("Question with id " + id + " has been deleted successfully.", HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
