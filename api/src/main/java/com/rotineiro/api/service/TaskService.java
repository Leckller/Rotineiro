package com.rotineiro.api.service;

import com.rotineiro.api.controller.dtos.Task.CreateTaskDto;
import com.rotineiro.api.controller.dtos.Task.EditTaskDto;
import com.rotineiro.api.repository.TaskHistoryRepository;
import com.rotineiro.api.repository.TaskRepository;
import com.rotineiro.api.repository.entities.Routine;
import com.rotineiro.api.repository.entities.Task;
import com.rotineiro.api.repository.entities.User;
import com.rotineiro.api.security.exceptions.NotFoundException;
import com.rotineiro.api.security.exceptions.UnauthorizedException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Service
public class TaskService {

  private final TaskRepository taskRepo;
  private final UserService userService;
  private final RoutineService routineService;
  private final TaskHistoryRepository taskHistRepo;

  @Autowired
  public TaskService(TaskRepository taskRepo, UserService userService, @Lazy RoutineService routineService, TaskHistoryRepository taskHistRepo) {
    this.taskRepo = taskRepo;
    this.userService = userService;
    this.routineService = routineService;
    this.taskHistRepo = taskHistRepo;
  }

  public Task getTaskById(String username, Integer taskId) {
    Task task = this.taskRepo
        .findById(taskId)
        .orElseThrow(() -> new NotFoundException("Tarefa não encontrada!"));

    if (!Objects.equals(task.getUser().getUsername(), username)) {
      throw new UnauthorizedException("Você não tem acesso a essa tarefa!");
    }

    return task;

  }

  public List<Task> getAllTasks(String username) {
    return this.taskRepo.findAllByUserUsername(username);
  }

  public Task createTask(String username, CreateTaskDto dto) {

    User user = this.userService.findByUsername(username);

    Task task = new Task();
    task.setName(dto.name());
    task.setEstimate(dto.estimate());
    task.setUser(user);

    if(dto.routineId() != null) {
      Routine routine = this.routineService.getRoutinebyId(username, dto.routineId());
      task.setRoutine(routine);
    }

    return this.taskRepo.save(task);

  }

  public List<Task> availableTasksForRoutine (String username, Integer routineId) {
    return this.taskRepo.availableTasksForRoutine(routineId, username);
  }

  @Transactional
  public void assingTasksToRoutine(String username, Integer routineId, List<Integer> taskIds ) {
    int updated = this.taskRepo.assignTasksToRoutine(routineId, taskIds, username);
    if (updated != taskIds.size()) {
      System.out.println("Alguma das tarefas não existe.");
    }
  }

  public void deleteTask(String username, Integer taskId) {
    Task task = this.getTaskById(username, taskId);
    this.taskRepo.delete(task);
  }

  public Task editTask(String username, Integer taskId, EditTaskDto dto) {
    Task task = this.getTaskById(username, taskId);

    if (dto.name() != null) {
      task.setName(dto.name());
    }
    if (dto.estimate() != null) {
      task.setEstimate(dto.estimate());
    }

    return this.taskRepo.save(task);
  }

  public Task toggleTask(String username, Integer taskID) {
    Task task = this.getTaskById(username, taskID);

    if (task.getCompleted()) {
      // resetar
      task.setStartedAt(null);
      task.setFinishedAt(null);
      task.setCompleted(false);
    } else {
      // concluir
      task.setCompleted(true);
      if (task.getStartedAt() == null) {
        task.setStartedAt(LocalDateTime.now().minusMinutes(task.getEstimate().longValue()));
      }
      task.setFinishedAt(LocalDateTime.now());
    }

    return taskRepo.save(task);
  }

  public Task startTask(String username, Integer taskID) {
    Task task = this.getTaskById(username, taskID);

    task.setStartedAt(LocalDateTime.now());
    task.setCompleted(false);
    task.setFinishedAt(null);

    return taskRepo.save(task);
  }


}
