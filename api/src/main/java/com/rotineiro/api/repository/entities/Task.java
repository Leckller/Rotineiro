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

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @OneToMany(mappedBy = "task")
  private List<TaskHistory> taskHistories;

  @ManyToOne
  @JoinColumn(name = "routine_id", nullable = false)
  private Routine routine;

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;

}
