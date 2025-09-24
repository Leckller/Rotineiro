package com.rotineiro.api.controller.dtos.History;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record HistoryTasksCountDto(
    @JsonProperty("total")
    Integer total,

    @JsonProperty("completed")
    Integer completed

) {}
