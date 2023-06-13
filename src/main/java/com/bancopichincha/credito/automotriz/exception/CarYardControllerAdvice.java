package com.bancopichincha.credito.automotriz.exception;

import com.bancopichincha.credito.automotriz.service.dto.ApiErrorResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
public class CarYardControllerAdvice {

  @ExceptionHandler(CarYardNotFoundException.class)
  public ResponseEntity<ApiErrorResponseDto> handleException(CarYardNotFoundException exception) {
    log.info("CarYardNotFoundException - handleException : " + exception.getMessage());
    var errorDto = ApiErrorResponseDto.generateDto(exception.getMessage());

    return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorDto);
  }

  @ExceptionHandler({CarYardAlreadyDisabledException.class, CarYardWithOperationsException.class})
  public ResponseEntity<ApiErrorResponseDto> handleException(RuntimeException exception) {
    log.info("CarYardControllerAdviceImpl - handleException : " + exception.getMessage());
    var errorDto = ApiErrorResponseDto.generateDto(exception.getMessage());

    return ResponseEntity.status(HttpStatus.CONFLICT).body(errorDto);
  }
}
