package br.com.example.quiz.service;

import br.com.example.quiz.model.DTO.Response.DataRegisterQuestionAnswer;
import br.com.example.quiz.model.DTO.Response.DataResponseQuestionAnswer;
import br.com.example.quiz.model.entity.Question;
import br.com.example.quiz.model.entity.QuestionAnswer;
import br.com.example.quiz.model.entity.Quiz;
import br.com.example.quiz.repository.QuestionRepository;
import br.com.example.quiz.repository.QuizRepository;
import br.com.example.quiz.repository.QuestionAnswerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Service
public class QuestionAnswerService {

  @Autowired
  private QuestionAnswerRepository questionAnswerRepository;

  @Autowired
  private QuizRepository quizRepository;

  @Autowired
  private QuestionRepository questionRepository;

  public List<QuestionAnswer> registerQuestionAnswer(UUID quizId, List<DataRegisterQuestionAnswer> dataRegisterQuestionAnswers) {
    List<QuestionAnswer> savedAnswers = new ArrayList<>();

    Quiz quiz = quizRepository.findById(quizId)
        .orElseThrow(() -> new RuntimeException("Quiz not found with ID: " + quizId));

    for (DataRegisterQuestionAnswer dataAnswer : dataRegisterQuestionAnswers) {
      Question question = quiz.getQuestions().stream()
          .filter(q -> q.getId().equals(dataAnswer.questionId()))
          .findFirst()
          .orElseThrow(() -> new RuntimeException("Question not found with ID: " + dataAnswer.questionId()));

      QuestionAnswer questionAnswer = new QuestionAnswer(dataAnswer.description(), question);
      savedAnswers.add(questionAnswerRepository.save(questionAnswer));
    }

    return savedAnswers;
  }

  public DataResponseQuestionAnswer getAllQuestionAnswer(UUID quizId, Pageable pageable) {
      Quiz quiz = quizRepository.findById(quizId)
          .orElseThrow(() -> new RuntimeException("Quiz not found"));

      return new DataResponseQuestionAnswer(quiz);
  }

}
