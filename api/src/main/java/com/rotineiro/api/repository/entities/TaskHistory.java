package com.rotineiro.api.repository.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "task_histories")
public class TaskHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private Double estimate;

  @Column(nullable = false)
  private Boolean completed = false;

  @Column(nullable = true)
  private LocalDateTime startedAt;

  @Column(nullable = true)
  private LocalDateTime finishedAt;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @ManyToOne
  @JoinColumn(name = "task_id", nullable = false)
  private Task task;

  @ManyToOne
  @JoinColumn(name = "routine_history_id", nullable = false)
  private RoutineHistory routineHistory;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

}
