package com.rotineiro.api.controller;

import com.rotineiro.api.controller.dtos.Routine.CreateRoutineDto;
import com.rotineiro.api.controller.dtos.Routine.EditRoutineDto;
import com.rotineiro.api.controller.dtos.Routine.RoutineDto;
import com.rotineiro.api.controller.dtos.Task.AssignTaskDto;
import com.rotineiro.api.controller.dtos.Task.DeallocateTaskDto;
import com.rotineiro.api.controller.dtos.Task.EditTaskDto;
import com.rotineiro.api.repository.entities.Routine;
import com.rotineiro.api.security.SecurityConfig;
import com.rotineiro.api.service.RoutineService;
import com.rotineiro.api.utils.DefaultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
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
  @Operation(summary = "Carrega todas as rotinas do usuário")
  public ResponseEntity<DefaultResponse<List<RoutineDto>>> getAllRoutines() {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    List<Routine> routines = this.routineService.getAllRoutines(username);
    DefaultResponse<List<RoutineDto>> response = new DefaultResponse<List<RoutineDto>>();
    response.setMessage("Eba rotinas!");
    response.setResult(routines.stream().map(RoutineDto::fromEntity).toList());

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @GetMapping("/{id}")
  @Operation(summary = "Carrega uma rotina pelo ID")
  public ResponseEntity<DefaultResponse<RoutineDto>> getByRoutineId(@PathVariable Integer id) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Routine routine = this.routineService.getRoutinebyId(username, id);
    DefaultResponse<RoutineDto> response = new DefaultResponse<RoutineDto>();
    response.setMessage("Uma rotina fresquinha direto do forno!");
    response.setResult(RoutineDto.fromEntity(routine));

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping
  @Operation(summary = "Cria uma rotina")
  public ResponseEntity<DefaultResponse<RoutineDto>> createRoutine(@Valid @RequestBody CreateRoutineDto dto) {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Routine routine = this.routineService.createRoutine(username, dto);
    DefaultResponse<RoutineDto> response = new DefaultResponse<RoutineDto>();

    response.setMessage("Rotina criada com sucesso!");
    response.setResult(RoutineDto.fromEntity(routine));

    return ResponseEntity.status(HttpStatus.CREATED).body(response);

  }

  @PatchMapping("/{routineID}")
  @Operation(summary = "Edita uma rotina pelo ID")
  public ResponseEntity<DefaultResponse<RoutineDto>> editRoutine(@PathVariable Integer routineID, @Valid @RequestBody EditRoutineDto dto) {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Routine routine = this.routineService.editRoutine(username, routineID, dto);
    DefaultResponse<RoutineDto> response = new DefaultResponse<RoutineDto>();

    response.setMessage("Rotina editada com sucesso!");
    response.setResult(RoutineDto.fromEntity(routine));

    return ResponseEntity.status(HttpStatus.OK).body(response);

  }

  @PostMapping("/assign/{routineID}")
  @Operation(summary = "Adiciona tarefas para uma rotina", description = "Recebe um array de IDs de tarefas e cria o relacionamento tarefa-rotina")
  public ResponseEntity<DefaultResponse<RoutineDto>> assignTaskToRoutine(@PathVariable Integer routineID, @Valid @RequestBody AssignTaskDto dto) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Routine routine = this.routineService.assignTasksToRoutine(username, routineID, dto.tasks());

    DefaultResponse<RoutineDto> response = new DefaultResponse<RoutineDto>();
    response.setResult(RoutineDto.fromEntity(routine));
    response.setMessage("Tarefas adicionadas com sucesso!");

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping("/deallocate/{routineID}")
  @Operation(summary = "Remove tarefas de uma rotina", description = "Recebe um array de IDs de tarefas e remove o relacionamento tarefa-rotina")
  public ResponseEntity<DefaultResponse<RoutineDto>> deallocateTaskToRoutine(@PathVariable Integer routineID, @Valid @RequestBody DeallocateTaskDto dto) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Routine routine = this.routineService.deallocateTaskToRoutine(username, routineID, dto.tasks());

    DefaultResponse<RoutineDto> response = new DefaultResponse<RoutineDto>();
    response.setResult(RoutineDto.fromEntity(routine));
    response.setMessage("Tarefas removidas da rotina com sucesso!");

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PatchMapping("/start/{routineID}")
  @Operation(summary = "Seleciona uma rotina como ativa", description = "Atribui uma rotina como ativa, caso já exista uma ativa ela é finalizada e é gerado o histórico dela e de suas tarefas")
  public ResponseEntity<DefaultResponse<RoutineDto>> startRoutine(@PathVariable Integer routineID) {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Routine routine = this.routineService.startRoutine(username, routineID);

    DefaultResponse<RoutineDto> response = new DefaultResponse<RoutineDto>();

    response.setMessage("Rotina selecionada com sucesso!");
    response.setResult(RoutineDto.fromEntity(routine));

    return ResponseEntity.status(HttpStatus.OK).body(response);

  }

  @PatchMapping("finish")
  @Operation(summary = "Finaliza uma Rotina", description = "Finaliza uma rotina, salva no histórico e deixa a rotina ativa como NULL")
  public ResponseEntity<DefaultResponse<Null>> finishRoutine() {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    this.routineService.finishRoutine(username);

    DefaultResponse<Null> response = new DefaultResponse<Null>();

    response.setMessage("Rotina finalizada com sucesso!");
    response.setResult(null);

    return ResponseEntity.status(HttpStatus.OK).body(response);

  }

  @DeleteMapping("{routineID}")
  @Operation(summary = "Apaga uma rotina")
  public ResponseEntity<DefaultResponse<Null>> deleteRoutine(@PathVariable Integer routineID) {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    this.routineService.deleteRoutine(username, routineID);

    DefaultResponse<Null> response = new DefaultResponse<Null>();

    response.setMessage("Rotina deletada com sucesso!");
    response.setResult(null);

    return ResponseEntity.status(HttpStatus.OK).body(response);

  }


}
