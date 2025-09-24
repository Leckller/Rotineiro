package com.rotineiro.api.controller.dtos.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.rotineiro.api.repository.entities.User;

public record UserDto (

    @JsonProperty("name")
    String name,
    @JsonProperty("id")
    Integer id

) {

  public static UserDto fromEntity(User user) {

    return new UserDto(user.getName(), user.getId());

  }

}
