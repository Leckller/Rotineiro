package com.rotineiro.api.service;

import com.rotineiro.api.controller.dtos.Routine.CreateRoutineDto;
import com.rotineiro.api.controller.dtos.Task.CreateTaskDto;
import com.rotineiro.api.repository.RoutineHistoryRepository;
import com.rotineiro.api.repository.RoutineRepository;
import com.rotineiro.api.repository.entities.Routine;
import com.rotineiro.api.repository.entities.Task;
import com.rotineiro.api.repository.entities.User;
import com.rotineiro.api.security.exceptions.NotFoundException;
import com.rotineiro.api.security.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class RoutineService {

  @Autowired
  private RoutineRepository routineRepo;
  @Autowired
  private RoutineHistoryRepository routineHistoryRepository;
  @Autowired
  private UserService userService;
  @Autowired
  private TaskService taskService;

  public RoutineService(RoutineRepository routineRepo, RoutineHistoryRepository routineHistoryRepository, TaskService taskService) {
    this.routineRepo = routineRepo;
    this.routineHistoryRepository = routineHistoryRepository;
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

  public List<Routine> getRoutines(String username) {

    User user = this.userService.findByUsername(username);
    return this.routineRepo.findAllByUser(user);

  }

  public Routine createRoutine(String username, CreateRoutineDto dto) {

    User user = this.userService.findByUsername(username);

    Routine routine = new Routine();

    routine.setName(dto.name());
    routine.setUser(user);

    if(dto.priority() != null) {
      routine.setPriority(dto.priority());
    }

    this.routineRepo.save(routine);

    if(!dto.tasks().isEmpty()) {
      this.taskService.assingTasksToRoutine(username, routine.getId(), dto.tasks());
    }

    return routine;

  }

}
