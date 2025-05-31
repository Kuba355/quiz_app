package org.quiz.quiz_app.dto;

import lombok.Data;
import java.util.List;

@Data
public class QuizSubmissionDto {
    private String email;
    private List<AnswerDto> answers;
}