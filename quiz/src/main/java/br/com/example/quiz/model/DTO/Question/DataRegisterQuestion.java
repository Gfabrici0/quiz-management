package br.com.example.quiz.model.DTO.Question;

import java.util.UUID;

public record DataRegisterQuestion(
    String description,
    UUID quizId
) {
}
