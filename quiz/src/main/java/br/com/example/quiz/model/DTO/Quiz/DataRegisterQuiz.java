package br.com.example.quiz.model.DTO.Quiz;

import br.com.example.quiz.model.DTO.Question.DataRegisterQuestion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record DataRegisterQuiz(
    @NotBlank
    String name,
    @NotBlank
    String description,
    @NotNull
    List<DataRegisterQuestion> question
) {
}
