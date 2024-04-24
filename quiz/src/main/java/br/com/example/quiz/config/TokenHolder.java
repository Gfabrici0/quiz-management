package br.com.example.quiz.config;

public class TokenHolder {

  public static final ThreadLocal<String> tokenHolder = new ThreadLocal<>();

  public static void setToken(String token) {
    tokenHolder.set(token);
  }

  public static String getToken() {
    return tokenHolder.get();
  }

  public static void clear() {
    tokenHolder.remove();
  }

}
