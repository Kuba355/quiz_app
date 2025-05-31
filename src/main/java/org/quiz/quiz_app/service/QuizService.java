package org.quiz.quiz_app.service;

import org.quiz.quiz_app.dto.*;
import org.quiz.quiz_app.model.Question;
import org.quiz.quiz_app.model.QuizResult;
import org.quiz.quiz_app.repository.QuestionRepository;
import org.quiz.quiz_app.repository.QuizResultRepository;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class QuizService {

    private final QuestionRepository questionRepository;
    private final QuizResultRepository quizResultRepository;

    public QuizService(QuestionRepository questionRepository, QuizResultRepository quizResultRepository) {
        this.questionRepository = questionRepository;
        this.quizResultRepository = quizResultRepository;
    }


    public List<QuestionDto> getFiveRandomQuestions() {
        return questionRepository.findFiveRandomQuestions()
                .stream()
                .map(q -> new QuestionDto(q.getId(), q.getContent(), q.getOptions()))
                .toList();
    }

    public QuizResultDto checkQuizAnswers(QuizSubmissionDto submission) {
        int correct = 0;
        int total = submission.getAnswers().size();

        for (AnswerDto answer : submission.getAnswers()) {
            Question q = questionRepository.findById(answer.getQuestionId()).orElse(null);
            if (q != null && q.getCorrectAnswerIndex() == answer.getSelectedAnswerIndex()) {
                correct++;
            }
        }

        if (submission.getEmail() != null && !submission.getEmail().isBlank()) {
            QuizResult result = QuizResult.builder()
                    .email(submission.getEmail())
                    .correct(correct)
                    .total(total)
                    .submittedAt(LocalDateTime.now())
                    .build();

            quizResultRepository.save(result);
        }
        return new QuizResultDto(new QuizResultInnerDto(correct, total));

    }


    public List<QuizResultSummaryDto> getResultsByEmail(String email, int page) {
        Pageable pageable = PageRequest.of(page, 10); // 10 wyników na stronę

        Page<QuizResult> resultsPage = quizResultRepository.findByEmailOrderBySubmittedAtDesc(email, pageable);

        return resultsPage.stream()
                .map(r -> new QuizResultSummaryDto(r.getCorrect(), r.getTotal(), r.getSubmittedAt()))
                .toList();
    }

    public List<QuestionDto> getFiveRandomQuestionsByCategory(String category) {
        return questionRepository.findFiveRandomQuestionsByCategory(category).stream()
                .map(q -> new QuestionDto(q.getId(), q.getContent(), q.getOptions()))
                .toList();
    }

    public List<String> getAllCategories() {
        return questionRepository.findDistinctCategories();
    }
}



