package br.com.example.quiz.service;

import br.com.example.quiz.config.TokenHolder;
import br.com.example.quiz.model.DTO.Quiz.DataQuiz;
import br.com.example.quiz.model.DTO.Quiz.DataRegisterQuiz;
import br.com.example.quiz.model.DTO.Quiz.DataUpdateQuiz;
import br.com.example.quiz.model.entity.Quiz;
import br.com.example.quiz.model.entity.User;
import br.com.example.quiz.repository.QuizRepository;
import br.com.example.quiz.repository.UserRepository;
import com.auth0.jwt.JWT;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class QuizService {

  @Autowired
  private QuizRepository quizRepository;

  @Autowired
  private UserRepository userRepository;

  public Quiz registerQuiz(DataRegisterQuiz dataRegisterQuiz) {
    User user = userRepository.getUserById(UUID.fromString(JWT.decode(TokenHolder.getToken()).getClaim("id").asString()))
        .orElseThrow(() -> new RuntimeException("Quiz not found"));
    return quizRepository.save(new Quiz(dataRegisterQuiz, user));
  }

  public Page<DataQuiz> getAllQuiz(Pageable pageable) {
    return quizRepository.findAll(pageable).map(DataQuiz::new);
  }

  public DataQuiz getQuizById(UUID id) {
    return quizRepository.findById(id).map(DataQuiz::new)
        .orElseThrow(() -> new RuntimeException("Quiz not found"));
  }

  public void updateQuiz(UUID id, DataUpdateQuiz dataUpdateQuiz) {
    Quiz quiz = quizRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Quiz not found"));
    quiz.updateQuiz(dataUpdateQuiz);
    quizRepository.save(quiz);
  }

  public void deleteQuizById(UUID id) {
    Quiz quiz = quizRepository.findById(id)
        .orElseThrow(() -> new RuntimeException("Quiz not found"));

    quizRepository.deleteById(quiz.getId());
  }

}
