package com.rotineiro.api.controller.dtos.Routine;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rotineiro.api.repository.Enum.PriorityEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;

public record EditRoutineDto(
    @Size(min=3, max = 20, message = "O seu nome deve ter pelo menos 3 letras e no máximo 20.")
    @JsonProperty("name")
    String name,

    @JsonProperty("priority")
    PriorityEnum priority,

    @JsonProperty("description")
    String description

) {
}
