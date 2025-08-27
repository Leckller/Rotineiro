package com.rotineiro.api.controller.dtos.Task;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.List;

public record CreateTaskDto(

    @NotBlank(message = "O Nome é obrigatório")
    @Size(min = 3, max = 20, message = "O seu nome deve ter pelo menos 3 letras e no máximo 20.")
    @JsonProperty("name")
    String name,

    @NotNull(message = "É necessário uma estimativa para conclusão da tarefa")
    @JsonProperty("estimate")
    Double estimate,

    @NotNull(message = "É necessário o ID da rotina")
    @JsonProperty("routine_id")
    Integer routineId,

    @NotEmpty(message = "É necessário passar uma lista com um ou mais ids de tarefas")
    @Size(min = 1, max = 10, message = "É necessário pelo menos um id da tarefa, com limite de 10")
    @JsonProperty("tasks")
    List<Integer> taskId

) {
}
