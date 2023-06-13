package com.bancopichincha.credito.automotriz.exception;

import com.bancopichincha.credito.automotriz.service.dto.ApiErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class VehicleControllerAdvice {

  @ExceptionHandler({VehicleNotFoundException.class, BrandNotFoundException.class})
  public ResponseEntity<ApiErrorResponseDto> handleExceptionNotFound(RuntimeException exception) {
    log.info("VehicleControllerAdvice - handleException NOT_FOUND: " + exception.getMessage());
    var errorDto = ApiErrorResponseDto.generateDto(exception.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
  }

  @ExceptionHandler({
    VehicleDuplicatedPlateException.class,
    OperationNotAllowedException.class,
    VehicleCreditApplicationRegisteredException.class
  })
  public ResponseEntity<ApiErrorResponseDto> handleExceptionConflict(RuntimeException exception) {
    log.info("VehicleControllerAdvice - handleException CONFLICT: " + exception.getMessage());
    var errorDto = ApiErrorResponseDto.generateDto(exception.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDto);
  }
}
