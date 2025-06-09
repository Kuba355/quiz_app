package org.quiz.quiz_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuizResultInnerDto {
    private List<Long> correct;
    private List<Long> wrong;
}