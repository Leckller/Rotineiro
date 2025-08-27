package com.rotineiro.api.repository;

import com.rotineiro.api.repository.entities.Routine;
import com.rotineiro.api.repository.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RoutineRepository extends JpaRepository<Routine, Integer> {

  List<Routine> findAllByUser(User user);

}
