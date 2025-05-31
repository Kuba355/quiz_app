package org.quiz.quiz_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class QuizResultInnerDto {
    private int correct;
    private int total;
}