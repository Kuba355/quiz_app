package org.quiz.quiz_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class QuizResultSummaryDto {
    private int correct;
    private int total;
    private LocalDateTime submittedAt;
}