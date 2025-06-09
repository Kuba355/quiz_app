package org.quiz.quiz_app.service;

import org.quiz.quiz_app.dto.*;
import org.quiz.quiz_app.mapper.QuestionMapper;
import org.quiz.quiz_app.model.Question;
import org.quiz.quiz_app.model.QuizResult;
import org.quiz.quiz_app.repository.QuestionRepository;
import org.quiz.quiz_app.repository.QuizResultRepository;
import org.springframework.stereotype.Service;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;

import java.util.stream.Collectors;

import java.time.LocalDateTime;
import java.util.ArrayList;
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
    public List<QuestionDto> getRandomQuestionsByCategory(String category, int numberOfQuestions) {
        List<Question> questions = questionRepository.findRandomQuestionsByCategory(category, numberOfQuestions);
        return questions.stream()
                .map(QuestionMapper::toDto)
                .collect(Collectors.toList());
    }

    public QuizResultDto checkQuizAnswers(QuizSubmissionDto submission) {
        List<Long> correctIds = new ArrayList<>();
        List<Long> wrongIds = new ArrayList<>();

        for (AnswerDto answer : submission.getAnswers()) {
            Question q = questionRepository.findById(answer.getQuestionId()).orElse(null);
            if (q != null) {
                if (q.getCorrectAnswerIndex() == answer.getSelectedAnswerIndex()) {
                    correctIds.add(q.getId());
                } else {
                    wrongIds.add(q.getId());
                }
            }
        }

        if (submission.getEmail() != null && !submission.getEmail().isBlank()) {
            QuizResult result = QuizResult.builder()
                    .email(submission.getEmail())
                    .correct(correctIds.size())
                    .total(submission.getAnswers().size())
                    .submittedAt(LocalDateTime.now())
                    .build();

            quizResultRepository.save(result);
        }

        QuizResultInnerDto resultInnerDto = new QuizResultInnerDto(correctIds, wrongIds);
        return new QuizResultDto(resultInnerDto);
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



