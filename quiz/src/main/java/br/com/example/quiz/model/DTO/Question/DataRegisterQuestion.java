package br.com.example.quiz.model.DTO.Question;

import jakarta.validation.constraints.NotBlank;

public record DataRegisterQuestion(
    @NotBlank
    String description
) {
}
