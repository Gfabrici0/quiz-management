package br.com.example.quiz.model.DTO.Quiz;

import br.com.example.quiz.model.DTO.Question.DataRegisterQuestion;

import java.util.List;

public record DataRegisterQuiz(
    String name,
    String description,
    List<DataRegisterQuestion> question
) {
}
