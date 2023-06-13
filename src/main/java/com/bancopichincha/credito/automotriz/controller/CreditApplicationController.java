package com.bancopichincha.credito.automotriz.controller;

import com.bancopichincha.credito.automotriz.service.CreditApplicationService;
import com.bancopichincha.credito.automotriz.service.dto.CreditApplicationRequest;
import com.bancopichincha.credito.automotriz.service.dto.CreditApplicationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("credit_applications")
public class CreditApplicationController {

  private final CreditApplicationService creditApplicationService;

  @PostMapping
  public ResponseEntity<CreditApplicationResponse> create(
      @RequestBody CreditApplicationRequest creditApplicationRequest) {
    log.info("CreditApplicationController create - " + creditApplicationRequest);

    CreditApplicationResponse creditApplicationResponse =
        creditApplicationService.create(creditApplicationRequest);

    return ResponseEntity.ok().body(creditApplicationResponse);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CreditApplicationResponse> update(
      @PathVariable Long id, @RequestBody CreditApplicationRequest creditApplicationRequest) {
    log.info(
        "CreditApplicationController update - id: "
            + id
            + ",creditApplicationRequest: "
            + creditApplicationRequest);

    CreditApplicationResponse creditApplicationResponse =
        creditApplicationService.update(id, creditApplicationRequest);

    return ResponseEntity.ok().body(creditApplicationResponse);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> delete(@PathVariable Long id) {
    log.info("CreditApplicationController delete - id: " + id);
    creditApplicationService.delete(id);

    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<List<CreditApplicationResponse>> findAll() {
    log.info("CreditApplicationController findAll");
    List<CreditApplicationResponse> creditApplicationResponse = creditApplicationService.findAll();

    return ResponseEntity.ok().body(creditApplicationResponse);
  }
}
