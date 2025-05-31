package org.quiz.quiz_app.dto;

import lombok.Data;

@Data
public class AnswerDto {
    private Long questionId;
    private int selectedAnswerIndex;
}