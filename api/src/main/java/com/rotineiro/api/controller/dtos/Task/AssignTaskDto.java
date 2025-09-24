package com.rotineiro.api.controller.dtos.Task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AssignTaskDto(
    @JsonProperty("tasks")
    List<Integer> tasks
) {
}
