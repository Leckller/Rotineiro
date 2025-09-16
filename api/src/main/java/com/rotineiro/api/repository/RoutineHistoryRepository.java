package com.rotineiro.api.repository;

import com.rotineiro.api.repository.entities.RoutineHistory;
import com.rotineiro.api.repository.entities.TaskHistory;
import com.rotineiro.api.repository.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface RoutineHistoryRepository extends JpaRepository<RoutineHistory, Integer> {
  List<RoutineHistory> findAllByUser(User user);
  List<RoutineHistory> findAllByUserAndCreatedAtBetween (User user, LocalDateTime start, LocalDateTime end);
}
