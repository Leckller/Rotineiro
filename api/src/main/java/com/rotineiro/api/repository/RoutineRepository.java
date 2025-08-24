package com.rotineiro.api.repository;

import com.rotineiro.api.repository.entities.Routine;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoutineRepository extends JpaRepository<Routine, Integer> {
}
