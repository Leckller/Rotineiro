package com.rotineiro.api.controller.dtos.History;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public record SequencyDto(
    @JsonProperty("best_sequency")
    Integer bestSequency,

    @JsonProperty("actual_sequency")
    Integer actualSequency
) {
}
