package br.com.example.quiz.repository;

import br.com.example.quiz.model.entity.QuestionAnswer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface QuestionAnswerRepository extends JpaRepository<QuestionAnswer, UUID> {

}
