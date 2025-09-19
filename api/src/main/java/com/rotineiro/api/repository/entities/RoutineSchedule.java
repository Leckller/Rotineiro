package com.rotineiro.api.repository.entities;

import jakarta.persistence.*;
import lombok.*;

import java.time.DayOfWeek;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "routine_schedule")
public class RoutineSchedule {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Integer id;

  @Enumerated(EnumType.STRING)
  @Column(nullable = false)
  private DayOfWeek dayOfWeek;

  @ManyToOne
  @JoinColumn(name = "routine_id", nullable = false)
  private Routine routine;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;
}
