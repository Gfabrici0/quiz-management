package br.com.example.quiz.model.entity;

import br.com.example.quiz.model.DTO.Question.DataRegisterQuestion;
import br.com.example.quiz.model.DTO.Quiz.DataRegisterQuiz;
import br.com.example.quiz.model.DTO.Quiz.DataUpdateQuiz;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quiz")
public class Quiz {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  public UUID id;

  @Column(name = "created_at", nullable = false)
  public LocalDate createdAt;

  @Column(name = "name", nullable = false)
  public String name;

  @Column(name = "description", nullable = false)
  public String description;

  @ManyToOne
  @JoinColumn(name = "creation_user_id")
  public User creationUserId;

  @OneToMany(mappedBy = "quiz", cascade = CascadeType.ALL, orphanRemoval = true)
  public List<Question> questions = new ArrayList<>();

  public Quiz(DataRegisterQuiz dataRegisterQuiz, User user) {
    this.createdAt = LocalDate.now();
    this.name = dataRegisterQuiz.name();
    this.description = dataRegisterQuiz.description();
    this.creationUserId = user;
    for(DataRegisterQuestion dataRegisterQuestion : dataRegisterQuiz.question()) {
      addQuestion(dataRegisterQuestion, this);
    }
  }

  public void addQuestion(DataRegisterQuestion dataRegisterQuestion, Quiz quiz) {
    Question question = new Question(dataRegisterQuestion, quiz);
    this.questions.add(question);
  }

  public void updateQuiz(DataUpdateQuiz dataUpdateQuiz) {
    if(dataUpdateQuiz.name() != null && !dataUpdateQuiz.name().isEmpty()) {
      this.name = dataUpdateQuiz.name();
    }
    if(dataUpdateQuiz.description() != null && !dataUpdateQuiz.description().isEmpty()) {
      this.description = dataUpdateQuiz.description();
    }
  }

}
