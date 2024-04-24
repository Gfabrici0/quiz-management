package br.com.example.quiz.model.entity;

import br.com.example.quiz.model.DTO.Question.DataRegisterQuestion;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "question")
public class Question {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  public UUID id;

  @Column(name = "description", nullable = false)
  public String description;

  @OneToMany
  @JoinColumn(name = "quiz_id")
  public Quiz quizId;

  public Question(DataRegisterQuestion dataRegisterQuestion, Quiz quiz) {
    this.description = dataRegisterQuestion.description();
    this.quizId = quiz;
  }

}
