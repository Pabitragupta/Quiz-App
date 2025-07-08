package com.pabitra.quizapp.service;

import com.pabitra.quizapp.entity.Question;
import com.pabitra.quizapp.entity.User;
import com.pabitra.quizapp.response.AllQuestionsSeen;
import com.pabitra.quizapp.repository.QuestionRepository;
import com.pabitra.quizapp.repository.UserRepository;
import com.pabitra.quizapp.response.QuestionsBasedOnTheCategory;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;


    // Get all question based on the login user
    public ResponseEntity<List<AllQuestionsSeen>> getAllQuestion(){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            if(email == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            List<Question> questionsFromDB = questionRepository.findByCreatedByEmail(email);
            List<AllQuestionsSeen> questions = new ArrayList<>();


            for (Question q : questionsFromDB) {
                AllQuestionsSeen qw = new AllQuestionsSeen(
                        q.getId(),
                        q.getQuestionTitle(),
                        q.getOption1(),
                        q.getOption2(),
                        q.getOption3(),
                        q.getOption4(),
                        q.getRightAnswer(),
                        q.getDifficultyLevel(),
                        q.getCategory()
                );
                questions.add(qw);
            }

            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
    }


    // Get question based on category
    public ResponseEntity<List<QuestionsBasedOnTheCategory>> getQuestionsByCategory(String category) {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            if (email == null) {
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            // Convert email to User
            User user = userRepository.findByEmail(email);
            if (user == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
//            User user = userOpt.get();

            // Now use user instead of email
            List<Question> questionsFromDB = questionRepository.findByCategoryAndCreatedBy(category, user);
            List<QuestionsBasedOnTheCategory> questions = new ArrayList<>();

            for (Question q : questionsFromDB) {
                QuestionsBasedOnTheCategory qw = new QuestionsBasedOnTheCategory(
                        q.getId(),
                        q.getQuestionTitle(),
                        q.getOption1(),
                        q.getOption2(),
                        q.getOption3(),
                        q.getOption4(),
                        q.getRightAnswer(),
                        q.getDifficultyLevel()
                );
                questions.add(qw);
            }

            return new ResponseEntity<>(questions, HttpStatus.OK);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
        }
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
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName();

            User user = userRepository.findByEmail(email);

            if(user == null){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }

            question.setCreatedBy(user);
            questionRepository.save(question);

            return new ResponseEntity<>(question, HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
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
