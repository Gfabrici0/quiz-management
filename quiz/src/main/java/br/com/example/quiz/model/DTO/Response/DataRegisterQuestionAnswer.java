package br.com.example.quiz.model.DTO.Response;

import java.util.UUID;

public record DataRegisterQuestionAnswer(
    String description,
    UUID questionId
) {
}
