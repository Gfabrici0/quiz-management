package br.com.example.quiz.model.DTO.Quiz;

import br.com.example.quiz.model.DTO.Question.DataQuestion;
import br.com.example.quiz.model.entity.Quiz;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record DataQuiz(
    UUID id,
    LocalDate createdAt,
    String name,
    String description,
    UUID creationUserId,
    List<DataQuestion> question
) {
  public DataQuiz(Quiz quiz) {
    this(
        quiz.getId(),
        quiz.getCreatedAt(),
        quiz.getName(),
        quiz.getDescription(),
        quiz.getCreationUserId().getId(),
        quiz.getQuestions().stream().map(
            DataQuestion::new
        ).collect(Collectors.toList())
    );
  }
}
