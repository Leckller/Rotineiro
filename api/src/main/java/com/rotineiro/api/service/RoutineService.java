package com.rotineiro.api.service;

import com.rotineiro.api.controller.dtos.Routine.CreateRoutineDto;
import com.rotineiro.api.controller.dtos.Routine.EditRoutineDto;
import com.rotineiro.api.controller.dtos.Task.CreateTaskDto;
import com.rotineiro.api.repository.RoutineHistoryRepository;
import com.rotineiro.api.repository.RoutineRepository;
import com.rotineiro.api.repository.TaskRepository;
import com.rotineiro.api.repository.UserRepository;
import com.rotineiro.api.repository.entities.*;
import com.rotineiro.api.security.exceptions.BadRequestException;
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
public class RoutineService {

  private final RoutineRepository routineRepo;
  private final TaskRepository taskRepo;
  private final UserRepository userRepository;
  private final RoutineHistoryRepository routineHistoryRepository;
  private final UserService userService;

  @Autowired
  public RoutineService(RoutineRepository routineRepo, TaskRepository taskRepo, UserRepository userRepository, RoutineHistoryRepository routineHistoryRepository, UserService userService) {
    this.routineRepo = routineRepo;
    this.taskRepo = taskRepo;
    this.userRepository = userRepository;
    this.routineHistoryRepository = routineHistoryRepository;
    this.userService = userService;
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

  @Transactional
  public Routine assignTasksToRoutine(String username, Integer routineId, List<Integer> taskIds) {
    Routine routine = routineRepo.findById(routineId)
        .orElseThrow(() -> new NotFoundException("Rotina não encontrada"));

    if (!routine.getUser().getUsername().equals(username)) {
      throw new UnauthorizedException("Você não pode alterar esta rotina");
    }

    List<Task> tasks = taskRepo.findAllById(taskIds).stream()
        .filter(task -> task.getUser().getUsername().equals(username))
        .toList();

    // adicionar relação dos dois lados
    for (Task task : tasks) {
      routine.getTasks().add(task);
      task.getRoutines().add(routine);
    }

    // salva apenas a rotina (JPA sincroniza a tabela de junção)
    return routineRepo.save(routine);
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
      this.assignTasksToRoutine(username, routine.getId(), dto.tasks());
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

  @Transactional
  public Routine startRoutine(String username, Integer routineId) {
    User user = this.userService.findByUsername(username);

    // Se já existe uma rotina ativa → mover para histórico
    if (user.hasActiveRoutine()) {
      Routine activeRoutine = user.getActiveRoutine();

      // Criar RoutineHistory
      RoutineHistory history = RoutineHistory.builder()
          .name(activeRoutine.getName())
          .priority(activeRoutine.getPriority())
          .description(activeRoutine.getDescription())
          .startedAt(activeRoutine.getStartedAt())
          .finishedAt(LocalDateTime.now()) // rotina finalizada
          .user(user)
          .routine(activeRoutine)
          .build();

      // Criar TaskHistory para cada tarefa
      List<TaskHistory> taskHistories = activeRoutine.getTasks().stream()
          .map(task -> TaskHistory.builder()
              .name(task.getName())
              .estimate(task.getEstimate())
              .completed(task.getCompleted())
              .startedAt(task.getStartedAt())
              .finishedAt(task.getFinishedAt())
              .task(task)
              .user(user)
              .routineHistory(history)
              .build()
          ).toList();

      history.setTasks(taskHistories);

      // Salvar histórico
      routineHistoryRepository.save(history);

      // Resetar os dados da rotina ativa (template)
      activeRoutine.setStartedAt(null);

      activeRoutine.getTasks().forEach(task -> {
        task.setStartedAt(null);
        task.setFinishedAt(null);
        task.setCompleted(false);
      });

      routineRepo.save(activeRoutine);
    }

    // Buscar a nova rotina
    Routine routine = this.getRoutinebyId(username, routineId);

    if (routine.getTasks().isEmpty()) {
      throw new BadRequestException("Adicione pelo menos uma Tarefa na Rotina para iniciar.");
    }

    // Iniciar nova rotina
    routine.setStartedAt(LocalDateTime.now());
    user.setActiveRoutine(routine);

    userRepository.save(user); // atualiza o usuário
    return routineRepo.save(routine);
  }


}
