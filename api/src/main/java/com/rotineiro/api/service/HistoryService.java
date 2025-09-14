package com.rotineiro.api.service;

import com.rotineiro.api.repository.RoutineHistoryRepository;
import com.rotineiro.api.repository.TaskHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class HistoryService {

  private final TaskHistoryRepository taskHistoryRepo;
  private final RoutineHistoryRepository routineHistoryRepo;

  @Autowired
  public HistoryService(TaskHistoryRepository taskHistoryRepo, RoutineHistoryRepository routineHistoryRepo) {
    this.taskHistoryRepo = taskHistoryRepo;
    this.routineHistoryRepo = routineHistoryRepo;
  }



}
