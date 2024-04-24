package br.com.example.quiz.model.entity;

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
@Table(name = "question_answer")
public class QuestionAnswer {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  public UUID id;

  @Column(name = "description", nullable = false)
  public String description;

  @ManyToOne
  @JoinColumn(name = "question_id")
  private Question question;

  public QuestionAnswer(String description, Question question) {
    this.description = description;
    this.question = question;
  }

}
