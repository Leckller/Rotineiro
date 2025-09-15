package com.rotineiro.api.controller;

import com.rotineiro.api.controller.dtos.History.CompleteHistoryDto;
import com.rotineiro.api.controller.dtos.Routine.RoutineDto;
import com.rotineiro.api.security.SecurityConfig;
import com.rotineiro.api.service.HistoryService;
import com.rotineiro.api.utils.DefaultResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/history")
@Tag(name = "Histórico", description = "Controller para o gerenciamento das funções de histórico")
@SecurityRequirement(name = SecurityConfig.SECURITY)
public class HistoryController {

  private final HistoryService historyService;

  @Autowired
  public HistoryController(HistoryService historyService) {
    this.historyService = historyService;
  }

  @GetMapping("{start}/{end}")
  @Operation(summary = "Carrega o Histórico das rotinas e tarefas")
  public ResponseEntity<DefaultResponse<CompleteHistoryDto>> getCompleteHistory(
      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime start,
      @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime end
  ) {

    String username = SecurityContextHolder.getContext().getAuthentication().getName();
    CompleteHistoryDto historyDto = historyService.completeHistory(username, start, end);

    DefaultResponse<CompleteHistoryDto> response = new DefaultResponse<CompleteHistoryDto>();
    response.setMessage("Um histórico mágico!");
    response.setResult(historyDto);

    return ResponseEntity.status(HttpStatus.OK).body(response);

  }

}
