package com.rotineiro.api.controller.dtos.Task;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@JsonIgnoreProperties(ignoreUnknown = true)
public record CreateTaskDto(

    @NotBlank(message = "O Nome é obrigatório")
    @Size(min = 3, max = 20, message = "O seu nome deve ter pelo menos 3 letras e no máximo 20.")
    @JsonProperty("name")
    String name,

    @NotNull(message = "É necessário uma estimativa para conclusão da tarefa")
    @JsonProperty("estimate")
    Double estimate,

    @JsonProperty("routine_id")
    Integer routineId

) {
}
