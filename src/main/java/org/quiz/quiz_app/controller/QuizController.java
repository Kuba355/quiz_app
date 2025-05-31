package org.quiz.quiz_app.controller;
import java.util.List;

import org.quiz.quiz_app.dto.*;

import org.quiz.quiz_app.service.QuizService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
@RequestMapping("/api/quiz")
public class QuizController {

    private final QuizService quizService;

    public QuizController(QuizService quizService) {
        this.quizService = quizService;
    }

    @GetMapping
    public List<QuestionDto> getFiveRandomQuestions() {
        return quizService.getFiveRandomQuestions();
    }

    @PostMapping("/submit")
    public QuizResultDto submitAnswers(@RequestBody QuizSubmissionDto submission) {
        return quizService.checkQuizAnswers(submission);
    }
    @GetMapping("/results")
    public List<QuizResultSummaryDto> getResults(
            @RequestParam String email,
            @RequestParam(defaultValue = "0") int page
    ) {
        return quizService.getResultsByEmail(email, page);
    }
    @GetMapping("/category")
    public List<QuestionDto> getQuestionsByCategory(@RequestParam String category) {
        return quizService.getFiveRandomQuestionsByCategory(category);
    }

    @GetMapping("/categories")
    public List<String> getAllCategories() {
        return quizService.getAllCategories();
    }
}

