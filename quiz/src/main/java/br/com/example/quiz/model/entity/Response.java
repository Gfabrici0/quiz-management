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
@Table(name = "response")
public class Response {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  public UUID id;

  @ManyToMany
  @JoinColumn(name = "quiz_id")
  public Quiz quizId;

  @ManyToMany
  @JoinColumn(name = "user_id")
  public User userId;

  @Column(name = "created_at", updatable = false, nullable = false)
  public Date createdAt;
}
