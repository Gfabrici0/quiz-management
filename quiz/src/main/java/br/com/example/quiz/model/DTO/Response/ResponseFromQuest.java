package br.com.example.quiz.model.DTO.Response;

import br.com.example.quiz.model.DTO.Question.DataQuestion;
import br.com.example.quiz.model.entity.Question;

public record ResponseFromQuest(
    DataQuestion question,
    String questionAnswerDescription

) {
  public ResponseFromQuest(Question question) {
    this(
        new DataQuestion(question),
        question.getAnswers().getDescription()
    );
  }
}
