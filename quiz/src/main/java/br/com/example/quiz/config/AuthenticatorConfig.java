package br.com.example.quiz.config;

import br.com.example.quiz.model.DTO.Token.TokenResponse;
import br.com.example.quiz.model.DTO.User.DataUser;
import br.com.example.quiz.model.DTO.User.UserCredentials;
import br.com.example.quiz.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.io.IOException;

public class AuthenticatorConfig extends UsernamePasswordAuthenticationFilter {

  private final AuthenticationManager authenticationManager;

  private final TokenManager tokenManager;

  private final ApplicationContext applicationContext;

  private final UserService userService;

  public AuthenticatorConfig(
      AuthenticationManager authenticationManager,
      TokenManager tokenManager,
      ApplicationContext applicationContext,
      UserService userService
  ) {
    this.authenticationManager = authenticationManager;
    this.tokenManager = tokenManager;
    this.applicationContext = applicationContext;
    this.userService = userService;
    super.setAuthenticationManager(authenticationManager);
  }

  @Override
  public Authentication attemptAuthentication(
      HttpServletRequest request,
      HttpServletResponse response
  ) throws AuthenticationException {
    try {
      UserCredentials credentials = new ObjectMapper().readValue(request.getInputStream(), UserCredentials.class);
      UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
          credentials.login(), credentials.password()
      );
      return authenticationManager.authenticate(authenticationToken);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }
  }

  @Override
  protected void successfulAuthentication(HttpServletRequest request,
                                          HttpServletResponse response,
                                          FilterChain chain,
                                          Authentication authResult) throws IOException {
    UserDetails userDetails = (UserDetails) authResult.getPrincipal();
    String email = userDetails.getUsername();

    DataUser userId = userService.findUserByEmail(email);

    String token = tokenManager.generateToken(email, userId.id());

    response.addHeader("Authorization", "Bearer " + token);
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    TokenResponse tokenResponse = new TokenResponse(token);

    response.getWriter().write(new ObjectMapper().writeValueAsString(tokenResponse));
    response.getWriter().flush();
  }

  @Override
  protected void unsuccessfulAuthentication(
      HttpServletRequest request,
      HttpServletResponse response,
      AuthenticationException failed
  ) throws IOException {
    response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    response.setCharacterEncoding("UTF-8");
    response.getWriter().write("Authentication Failed: " + failed.getMessage());
  }

}
