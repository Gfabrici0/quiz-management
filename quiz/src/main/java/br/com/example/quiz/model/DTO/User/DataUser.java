package br.com.example.quiz.model.DTO.User;

import br.com.example.quiz.model.entity.User;

import java.util.UUID;

public record DataUser(
    UUID id,
    String login
) {
  public DataUser(User user) {
    this(
        user.getId(),
        user.getLogin()
    );
  }
}
