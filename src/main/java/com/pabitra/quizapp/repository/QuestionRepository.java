package com.pabitra.quizapp.repository;

import com.pabitra.quizapp.entity.Question;
import com.pabitra.quizapp.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {
    List<Question> findByCategory(String category);
    List<Question> findByDifficultyLevel(String difficultyLevel);
    List<Question> findByDifficultyLevelAndCategory(String difficultyLevel, String category);

    @Query(value = "SELECT * FROM question WHERE category = ?1 ORDER BY RAND() LIMIT ?2", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(String category, int numQ);

    List<Question> findByCreatedByEmail(String email);

    List<Question> findByCategoryAndCreatedBy(String category, User createdBy);

    List<Question> findByDifficultyLevelAndCreatedBy(String difficultyLevel, User createdBy);
}

