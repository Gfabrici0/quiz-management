package br.com.example.quiz.model.DTO.User;

import jakarta.validation.constraints.*;

public record DataRegisterUser(
    @NotBlank
    String login,
    @NotBlank
    String password
) {
}
