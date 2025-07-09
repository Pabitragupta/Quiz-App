package com.pabitra.quizapp.repository;

import com.pabitra.quizapp.entity.Question;
import com.pabitra.quizapp.entity.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuizRepository extends JpaRepository<Quiz, Long> {

    List<Quiz> findByQuestionsContaining(Question existingQuestion);
}
