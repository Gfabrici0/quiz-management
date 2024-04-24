package br.com.example.quiz.service;

import br.com.example.quiz.model.DTO.User.DataRegisterUser;
import br.com.example.quiz.model.DTO.User.DataUpdateUser;
import br.com.example.quiz.model.DTO.User.DataUser;
import br.com.example.quiz.model.entity.User;
import br.com.example.quiz.exception.UserNotFoundException;
import br.com.example.quiz.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private ApplicationContext applicationContext;

  public User registerUser(DataRegisterUser dataRegisterUser) {
    return userRepository.save(new User(dataRegisterUser));
  }

  public Page<DataUser> getAllUsers(Pageable pageable) {
    return userRepository.findAll(pageable).map(DataUser::new);
  }

  public DataUser getUserById(UUID id) {
    return userRepository.findById(id).map(DataUser::new)
        .orElseThrow(() -> new UserNotFoundException("User not found"));
  }

  public void deleteUser(UUID userId) {
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new UserNotFoundException("User not found"));

    userRepository.deleteById(user.getId());
  }

  public void updateUser(UUID id, DataUpdateUser dataUpdateUser) {
    User user = userRepository.findById(id)
        .orElseThrow(() -> new UserNotFoundException("User not found"));
    user.updateUser(dataUpdateUser);
    userRepository.save(user);
  }

  public boolean verifyIfLoginIsValid(String email) {
    User user = userRepository.findByLogin(email)
        .orElseThrow(() -> new UserNotFoundException("User not found"));
    return user.getLogin() != null || !user.getLogin().isEmpty();
  }

  public DataUser findUserByEmail(String email) {
    User user = userRepository.findByLogin(email)
        .orElseThrow(() -> new UserNotFoundException("User not found"));

    return new DataUser(user);
  }
}
