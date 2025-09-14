package com.rotineiro.api.service;

import com.rotineiro.api.controller.dtos.History.AmountRoutineUseDto;
import com.rotineiro.api.controller.dtos.History.AmountTaskUseDto;
import com.rotineiro.api.repository.RoutineHistoryRepository;
import com.rotineiro.api.repository.TaskHistoryRepository;
import com.rotineiro.api.repository.entities.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class HistoryService {

  private final TaskHistoryRepository taskHistoryRepo;
  private final RoutineHistoryRepository routineHistoryRepo;
  private final UserService userService;

  @Autowired
  public HistoryService(TaskHistoryRepository taskHistoryRepo, RoutineHistoryRepository routineHistoryRepo, UserService userService) {
    this.taskHistoryRepo = taskHistoryRepo;
    this.routineHistoryRepo = routineHistoryRepo;
    this.userService = userService;
  }

  public List<AmountRoutineUseDto> amountOfRoutineUse(String username) {

    User user = this.userService.findByUsername(username);
    List<RoutineHistory> routines = routineHistoryRepo.findAllByUser(user);

    Map<Routine, Long> routineUsage = routines.stream()
        .collect(Collectors.groupingBy(
            RoutineHistory::getRoutine, // chave: a rotina
            Collectors.counting()       // valor: quantidade de usos
        ));

    return routineUsage.entrySet().stream()
        .map(entry -> new AmountRoutineUseDto(
            entry.getKey().getId(),
            entry.getKey().getName(),
            entry.getValue().intValue()
        ))
        .sorted((a, b) -> Long.compare(b.uses(), a.uses()))
        .collect(Collectors.toList());
  }

  public List<AmountTaskUseDto> amountOfTaskUseBetweenDate(String username, LocalDateTime start, LocalDateTime end) {

    User user = this.userService.findByUsername(username);
    List<TaskHistory> tasks = taskHistoryRepo.findAllByUserAndCreatedAtBetween(user, start, end);

    Map<Task, Long> taskUsage = tasks.stream()
        .collect(Collectors.groupingBy(
            TaskHistory::getTask, // chave: a rotina
            Collectors.counting()       // valor: quantidade de usos
        ));

    return taskUsage.entrySet().stream()
        .map(entry -> new AmountTaskUseDto(
            entry.getKey().getId(),
            entry.getKey().getName(),
            entry.getValue().intValue()
        ))
        .sorted((a, b) -> Long.compare(b.uses(), a.uses()))
        .collect(Collectors.toList());

  }

  public AmountRoutineUseDto mostUsedRoutine(String username, LocalDateTime start, LocalDateTime end) {

    User user = this.userService.findByUsername(username);
    List<RoutineHistory> histories = routineHistoryRepo.findAllByUserAndCreatedAtBetween(user, start, end);

    Optional<AmountRoutineUseDto> mostUsedRoutine = histories.stream()
        .collect(Collectors.groupingBy(RoutineHistory::getRoutine, Collectors.counting()))
        .entrySet().stream()
        .max(Map.Entry.comparingByValue()) // pega o maior
        .map(entry -> new AmountRoutineUseDto(
            entry.getKey().getId(),
            entry.getKey().getName(),
            entry.getValue().intValue()
        ));

    return mostUsedRoutine.orElseGet(() -> new AmountRoutineUseDto(0, "", 0));

  }

}
