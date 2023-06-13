package com.bancopichincha.credito.automotriz.exception;

import com.bancopichincha.credito.automotriz.service.dto.ApiErrorResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@Slf4j
@ControllerAdvice
@RequiredArgsConstructor
public class ExceptionHandlingControllerAdvice {

  @ExceptionHandler(
      value = {DataIntegrityViolationException.class, ConstraintViolationException.class})
  public ResponseEntity<ApiErrorResponseDto> handleException(Exception exception) {
    log.info("ExceptionHandlingControllerAdvice - handleException : " + exception.getMessage());
    var responseError = ApiErrorResponseDto.generateDto(exception.getMessage());

    return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(responseError);
  }
}
