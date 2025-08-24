package com.rotineiro.api.repository;

import com.rotineiro.api.repository.entities.RoutineHistory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineHistoryRepository extends JpaRepository<RoutineHistory, Integer> {
}
