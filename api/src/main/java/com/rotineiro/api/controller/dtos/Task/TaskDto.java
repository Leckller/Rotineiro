package com.rotineiro.api.controller.dtos.Task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.rotineiro.api.repository.entities.Task;

@JsonIgnoreProperties(ignoreUnknown = true)
public record TaskDto(

    @JsonProperty("id")
    Integer id,

    @JsonProperty("name")
    String name,

    @JsonProperty("estimate")
    Double estimate,

    @JsonProperty("estimate")
    Boolean completed

) {

  public static TaskDto fromEntity (Task task) {
    return new TaskDto(task.getId(), task.getName(), task.getEstimate(), task.getCompleted());
  }

}
