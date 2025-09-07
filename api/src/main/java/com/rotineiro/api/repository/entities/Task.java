package com.rotineiro.api.repository.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tasks")
public class Task {

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

  @OneToMany(mappedBy = "task")
  private List<TaskHistory> taskHistories;

  @ManyToMany
  @JoinTable(
      name = "task_routines",
      joinColumns = @JoinColumn(name = "task_id"),
      inverseJoinColumns = @JoinColumn(name = "routine_id")
  )
  private Set<Routine> routines = new HashSet<>();

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

}
