package com.rotineiro.api.controller.dtos.Task;

import com.fasterxml.jackson.annotation.JsonProperty;

public record EditTaskDto(

    @JsonProperty("name")
    String name,

    @JsonProperty("estimate")
    Double estimate

) { }
