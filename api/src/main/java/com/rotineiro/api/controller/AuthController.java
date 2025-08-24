package com.rotineiro.api.controller;

import com.rotineiro.api.controller.dtos.user.AuthDto;
import com.rotineiro.api.controller.dtos.user.TokenDto;
import com.rotineiro.api.repository.entities.User;
import com.rotineiro.api.security.exceptions.UnauthorizedException;
import com.rotineiro.api.service.TokenService;
import com.rotineiro.api.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthenticationManager authenticationManager;
  private final TokenService tokenService;
  private final UserService userService;

  @Autowired
  public AuthController(AuthenticationManager authenticationManager, TokenService tokenService, UserService userService) {
    this.authenticationManager = authenticationManager;
    this.tokenService = tokenService;
    this.userService = userService;
  }

  @PostMapping("/login")
  public TokenDto login(@Valid @RequestBody AuthDto authDto) throws UnauthorizedException {

    User user = this.userService.userExistsAndIsValid(authDto.email(), authDto.password());

    UsernamePasswordAuthenticationToken usernamePassword =
        new UsernamePasswordAuthenticationToken(user.getUsername(), authDto.password());

    Authentication auth = authenticationManager.authenticate(usernamePassword);
    String token = tokenService.generateToken(auth.getName());

    return new TokenDto(token);
  }

}
