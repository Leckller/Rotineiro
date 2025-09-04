package com.rotineiro.api.service;

import com.rotineiro.api.controller.dtos.Routine.CreateRoutineDto;
import com.rotineiro.api.controller.dtos.Routine.EditRoutineDto;
import com.rotineiro.api.controller.dtos.Task.CreateTaskDto;
import com.rotineiro.api.repository.RoutineHistoryRepository;
import com.rotineiro.api.repository.RoutineRepository;
import com.rotineiro.api.repository.entities.Routine;
import com.rotineiro.api.repository.entities.Task;
import com.rotineiro.api.repository.entities.User;
import com.rotineiro.api.security.exceptions.NotFoundException;
import com.rotineiro.api.security.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RoutineService {

  private final RoutineRepository routineRepo;
  private final RoutineHistoryRepository routineHistoryRepository;
  private final UserService userService;
  private final TaskService taskService;

  @Autowired
  public RoutineService(RoutineRepository routineRepo, RoutineHistoryRepository routineHistoryRepository, UserService userService, @Lazy TaskService taskService) {
    this.routineRepo = routineRepo;
    this.routineHistoryRepository = routineHistoryRepository;
    this.userService = userService;
    this.taskService = taskService;
  }

  public Routine getRoutinebyId(String username, Integer routineId) throws NotFoundException, UnauthorizedException {

    Routine routine = this.routineRepo
        .findById(routineId)
        .orElseThrow(() -> new NotFoundException("Rotina não encontrada"));

    if (!Objects.equals(routine.getUser().getUsername(), username)) {
      throw new UnauthorizedException("Você não tem permissão para acessar essa rotina");
    }

    return routine;

  }

  public List<Routine> getAllRoutines(String username) {

    User user = this.userService.findByUsername(username);
    return this.routineRepo.findAllByUser(user);

  }

  public Routine createRoutine(String username, CreateRoutineDto dto) {

    User user = this.userService.findByUsername(username);

    Routine routine = new Routine();

    routine.setName(dto.name());
    routine.setDescription(dto.description());
    routine.setUser(user);

    if(dto.priority() != null) {
      routine.setPriority(dto.priority());
    }

    this.routineRepo.save(routine);

    if(dto.tasks() != null && !dto.tasks().isEmpty()) {
      this.taskService.assingTasksToRoutine(username, routine.getId(), dto.tasks());
    }

    return routine;

  }

  public Routine editRoutine(String username, Integer routineID, EditRoutineDto dto) {

    Routine routine = this.getRoutinebyId(username, routineID);

    if(dto.priority() != null) {
      routine.setPriority(dto.priority());
    }

    if(dto.name() != null) {
      routine.setName(dto.name());
    }

    if(dto.description() != null) {
      routine.setDescription(dto.description());
    }

    return this.routineRepo.save(routine);

  }

}
