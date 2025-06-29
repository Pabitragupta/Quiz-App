package com.pabitra.quizapp.service;

import com.pabitra.quizapp.entity.Question;
import com.pabitra.quizapp.repository.QuestionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuestionService {

    @Autowired
    private QuestionRepository questionRepository;

    // Get all question
    public List<Question> getAllQuestion(){
        return questionRepository.findAll();
    }

    // Get question base on category
    public List<Question> getQuestionsByCategory(String category) {
        return questionRepository.findByCategory(category);
    }

    // Add question to the database
    public Question saveQuestion(Question question) {
        return questionRepository.save(question);
    }

    // Update Question into the database
    public Question saveUpdateQuestion(long id, Question updatedQuestion) {

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


        return questionRepository.save(existingQuestion);
    }

    // Delete the question to the database
    public String deleteQuestion(long id) {
        Question existingQuestion = questionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Question not found with id: " + id));

        questionRepository.delete(existingQuestion);
        return "Question with id " + id + " has been deleted successfully.";
    }
}
