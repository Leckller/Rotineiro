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
@Table(name = "task_histories")
public class TaskHistory {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Column(nullable = false)
  private boolean completed = false;

  @Column(nullable = false)
  private double estimate;

  @Column(nullable = false)
  private LocalDateTime startedAt;

  @Column(nullable = false)
  private LocalDateTime finishedAt;

  @ManyToOne
  @JoinColumn(name = "task_id", nullable = false)
  private Task task;

}
