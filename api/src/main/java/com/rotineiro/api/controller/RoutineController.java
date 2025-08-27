package com.rotineiro.api.controller;

import com.rotineiro.api.controller.dtos.Routine.CreateRoutineDto;
import com.rotineiro.api.controller.dtos.Routine.RoutineDto;
import com.rotineiro.api.repository.entities.Routine;
import com.rotineiro.api.security.SecurityConfig;
import com.rotineiro.api.service.RoutineService;
import com.rotineiro.api.utils.DefaultResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/routine")
@Tag(name = "Rotina", description = "Controller para o gerenciamento das funções das rotinas")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class RoutineController {

  private final RoutineService routineService;

  @Autowired
  public RoutineController(RoutineService routineService) {
    this.routineService = routineService;
  }

  @GetMapping("/all")
  public ResponseEntity<DefaultResponse<List<RoutineDto>>> getAllRoutines() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    List<Routine> routines = this.routineService.getAllRoutines(username);
    DefaultResponse<List<RoutineDto>> response = new DefaultResponse<List<RoutineDto>>();
    response.setMessage("Eba rotinas!");
    response.setResult(routines.stream().map(RoutineDto::fromEntity).toList());

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping
  public ResponseEntity<DefaultResponse<RoutineDto>> createRoutine(@Valid @RequestBody CreateRoutineDto dto) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Routine routine = this.routineService.createRoutine(username, dto);
    DefaultResponse<RoutineDto> response = new DefaultResponse<RoutineDto>();
    response.setMessage("Rotina criada com sucesso!");
    response.setResult(RoutineDto.fromEntity(routine));

    return ResponseEntity.status(HttpStatus.CREATED).body(response);
  }


}
