package com.rotineiro.api.service;

import com.rotineiro.api.controller.dtos.Task.CreateTaskDto;
import com.rotineiro.api.repository.TaskHistoryRepository;
import com.rotineiro.api.repository.TaskRepository;
import com.rotineiro.api.repository.entities.Task;
import com.rotineiro.api.repository.entities.User;
import com.rotineiro.api.security.exceptions.NotFoundException;
import com.rotineiro.api.security.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class TaskService {

  @Autowired
  private final TaskRepository taskRepo;
  private final UserService userService;
  private final TaskHistoryRepository taskHistRepo;

  public TaskService(TaskRepository taskRepo, UserService userService, TaskHistoryRepository taskHistRepo) {
    this.taskRepo = taskRepo;
    this.userService = userService;
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
    return this.taskRepo.save(task);

  }




}
