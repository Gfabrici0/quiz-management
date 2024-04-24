package br.com.example.quiz.model.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "quiz_answer")
public class QuizAnswer {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  public UUID id;

  @ManyToOne
  @JoinColumn(name = "quiz_id")
  public Quiz quiz;

  @ManyToOne
  @JoinColumn(name = "user_id")
  public User user;

  @Column(name = "created_at", updatable = false, nullable = false)
  public Date createdAt;
}
