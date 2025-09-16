package com.rotineiro.api.controller.dtos.History;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AmountTaskUseDto (

    @JsonProperty("id")
    Integer id,

    @JsonProperty("task_name")
    String taskName,

    @JsonProperty("uses")
    Integer uses

) { }
