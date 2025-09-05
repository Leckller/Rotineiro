package com.rotineiro.api.controller;

import com.rotineiro.api.controller.dtos.Routine.RoutineDto;
import com.rotineiro.api.controller.dtos.user.CreateUserDto;
import com.rotineiro.api.controller.dtos.user.TokenDto;
import com.rotineiro.api.repository.entities.Routine;
import com.rotineiro.api.repository.entities.User;
import com.rotineiro.api.security.SecurityConfig;
import com.rotineiro.api.security.exceptions.BadRequestException;
import com.rotineiro.api.service.TokenService;
import com.rotineiro.api.service.UserService;
import com.rotineiro.api.utils.DefaultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@Tag(name = "Usuário", description = "Controller para o gerenciamento das funções do usuário")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class UserController {

  private final UserService userService;
  private final TokenService tokenService;

  @Autowired
  public UserController(UserService userService, TokenService tokenService) {
    this.userService = userService;
    this.tokenService = tokenService;
  }

  @Operation(summary = "Método para criar um usuário", description = "Cria um usuário e retorna o token de acesso")
  @ApiResponse(responseCode = "200", description = "Usuário criado com sucesso!")
  @ApiResponse(responseCode = "400", description = "Email já existente!")
  @ApiResponse(responseCode = "400", description = "Username já cadastrado!")
  @ApiResponse(responseCode = "500", description = "Erro no servidor!")
  @PostMapping
  public ResponseEntity<DefaultResponse<TokenDto>> createUser(@Valid @RequestBody CreateUserDto userCreationDto) throws BadRequestException {

    User user = this.userService.createUser(userCreationDto);

    String token = this.tokenService.generateToken(user.getUsername());
    TokenDto tokenDto = new TokenDto(token);
    DefaultResponse<TokenDto> response = new DefaultResponse<TokenDto>();
    response.setMessage("Usuário criado com sucesso!");
    response.setResult(tokenDto);

    return ResponseEntity
        .status(HttpStatus.CREATED)
        .body(response);

  }

  @GetMapping("/actualRoutine")
  public ResponseEntity<DefaultResponse<RoutineDto>> getActualRoutine() {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Routine routine = this.userService.getActualRoutine(username);

    DefaultResponse<RoutineDto> response = new DefaultResponse<RoutineDto>();

    response.setMessage("Um novo dia, mais uma rotina!");
    response.setResult(RoutineDto.fromEntity(routine));

    return ResponseEntity.status(HttpStatus.OK).body(response);

  }


}
