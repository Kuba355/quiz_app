package org.quiz.quiz_app.mapper;

import org.quiz.quiz_app.dto.QuestionDto;
import org.quiz.quiz_app.model.Question;

public class QuestionMapper {

    public static QuestionDto toDto(Question question) {
        return new QuestionDto(
                question.getId(),
                question.getContent(),
                question.getOptions()
        );
    }
}