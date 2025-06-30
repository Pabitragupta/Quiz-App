package com.pabitra.quizapp.service;

import com.pabitra.quizapp.entity.Question;
import com.pabitra.quizapp.entity.Quiz;
import com.pabitra.quizapp.repository.QuestionRepository;
import com.pabitra.quizapp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuizService {

    @Autowired
    private QuizRepository quizRepository;

    @Autowired
    private QuestionRepository questionRepository;


    public ResponseEntity<?> createQuiz(String category, int numQ, String title) {
        try {
            List<Question> questions = questionRepository.findRandomQuestionsByCategory(category, numQ);

            Quiz quiz = new Quiz();
            quiz.setTitle(title);
            quiz.setQuestions(questions);

            quizRepository.save(quiz);

            return new ResponseEntity<>("Quiz is Created", HttpStatus.CREATED);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>("Something is wrong", HttpStatus.BAD_REQUEST);
    }
}
