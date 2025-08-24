package com.rotineiro.api.repository;

import com.rotineiro.api.repository.entities.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Integer> {
}
