package com.rotineiro.api.controller.dtos.History;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CompleteHistoryDto (
    @JsonProperty("most_used_routine")
    AmountRoutineUseDto mostUsedRoutine,

    @JsonProperty("amount_of_routine_use")
    List<AmountRoutineUseDto> amountOfRoutineUse,

    @JsonProperty("amount_of_task_use")
    List<AmountTaskUseDto> amountOfTaskUse
) {}
