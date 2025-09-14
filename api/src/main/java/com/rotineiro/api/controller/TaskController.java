package com.rotineiro.api.controller;

import com.rotineiro.api.controller.dtos.Routine.RoutineDto;
import com.rotineiro.api.controller.dtos.Task.AssignTaskDto;
import com.rotineiro.api.controller.dtos.Task.CreateTaskDto;
import com.rotineiro.api.controller.dtos.Task.EditTaskDto;
import com.rotineiro.api.controller.dtos.Task.TaskDto;
import com.rotineiro.api.repository.entities.Routine;
import com.rotineiro.api.repository.entities.Task;
import com.rotineiro.api.security.SecurityConfig;
import com.rotineiro.api.service.RoutineService;
import com.rotineiro.api.service.TaskService;
import com.rotineiro.api.utils.DefaultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/task")
@Tag(name = "Tarefa", description = "Controller para o gerenciamento das funções das tarefas")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class TaskController {

  private final TaskService taskService;

  @Autowired
  public TaskController(TaskService taskService) {
    this.taskService = taskService;
  }

  @GetMapping("/all")
  @Operation(summary = "Carrega todas as tarefas do usuário", description = "Carrega todas as tarefas do usuário")
  public ResponseEntity<DefaultResponse<List<TaskDto>>> getAllTasks() {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    List<Task> tasks = this.taskService.getAllTasks(username);
    DefaultResponse<List<TaskDto>> response = new DefaultResponse<List<TaskDto>>();

    response.setMessage("Eba tarefas!");
    response.setResult(tasks.stream().map(TaskDto::fromEntity).toList());

    return  ResponseEntity.status(HttpStatus.OK).body(response);

  }

  @GetMapping("/{routineId}")
  @Operation(summary = "Carrega as tarefas disponíveis para a rotina", description = "Carrega as tarefas disponíveis para a rotina")
  public ResponseEntity<DefaultResponse<List<TaskDto>>> getAvailableTasksForRoutine(@PathVariable Integer routineId) {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    List<Task> tasks = this.taskService.availableTasksForRoutine(username, routineId);
    DefaultResponse<List<TaskDto>> response = new DefaultResponse<List<TaskDto>>();

    response.setMessage("Eba tarefas disponíveis!");
    response.setResult(tasks.stream().map(TaskDto::fromEntity).toList());

    return  ResponseEntity.status(HttpStatus.OK).body(response);

  }

  @PatchMapping("/toggleStatus/{taskID}")
  @Operation(summary = "Alterna o status de uma tarefa pelo id", description = "Alterna o status da tarefa e retorna a mesma")
  public ResponseEntity<DefaultResponse<TaskDto>> completeTask(@PathVariable Integer taskID) {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Task task = this.taskService.toggleTask(username, taskID);
    DefaultResponse<TaskDto> response = new DefaultResponse<TaskDto>();

    response.setMessage("Status da tarefa alterado com sucesso!");
    response.setResult(TaskDto.fromEntity(task));

    return  ResponseEntity.status(HttpStatus.OK).body(response);

  }

  @PostMapping
  @Operation(summary = "Cria uma tarefa", description = "Cria uma tarefa e retorna uma tarefa")
  public ResponseEntity<DefaultResponse<TaskDto>> createTask(@Valid @RequestBody CreateTaskDto dto) {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Task task = this.taskService.createTask(username, dto);
    DefaultResponse<TaskDto> response = new DefaultResponse<TaskDto>();

    response.setMessage("Tarefa criada com sucesso!");
    response.setResult(TaskDto.fromEntity(task));

    return  ResponseEntity.status(HttpStatus.OK).body(response);

  }

  @DeleteMapping("/{taskId}")
  @Operation(summary = "Deleta uma tarefa", description = "Deleta uma tarefa")
  public ResponseEntity<DefaultResponse<Null>> deleteTask(@PathVariable Integer taskId) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    this.taskService.deleteTask(username, taskId);

    DefaultResponse<Null> response = new DefaultResponse<Null>();
    response.setMessage("Tarefa deletada com sucesso!");

    return  ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PatchMapping("/{taskId}")
  @Operation(summary = "Edita uma tarefa pelo id", description = "Edita uma tarefa pelo id")
  public ResponseEntity<DefaultResponse<TaskDto>> editTask(@PathVariable Integer taskId, @Valid @RequestBody EditTaskDto dto) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Task task = this.taskService.editTask(username, taskId, dto);

    DefaultResponse<TaskDto> response = new DefaultResponse<TaskDto>();
    response.setMessage("Tarefa editada com sucesso!");
    response.setResult(TaskDto.fromEntity(task));

    return  ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PatchMapping("start/{taskId}")
  @Operation(summary = "Inicia uma tarefa", description = "Seta o horário em que a tarefa foi iniciada")
  public ResponseEntity<DefaultResponse<TaskDto>> startTask(@PathVariable Integer taskId) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Task task = this.taskService.startTask(username, taskId);

    DefaultResponse<TaskDto> response = new DefaultResponse<TaskDto>();
    response.setMessage("Tarefa iniciada com sucesso!");
    response.setResult(TaskDto.fromEntity(task));

    return  ResponseEntity.status(HttpStatus.OK).body(response);
  }

}
