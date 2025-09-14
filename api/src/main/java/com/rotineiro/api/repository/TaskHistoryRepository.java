package com.rotineiro.api.repository;

import com.rotineiro.api.repository.entities.TaskHistory;
import com.rotineiro.api.repository.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Integer> {
  List<TaskHistory> findAllByUserAndCreatedAtBetween (User user, LocalDateTime start, LocalDateTime end);
}
