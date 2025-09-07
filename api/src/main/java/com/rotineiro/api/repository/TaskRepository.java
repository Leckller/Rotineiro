package com.rotineiro.api.repository;

import com.rotineiro.api.repository.entities.Task;
import com.rotineiro.api.repository.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface TaskRepository extends JpaRepository<Task, Integer> {
  List<Task> findAllByUser(User user);
  List<Task> findAllByUserUsername(String username);

  @Query("SELECT t FROM Task t " +
      "WHERE t.user.username = :username " +
      "AND NOT EXISTS (" +
      "   SELECT 1 FROM t.routines r WHERE r.id = :routineId" +
      ")")
  List<Task> availableTasksForRoutine(@Param("routineId") Integer routineId,
                                      @Param("username") String username);


  List<Task> findAllByIdInAndUserId(List<Integer> taskIds, Integer userId);
}
