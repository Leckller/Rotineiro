package com.rotineiro.api.repository;

import com.rotineiro.api.repository.entities.TaskHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Integer> {
}
