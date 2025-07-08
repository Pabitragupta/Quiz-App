package com.pabitra.quizapp.service;

import com.pabitra.quizapp.entity.*;
import com.pabitra.quizapp.entity.Question;
import com.pabitra.quizapp.response.QuestionWrapper;
import com.pabitra.quizapp.repository.QuestionRepository;
import com.pabitra.quizapp.repository.QuizRepository;
import com.pabitra.quizapp.repository.UserRepository;
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
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;

    @Autowired
    private UserRepository userRepository;


    //Used to find the valid user
    public User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByEmail(email);
        if(!userRepository.existsByEmail(email)){
            throw new RuntimeException("User not present on that email");
        }
        else {
            return user;
        }
    }


    // Used to create the quiz
    public ResponseEntity<?> createQuiz(String category, int numQ, String title) {
        try {
            User currentUser = getCurrentUser();

            List<Question> questions = questionRepository.findRandomQuestionsByCategory(category, numQ);

            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions);
            quiz.setCreatedBy(currentUser);  // ✅ Set the quiz owner

            quizRepository.save(quiz);

            return new ResponseEntity<>("Quiz is Created", HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>("Something is wrong", HttpStatus.BAD_REQUEST);
        }
    }


    // Used to get the quiz
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(long id) {
        try {
            Optional<Quiz> quizOptional = quizRepository.findById(id);
            if (quizOptional.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }

            Quiz quiz = quizOptional.get();

            // Check if the current user is the creator
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName(); // Email from JWT or login

            if (!quiz.getCreatedBy().getEmail().equals(email)) {
                return new ResponseEntity<>(HttpStatus.FORBIDDEN); // Not the owner
            }

            // Convert questions to wrappers
            List<Question> questionsFromDB = quiz.getQuestions();
            List<QuestionWrapper> questionsFromUser = new ArrayList<>();

            for (Question q : questionsFromDB) {
                QuestionWrapper qw = new QuestionWrapper(
                        q.getId(),
                        q.getQuestionTitle(),
                        q.getOption1(),
                        q.getOption2(),
                        q.getOption3(),
                        q.getOption4()
                );
                questionsFromUser.add(qw);
            }

            return new ResponseEntity<>(questionsFromUser, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }




    // Used to calculate the result
    public ResponseEntity<?> calculateResult(long id, List<Response> responses) {
        try {
            Optional<Quiz> quizOptional = quizRepository.findById(id);
            if (quizOptional.isEmpty()) {
                return new ResponseEntity<>("This quiz id is not present.", HttpStatus.NOT_FOUND);
            }

            Quiz quiz = quizOptional.get();

            // Check if the current user is the creator
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String email = authentication.getName(); // Email from JWT or login

            if (!quiz.getCreatedBy().getEmail().equals(email)) {
                return new ResponseEntity<>("You are not the wonner of this quiz", HttpStatus.FORBIDDEN); // Not the owner
            }


            List<Question> questions = quiz.getQuestions();
            int len = questions.size();
            int right = 0;
            int wrong = 0;
            int ind = 0;
            for(Response response : responses){
                if(response.getResponse().equals(questions.get(ind).getRightAnswer())){
                    right++;
                }
                else {
                    wrong++;
                }
                ind++;
            }

            return new ResponseEntity<>("Correct = " + right + " Wrong = " + wrong +
                    " Total Score " + right + " out of " + len, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
}
