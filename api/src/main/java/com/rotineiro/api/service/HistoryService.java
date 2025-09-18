package com.rotineiro.api.service;

import com.rotineiro.api.controller.dtos.History.*;
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

    List<DayUseDto> amountTaskUse = this.amountOfTaskUse(user, start, end);
    List<DayUseRoutine> amountRoutineUse = this.amountOfRoutineUse(user, start, end);
    MostUsedDto mostUsedRoutine = this.mostUsedRoutine(user, start, end);
    HistoryTasksCountDto historyTasksCount = this.getTaskCount(user, start, end);
    SequencyDto actualSequency = this.userService.getSequency();

    return new CompleteHistoryDto(mostUsedRoutine, amountRoutineUse, amountTaskUse, historyTasksCount, actualSequency);
  }

  public HistoryTasksCountDto getTaskCount(User user, LocalDateTime start, LocalDateTime end) {
    Object[] counts = this.taskHistoryRepo.countTotalAndCompletedByDate(user, start, end).getFirst();
    Long total = (Long) counts[0];
    long completed = counts[1] == null ? 0L : (Long) counts[1];

    return new HistoryTasksCountDto(total.intValue(), (int) completed);
  }

  public List<DayUseRoutine> amountOfRoutineUse(User user, LocalDateTime start, LocalDateTime end) {

    List<RoutineHistory> routines = routineHistoryRepo.findAllByUserAndCreatedAtBetween(user, start, end);

    return routines.stream()
        .collect(Collectors.groupingBy(RoutineHistory::getName))
        .keySet().stream()
        .map(DayUseRoutine::new).toList();
  }

  public List<DayUseDto> amountOfTaskUse(User user, LocalDateTime start, LocalDateTime end) {

    List<TaskHistory> tasks = taskHistoryRepo.findAllByUserAndCreatedAtBetween(user, start, end);

    return tasks.stream()
        .collect(Collectors.groupingBy(r -> r.getCreatedAt().toLocalDate()))
        .entrySet().stream()
        .map(entry -> new DayUseDto(entry.getKey(),
            entry.getValue().stream().map(task -> new AmountUseDto(task.getId(), task.getName() )).toList()))
        .collect(Collectors.toList());

  }

  public MostUsedDto mostUsedRoutine(User user, LocalDateTime start, LocalDateTime end) {

    List<RoutineHistory> histories = routineHistoryRepo.findAllByUserAndCreatedAtBetween(user, start, end);

    Optional<MostUsedDto> mostUsedRoutine = histories.stream()
        .collect(Collectors.groupingBy(RoutineHistory::getRoutine, Collectors.counting()))
        .entrySet().stream()
        .max(Map.Entry.comparingByValue()) // pega o maior
        .map(entry -> new MostUsedDto(
            entry.getKey().getId(),
            entry.getKey().getName(),
            entry.getValue().intValue(),
            entry.getKey().getCreatedAt()
        ));

    return mostUsedRoutine.orElseGet(() -> new MostUsedDto(0, "", 0, null));

  }

}
