package br.com.example.quiz.model.DTO.Question;

import br.com.example.quiz.model.entity.Question;

import java.util.UUID;

public record DataQuestion(
    UUID id,
    UUID quizId,
    String description
) {
  public DataQuestion(Question question) {
    this(
        question.getId(),
        question.getQuizId().getId(),
        question.getDescription()
    );
  }
}
