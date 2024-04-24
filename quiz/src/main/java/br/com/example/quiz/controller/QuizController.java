package br.com.example.quiz.controller;

import br.com.example.quiz.model.DTO.Quiz.DataQuiz;
import br.com.example.quiz.model.DTO.Quiz.DataRegisterQuiz;
import br.com.example.quiz.model.DTO.Quiz.DataUpdateQuiz;
import br.com.example.quiz.model.entity.Quiz;
import br.com.example.quiz.service.QuizService;
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
import java.util.UUID;

@RestController
@RequestMapping("quiz")
@Controller
public class QuizController {

  @Autowired
  private QuizService quizService;

  @Transactional
  @PostMapping
  public ResponseEntity<DataQuiz> registerQuiz(@RequestBody @Valid DataRegisterQuiz dataRegisterQuiz) {
    Quiz createdQuiz = quizService.registerQuiz(dataRegisterQuiz);

    URI location = ServletUriComponentsBuilder
        .fromCurrentRequest()
        .path("/{id}")
        .buildAndExpand(createdQuiz.getId())
        .toUri();

    return ResponseEntity.created(location).body(new DataQuiz(createdQuiz));
  }

  @GetMapping
  public ResponseEntity<Page<DataQuiz>> getAllQuiz(@PageableDefault(size = 10, sort = {"login"}) Pageable pageable) {
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

}
