package br.com.example.quiz.controller;

import br.com.example.quiz.config.TokenHolder;
import br.com.example.quiz.model.DTO.Quiz.DataQuiz;
import br.com.example.quiz.model.DTO.Quiz.DataRegisterQuiz;
import br.com.example.quiz.model.DTO.Quiz.DataUpdateQuiz;
import br.com.example.quiz.model.DTO.Response.DataRegisterQuestionAnswer;
import br.com.example.quiz.model.DTO.Response.DataResponseQuestionAnswer;
import br.com.example.quiz.model.entity.Quiz;
import br.com.example.quiz.model.entity.QuestionAnswer;
import br.com.example.quiz.service.QuizService;
import br.com.example.quiz.service.QuestionAnswerService;
import com.auth0.jwt.JWT;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("quiz")
@Controller
public class QuizController {

  @Autowired
  private QuizService quizService;

  @Autowired
  private QuestionAnswerService questionAnswerService;

  /* Quiz */

  @Transactional
  @PostMapping
  public ResponseEntity<DataQuiz> registerQuiz(@RequestBody @Valid DataRegisterQuiz dataRegisterQuiz) {
    System.out.println("UUID: " + JWT.decode(TokenHolder.getToken()).getClaim("id").asString());
    Quiz createdQuiz = quizService.registerQuiz(dataRegisterQuiz);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(createdQuiz.getId())
        .toUri();

    return ResponseEntity.created(location).body(new DataQuiz(createdQuiz));
  }

  @GetMapping
  public ResponseEntity<Page<DataQuiz>> getAllQuiz(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
    Page<DataQuiz> result = quizService.getAllQuiz(pageable);
    return ResponseEntity.ok().body(result);
  }

  @GetMapping("/{id}")
  public ResponseEntity<DataQuiz> getQuizById(@PathVariable UUID id) {
    DataQuiz result = quizService.getQuizById(id);
    return ResponseEntity.ok().body(result);
  }

  @Transactional
  @PutMapping("/{id}")
  public ResponseEntity<String> updateQuiz(@PathVariable UUID id,@RequestBody @Valid DataUpdateQuiz dataUpdateQuiz) {
    quizService.updateQuiz(id, dataUpdateQuiz);
    return ResponseEntity.noContent().build();
  }

  @Transactional
  @DeleteMapping("/{id}")
  public ResponseEntity<?> deleteQuizById(@PathVariable UUID id) {
    quizService.deleteQuizById(id);
    return ResponseEntity.noContent().build();
  }

  /* Response from Quiz */

  @Transactional
  @PostMapping("{quizId}/response")
  public ResponseEntity<List<QuestionAnswer>> registerQuestionAnswer(@PathVariable UUID quizId, @RequestBody @Valid List<DataRegisterQuestionAnswer> dataRegisterQuestionAnswer) {
    List<QuestionAnswer> createdQuestionAnswer = questionAnswerService.registerQuestionAnswer(quizId, dataRegisterQuestionAnswer);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(createdQuestionAnswer.get(0).getId())
        .toUri();

    return ResponseEntity.created(location).body(null);
  }

  @GetMapping("{quizId}/response")
  public ResponseEntity<DataResponseQuestionAnswer> getAllQuestionAnswer(@PathVariable UUID quizId, @PageableDefault(size = 10, sort = {"description"}) Pageable pageable) {
    DataResponseQuestionAnswer result = questionAnswerService.getAllQuestionAnswer(quizId, pageable);
    return ResponseEntity.ok().body(result);
  }

}
