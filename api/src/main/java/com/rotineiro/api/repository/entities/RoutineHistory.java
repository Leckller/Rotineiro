package com.rotineiro.api.repository.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "routine_histories")
public class RoutineHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private boolean completed = false;

  @Column(nullable = false)
  private Integer tasksCompleted = 0;

  @Column(nullable = false)
  private double estimate;

  @Column(nullable = false)
  private LocalDateTime startedAt;

  @Column(nullable = false)
  private LocalDateTime finishedAt;

  @ManyToOne
  @JoinColumn(name = "routine_id", nullable = false)
  private Routine routine;

}
