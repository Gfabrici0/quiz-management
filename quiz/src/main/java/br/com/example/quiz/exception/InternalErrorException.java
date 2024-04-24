package br.com.example.quiz.exception;

public class InternalErrorException extends RuntimeException {
  public InternalErrorException(String message) {
    super(message);
  }
}