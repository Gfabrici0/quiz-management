package br.com.example.quiz.model.DTO.Response;

import br.com.example.quiz.model.entity.QuestionAnswer;
import br.com.example.quiz.model.entity.Quiz;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record DataResponseQuestionAnswer(
    UUID quizId,
    List<ResponseFromQuest> question
) {
  public DataResponseQuestionAnswer(Quiz quiz) {
    this(
        quiz.getId(),
        quiz.getQuestions().stream().map(
            ResponseFromQuest::new
        ).collect(Collectors.toList())
    );
  }
}
