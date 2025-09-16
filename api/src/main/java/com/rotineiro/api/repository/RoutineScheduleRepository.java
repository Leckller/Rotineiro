package com.rotineiro.api.repository;

import com.rotineiro.api.repository.entities.RoutineSchedule;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineScheduleRepository extends JpaRepository<RoutineSchedule, Integer> {
}
