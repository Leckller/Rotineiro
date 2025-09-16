package com.rotineiro.api.service;

import com.rotineiro.api.controller.dtos.History.AmountUseDto;
import com.rotineiro.api.controller.dtos.History.CompleteHistoryDto;
import com.rotineiro.api.controller.dtos.History.HistoryTasksCountDto;
import com.rotineiro.api.controller.dtos.History.SequencyDto;
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

  public CompleteHistoryDto completeHistory(String username, LocalDateTime start, LocalDateTime end) {
    User user = userService.findByUsername(username);

    List<AmountUseDto> amountTaskUse = this.amountOfTaskUseBetweenDate(user, start, end);
    List<AmountUseDto> amountUse = this.amountOfRoutineUse(user, start, end);
    AmountUseDto mostUsedRoutine  = this.mostUsedRoutine(user, start, end);
    HistoryTasksCountDto historyTasksCount = this.getTaskCount(user, start, end);
    SequencyDto actualSequency = this.userService.getSequency();

    return new CompleteHistoryDto(mostUsedRoutine, amountUse, amountTaskUse, historyTasksCount, actualSequency);
  }

  public HistoryTasksCountDto getTaskCount(User user, LocalDateTime start, LocalDateTime end) {
    Object[] counts = this.taskHistoryRepo.countTotalAndCompletedByDate(user, start, end);
    Long total = (Long) counts[0];
    Long completed = (Long) counts[1];

    return new HistoryTasksCountDto(total.intValue(), completed.intValue());
  }

  public List<AmountUseDto> amountOfRoutineUse(User user, LocalDateTime start, LocalDateTime end) {

    List<RoutineHistory> routines = routineHistoryRepo.findAllByUserAndCreatedAtBetween(user, start, end);

    Map<Routine, Long> routineUsage = routines.stream()
        .collect(Collectors.groupingBy(
            RoutineHistory::getRoutine, // chave: a rotina
            Collectors.counting()       // valor: quantidade de usos
        ));

    return routineUsage.entrySet().stream()
        .map(entry -> new AmountUseDto(
            entry.getKey().getId(),
            entry.getKey().getName(),
            entry.getValue().intValue()
        ))
        .sorted((a, b) -> Long.compare(b.uses(), a.uses()))
        .collect(Collectors.toList());
  }

  public List<AmountUseDto> amountOfTaskUseBetweenDate(User user, LocalDateTime start, LocalDateTime end) {

    List<TaskHistory> tasks = taskHistoryRepo.findAllByUserAndCreatedAtBetween(user, start, end);

    Map<Task, Long> taskUsage = tasks.stream()
        .collect(Collectors.groupingBy(
            TaskHistory::getTask, // chave: a rotina
            Collectors.counting()       // valor: quantidade de usos
        ));

    return taskUsage.entrySet().stream()
        .map(entry -> new AmountUseDto(
            entry.getKey().getId(),
            entry.getKey().getName(),
            entry.getValue().intValue()
        ))
        .sorted((a, b) -> Long.compare(b.uses(), a.uses()))
        .collect(Collectors.toList());

  }

  public AmountUseDto mostUsedRoutine(User user, LocalDateTime start, LocalDateTime end) {

    List<RoutineHistory> histories = routineHistoryRepo.findAllByUserAndCreatedAtBetween(user, start, end);

    Optional<AmountUseDto> mostUsedRoutine = histories.stream()
        .collect(Collectors.groupingBy(RoutineHistory::getRoutine, Collectors.counting()))
        .entrySet().stream()
        .max(Map.Entry.comparingByValue()) // pega o maior
        .map(entry -> new AmountUseDto(
            entry.getKey().getId(),
            entry.getKey().getName(),
            entry.getValue().intValue()
        ));

    return mostUsedRoutine.orElseGet(() -> new AmountUseDto(0, "", 0));

  }

}
