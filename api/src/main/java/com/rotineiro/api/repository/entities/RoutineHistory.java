package com.rotineiro.api.repository.entities;

import com.rotineiro.api.repository.Enum.PriorityEnum;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
  private String name;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private PriorityEnum priority = PriorityEnum.LOW;

  @Column(nullable = false)
  private String description;

  private LocalDateTime startedAt;

  private LocalDateTime finishedAt;

  @CreationTimestamp
  private LocalDateTime createdAt;

  @UpdateTimestamp
  private LocalDateTime updatedAt;

  @ManyToOne
  @JoinColumn(name = "routine_id", nullable = false)
  private Routine routine;

  @OneToMany(mappedBy = "routineHistory")
  private List<TaskHistory> tasks = new ArrayList<TaskHistory>();

  @ManyToOne
  @JoinColumn(name = "user_id", nullable = false)
  private User user;



}
