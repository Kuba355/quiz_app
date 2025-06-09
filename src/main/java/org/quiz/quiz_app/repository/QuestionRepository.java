package org.quiz.quiz_app.repository;

import org.quiz.quiz_app.model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Long> {

    @Query(value = "SELECT * FROM question ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<Question> findFiveRandomQuestions();

    @Query(value = "SELECT * FROM question WHERE category = :category ORDER BY RAND() LIMIT 5", nativeQuery = true)
    List<Question> findFiveRandomQuestionsByCategory(@Param("category") String category);

    @Query("SELECT DISTINCT q.category FROM Question q")
    List<String> findDistinctCategories();

    @Query(value = "SELECT * FROM question WHERE category = :category ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Question> findRandomQuestionsByCategory(@Param("category") String category, @Param("limit") int limit);
}