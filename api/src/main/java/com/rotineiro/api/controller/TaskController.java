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
  private final RoutineService routineService;

  @Autowired
  public TaskController(TaskService taskService, @Lazy RoutineService routineService) {
    this.taskService = taskService;
    this.routineService = routineService;
  }

  @GetMapping("/all")
  public ResponseEntity<DefaultResponse<List<TaskDto>>> getAllTasks() {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    List<Task> tasks = this.taskService.getAllTasks(username);
    DefaultResponse<List<TaskDto>> response = new DefaultResponse<List<TaskDto>>();

    response.setMessage("Eba tarefas!");
    response.setResult(tasks.stream().map(TaskDto::fromEntity).toList());

    return  ResponseEntity.status(HttpStatus.OK).body(response);

  }
  @GetMapping("/{routineId}")
  public ResponseEntity<DefaultResponse<List<TaskDto>>> getAvailableTasksForRoutine(@PathVariable Integer routineId) {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    List<Task> tasks = this.taskService.availableTasksForRoutine(username, routineId);
    DefaultResponse<List<TaskDto>> response = new DefaultResponse<List<TaskDto>>();

    response.setMessage("Eba tarefas disponíveis!");
    response.setResult(tasks.stream().map(TaskDto::fromEntity).toList());

    return  ResponseEntity.status(HttpStatus.OK).body(response);

  }

  @PostMapping
  public ResponseEntity<DefaultResponse<TaskDto>> createTask(@Valid @RequestBody CreateTaskDto dto) {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Task task = this.taskService.createTask(username, dto);
    DefaultResponse<TaskDto> response = new DefaultResponse<TaskDto>();

    response.setMessage("Tarefa criada com sucesso!");
    response.setResult(TaskDto.fromEntity(task));

    return  ResponseEntity.status(HttpStatus.OK).body(response);

  }

  @PostMapping("/assign/{routineID}")
  public ResponseEntity<DefaultResponse<Null>> assignTaskToRoutine(@PathVariable Integer routineID, @Valid @RequestBody AssignTaskDto dto) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();

    this.routineService.assignTasksToRoutine(username, routineID, dto.tasks());

    DefaultResponse<Null> response = new DefaultResponse<Null>();
    response.setMessage("Tarefas adicionadas com sucesso!");

    return ResponseEntity.status(HttpStatus.OK).body(response);
  }


  @DeleteMapping("/{taskId}")
  public ResponseEntity<DefaultResponse<Null>> deleteTask(@PathVariable Integer taskId) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    this.taskService.deleteTask(username, taskId);

    DefaultResponse<Null> response = new DefaultResponse<Null>();
    response.setMessage("Tarefa deletada com sucesso!");

    return  ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PatchMapping("/{taskId}")
  public ResponseEntity<DefaultResponse<TaskDto>> editTask(@PathVariable Integer taskId, @Valid @RequestBody EditTaskDto dto) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Task task = this.taskService.editTask(username, taskId, dto);

    DefaultResponse<TaskDto> response = new DefaultResponse<TaskDto>();
    response.setMessage("Tarefa editada com sucesso!");
    response.setResult(TaskDto.fromEntity(task));

    return  ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PatchMapping("/complete/{taskId}")
  public ResponseEntity<DefaultResponse<TaskDto>> toggleCompleteTask(@PathVariable Integer taskId) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Task task = this.taskService.toggleTask(username, taskId);

    DefaultResponse<TaskDto> response = new DefaultResponse<TaskDto>();
    response.setMessage("Tarefa concluída com sucesso!");
    response.setResult(TaskDto.fromEntity(task));

    return  ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PatchMapping("start/{taskId}")
  public ResponseEntity<DefaultResponse<TaskDto>> startTask(@PathVariable Integer taskId) {
    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    Task task = this.taskService.startTask(username, taskId);

    DefaultResponse<TaskDto> response = new DefaultResponse<TaskDto>();
    response.setMessage("Tarefa iniciada com sucesso!");
    response.setResult(TaskDto.fromEntity(task));

    return  ResponseEntity.status(HttpStatus.OK).body(response);
  }

}
