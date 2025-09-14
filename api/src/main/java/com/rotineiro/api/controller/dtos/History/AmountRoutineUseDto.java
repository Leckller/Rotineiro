package com.rotineiro.api.controller.dtos.History;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record AmountRoutineUseDto(

    @JsonProperty("id")
    Integer id,

    @JsonProperty("routine_name")
    String routineName,

    @JsonProperty("uses")
    Integer uses

) {
}
