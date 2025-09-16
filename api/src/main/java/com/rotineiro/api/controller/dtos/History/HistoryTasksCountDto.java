package com.rotineiro.api.controller.dtos.History;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HistoryTasksCountDto(
    @JsonProperty("count_tasks")
    Integer countTasks,

    @JsonProperty("completed_tasks")
    Integer completedTasks

) {}
