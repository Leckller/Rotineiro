package com.rotineiro.api.service;

import com.rotineiro.api.repository.TaskHistoryRepository;
import com.rotineiro.api.repository.TaskRepository;
import com.rotineiro.api.repository.entities.Task;
import com.rotineiro.api.security.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskService {

  @Autowired
  private TaskRepository taskRepo;
  private TaskHistoryRepository taskHistRepo;

  public TaskService(TaskRepository taskRepo) {
    this.taskRepo = taskRepo;
  }

}
