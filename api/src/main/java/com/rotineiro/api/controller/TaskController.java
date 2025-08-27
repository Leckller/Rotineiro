package com.rotineiro.api.controller;

import com.rotineiro.api.controller.dtos.Routine.RoutineDto;
import com.rotineiro.api.controller.dtos.Task.CreateTaskDto;
import com.rotineiro.api.controller.dtos.Task.TaskDto;
import com.rotineiro.api.repository.entities.Routine;
import com.rotineiro.api.repository.entities.Task;
import com.rotineiro.api.security.SecurityConfig;
import com.rotineiro.api.service.TaskService;
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
  public ResponseEntity<DefaultResponse<List<TaskDto>>> getAllTasks() {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    List<Task> tasks = this.taskService.getAllTasks(username);
    DefaultResponse<List<TaskDto>> response = new DefaultResponse<List<TaskDto>>();

    response.setMessage("Eba tarefas!");
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


}
