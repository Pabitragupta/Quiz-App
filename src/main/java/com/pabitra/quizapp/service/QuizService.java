package com.pabitra.quizapp.service;

import com.pabitra.quizapp.entity.Question;
import com.pabitra.quizapp.entity.QuestionWrapper;
import com.pabitra.quizapp.entity.Quiz;
import com.pabitra.quizapp.entity.Response;
import com.pabitra.quizapp.repository.QuestionRepository;
import com.pabitra.quizapp.repository.QuizRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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


    // Used to create the quiz
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


    // Used to get the quiz
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(long id) {
        try {
            Optional<Quiz> quiz = quizRepository.findById(id);
            List<Question> questionsFromDB = quiz.get().getQuestions();
            List<QuestionWrapper> questionsFromUser = new ArrayList<>();

            for(Question q : questionsFromDB){
                QuestionWrapper qw = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(),
                        q.getOption2(), q.getOption3(), q.getOption4());

                questionsFromUser.add(qw);
            }

            return new ResponseEntity<>(questionsFromUser, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }

        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }


    // Used to calculate the result
    public ResponseEntity<?> calculateResult(long id, List<Response> responses) {
        try {
            Optional<Quiz> quiz = quizRepository.findById(id);
            List<Question> questions = quiz.get().getQuestions();
            int right = 0;
            int ind = 0;
            for(Response response : responses){
                if(response.getResponse().equals(questions.get(ind).getRightAnswer())){
                    right++;
                }
                ind++;
            }

            return new ResponseEntity<>(right, HttpStatus.OK);
        } catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
