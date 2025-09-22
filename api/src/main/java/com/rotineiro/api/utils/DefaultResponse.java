package com.rotineiro.api.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@JsonIgnoreProperties(ignoreUnknown = true)
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class DefaultResponse<GenericResponse> {
  @JsonProperty("message")
  String message;

  @JsonProperty("response")
  GenericResponse result;
}
