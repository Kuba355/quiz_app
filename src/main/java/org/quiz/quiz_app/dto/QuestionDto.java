package org.quiz.quiz_app.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class QuestionDto {
    private Long id;
    private String content;
    private List<String> options;
}