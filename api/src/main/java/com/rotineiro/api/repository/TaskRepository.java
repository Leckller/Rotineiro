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
  @Modifying
  @Query("update Task t set t.routine.id = :routineId " +
         "where t.id in :taskIds and t.user.username = :username")
  int assignTasksToRoutine(@Param("routineId") Integer routineId,
                           @Param("taskIds") List<Integer> taskIds,
                           @Param("username") String username);
  @Query("SELECT t FROM Task t " +
         "WHERE (t.routine IS NULL OR t.routine.id <> :routineId) " +
         "AND t.user.username = :username")
  List<Task> availableTasksForRoutine(@Param("routineId") Integer routineId,
                                      @Param("username") String username);

  List<Task> findAllByIdInAndUserId(List<Integer> taskIds, Integer userId);
}
