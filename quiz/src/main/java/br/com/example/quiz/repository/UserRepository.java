package br.com.example.quiz.repository;

import br.com.example.quiz.model.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

  Optional<User> findByLogin(String email);

  Optional<User> getUserById(UUID id);
}
