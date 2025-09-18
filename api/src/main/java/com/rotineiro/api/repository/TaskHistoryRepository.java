package com.rotineiro.api.repository;

import com.rotineiro.api.repository.entities.TaskHistory;
import com.rotineiro.api.repository.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface TaskHistoryRepository extends JpaRepository<TaskHistory, Integer> {
  @Query("SELECT t FROM TaskHistory t " +
      "WHERE t.user = :user " +
      "AND t.completed = true " +
      "AND t.createdAt BETWEEN :startDate AND :endDate")
  List<TaskHistory> findAllByUserAndCompleted (
      @Param("user") User user,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);

  @Query("SELECT COUNT(t), SUM(CASE WHEN t.completed = true THEN 1 ELSE 0 END) " +
      "FROM TaskHistory t " +
      "WHERE t.user = :user " +
      "AND t.createdAt BETWEEN :startDate AND :endDate")
  List<Object[]> countTotalAndCompletedByDate(
      @Param("user") User user,
      @Param("startDate") LocalDateTime startDate,
      @Param("endDate") LocalDateTime endDate);
}
