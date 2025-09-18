package com.rotineiro.api.controller.dtos.History;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record DayUseDto(

    @JsonProperty("date")
    LocalDate date,

    @JsonProperty("entities")
    List<AmountUseDto> entities

) {
}
