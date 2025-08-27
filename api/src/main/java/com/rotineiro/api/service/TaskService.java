package com.rotineiro.api.service;

import com.rotineiro.api.controller.dtos.Task.CreateTaskDto;
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

  @Transactional
  public void assingTasksToRoutine(String username, Integer routineId, List<Integer> taskIds ) {
    int updated = this.taskRepo.assignTasksToRoutine(routineId, taskIds, username);
    if (updated != taskIds.size()) {
      System.out.println("Alguma das tarefas não existe.");
    }
  }

}
