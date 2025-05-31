package org.quiz.quiz_app.repository;

import org.quiz.quiz_app.model.QuizResult;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuizResultRepository extends JpaRepository<QuizResult, Long> {

    Page<QuizResult> findByEmailOrderBySubmittedAtDesc(String email, Pageable pageable);}