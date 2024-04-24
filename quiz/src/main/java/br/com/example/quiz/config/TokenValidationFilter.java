package br.com.example.quiz.config;

import br.com.example.quiz.exception.UserNotFoundException;
import br.com.example.quiz.service.UserService;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class TokenValidationFilter extends OncePerRequestFilter {

  private final TokenManager tokenManager;

  private final UserService userService;

  public TokenValidationFilter(TokenManager tokenManager, UserService userService) {
    this.tokenManager = tokenManager;
    this.userService = userService;
  }

  @Override
  protected void doFilterInternal(
      HttpServletRequest request,
      HttpServletResponse response,
      FilterChain chain
  ) throws ServletException, IOException {
    String header = request.getHeader("Authorization");

    if (header != null && header.startsWith("Bearer ")) {
      try {
        String token = header.substring(7);
        String email = tokenManager.validateTokenAndGetEmail(token);
        boolean veriry = userService.verifyIfLoginIsValid(email);

        if (veriry && SecurityContextHolder.getContext().getAuthentication() == null) {
          UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null, null);
          SecurityContextHolder.getContext().setAuthentication(auth);
        }
      } catch (TokenExpiredException tokenExpiredException) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"error\": \"Token expired: " + tokenExpiredException.getMessage() + " on " + tokenExpiredException.getExpiredOn() + "\"}");
        return;
      } catch (JWTVerificationException jwtVerificationException) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"error\": \"Invalid token: " + jwtVerificationException.getMessage() + "\"}");
        return;
      } catch (UserNotFoundException userNotFoundException) {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.getWriter().write("{\"error\": \"Invalid token: " + userNotFoundException.getMessage() + "\"}");
      }
    }

    chain.doFilter(request, response);
  }

}
