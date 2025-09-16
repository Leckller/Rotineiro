package com.rotineiro.api.service;

import com.rotineiro.api.controller.dtos.user.CreateUserDto;
import com.rotineiro.api.repository.UserRepository;
import com.rotineiro.api.repository.entities.Routine;
import com.rotineiro.api.repository.entities.User;
import com.rotineiro.api.security.exceptions.BadRequestException;
import com.rotineiro.api.security.exceptions.NotFoundException;
import com.rotineiro.api.security.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class UserService implements UserDetailsService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  @Autowired
  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public Routine getActualRoutine(String username) {
    User user = this.findByUsername(username);
    Routine routine = user.getActiveRoutine();
    if (routine == null) return null;
    return routine;
  }

  public Integer getSequency() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    User user = this.findByUsername(username);
    return user.getStreak();
  }

  public void updateStreak(User user, LocalDate today) {
    LocalDate lastActivity = user.getLastActivity().toLocalDate();

    if (lastActivity.equals(today)) {
      // Já registrou hoje → não incrementa
      return;
    } else if (lastActivity.equals(today.minusDays(1))) {
      // Ontem teve rotina → incrementa streak
      user.setStreak(user.getStreak() + 1);
    } else {
      // Perdeu a sequência → reinicia
      user.setStreak(1);
    }

    // Atualiza melhor streak
    if (user.getStreak() > user.getBestStreak()) {
      user.setBestStreak(user.getStreak());
    }

    // Atualiza última atividade
    user.setLastActivity(today.atStartOfDay());

    this.userRepository.save(user);
  }


  public User createUser(CreateUserDto userCreationDto) throws BadRequestException {

    this.emailExists(userCreationDto.email());
    this.usernameExists(userCreationDto.username());

    String hashedPassword = new BCryptPasswordEncoder()
        .encode(userCreationDto.password());

    User user = new User();

    user.setName(userCreationDto.name());
    user.setUsername(userCreationDto.username());
    user.setEmail(userCreationDto.email());
    user.setPassword(hashedPassword);
    user.setLastActivity(LocalDate.now().atStartOfDay());

    return this.userRepository.save(user);

  }

  public void usernameExists(String username) throws BadRequestException {
    if (this.userRepository.findByUsername(username).isPresent()) {
      throw new BadRequestException("Este Username já possui cadastro!");
    }
  }

  public void emailExists(String email) throws BadRequestException {
    if (this.userRepository.findByEmail(email).isPresent()) {
      throw new BadRequestException("Este Email já possui cadastro!");
    }
  }

  public User findByUsername(String username) throws NotFoundException {
    return this.userRepository.findByUsername(username).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
  }

  public User findByEmail(String email) throws NotFoundException {
    return this.userRepository.findByEmail(email).orElseThrow(() -> new NotFoundException("Usuário não encontrado"));
  }

  public User userExistsAndIsValid(String email, String password) throws UnauthorizedException {

    User user = this.userRepository.findByEmail(email)
        .orElseThrow(() -> new UnauthorizedException("Usuário ou senha inválidos!"));

    if (!passwordEncoder.matches(password, user.getPassword())) {
      throw new UnauthorizedException("Usuário ou senha inválidos!");
    }

    return user;

  }

  /**
   * Encontra um usuário pelo nome
   */
  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
    return this.userRepository
        .findByUsername(username)
        .orElseThrow(() -> new UsernameNotFoundException(username));
  }
}