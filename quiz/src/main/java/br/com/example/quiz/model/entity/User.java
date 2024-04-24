package br.com.example.quiz.model.entity;

import br.com.example.quiz.model.DTO.User.DataRegisterUser;
import br.com.example.quiz.model.DTO.User.DataUpdateUser;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.UUID;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "\"user\"")
@EqualsAndHashCode(of = "id")
public class User implements UserDetails {

  @Id
  @GeneratedValue(generator = "UUID")
  @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", updatable = false, nullable = false)
  public UUID id;

  @Column(name = "login", unique = true, nullable = false)
  public String login;

  @Column(name = "password", nullable = false)
  public String password;

  public User(DataRegisterUser dataRegisterUser) {
    login = dataRegisterUser.login();
    password = new BCryptPasswordEncoder().encode(dataRegisterUser.password());
  }

  public void updateUser(DataUpdateUser dataUpdateUser) {
    if(dataUpdateUser.login() != null && !dataUpdateUser.login().isEmpty()){
      this.setLogin(dataUpdateUser.login());
    }
    if(dataUpdateUser.password() != null && !dataUpdateUser.password().isEmpty()){
      this.setPassword(dataUpdateUser.password());
    }
  }

  /* Overrides the getUsername method of the UserDetails class to use email for authentication */
  @Override
  public String getUsername() {
    return getLogin();
  }

  @Override
  public Collection<? extends GrantedAuthority> getAuthorities() {
    return null;
  }

  @Override
  public boolean isAccountNonExpired() {
    return true;
  }

  @Override
  public boolean isAccountNonLocked() {
    return true;
  }

  @Override
  public boolean isCredentialsNonExpired() {
    return true;
  }

  @Override
  public boolean isEnabled() {
    return true;
  }

}