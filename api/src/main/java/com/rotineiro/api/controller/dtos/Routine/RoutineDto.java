package com.rotineiro.api.controller.dtos.Routine;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rotineiro.api.repository.Enum.PriorityEnum;
import com.rotineiro.api.repository.entities.Routine;
import com.rotineiro.api.repository.entities.Task;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record RoutineDto(

    @JsonProperty("id")
    Integer id,

    @JsonProperty("name")
    String name,

    @JsonProperty("tasks")
    List<Task> tasks,

    @JsonProperty("priority")
    PriorityEnum priority
) {
  public static RoutineDto fromEntity(Routine routine) {
    return new RoutineDto(routine.getId(), routine.getName(), routine.getTasks(), routine.getPriority());
  }
}
