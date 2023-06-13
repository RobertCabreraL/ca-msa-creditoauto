package com.bancopichincha.credito.automotriz.controller;

import com.bancopichincha.credito.automotriz.service.CustomerAssignmentService;
import com.bancopichincha.credito.automotriz.service.dto.CustomerAssignmentRequest;
import com.bancopichincha.credito.automotriz.service.dto.CustomerAssignmentResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("customer_assignments")
public class CustomerAssignmentController {

  private final CustomerAssignmentService customerAssignmentService;

  @PostMapping
  public ResponseEntity<CustomerAssignmentResponse> create(
      @RequestBody CustomerAssignmentRequest customerAssignmentRequest) {
    log.info("CustomerAssignmentController create - " + customerAssignmentRequest);

    CustomerAssignmentResponse customerAssignmentResponse =
        customerAssignmentService.create(customerAssignmentRequest);

    return ResponseEntity.ok().body(customerAssignmentResponse);
  }

  @PutMapping("/{id}")
  public ResponseEntity<CustomerAssignmentResponse> update(
      @PathVariable Long id, @RequestBody CustomerAssignmentRequest assignmentRequest) {
    log.info(
        "CustomerAssignmentController update - id: "
            + id
            + "assignmentRequest:"
            + assignmentRequest);

    CustomerAssignmentResponse customerAssignmentResponse =
        customerAssignmentService.update(id, assignmentRequest);

    return ResponseEntity.ok().body(customerAssignmentResponse);
  }

  @DeleteMapping("/{id}")
  public ResponseEntity<Boolean> delete(@PathVariable Long id) {
    log.info("CustomerAssignmentController delete - id: " + id);

    customerAssignmentService.delete(id);

    return ResponseEntity.ok().build();
  }

  @GetMapping
  public ResponseEntity<List<CustomerAssignmentResponse>> findBy(
      @RequestParam(required = false) Long customerId,
      @RequestParam(required = false) Long carYardId) {

    List<CustomerAssignmentResponse> customerAssignments =
        customerAssignmentService.findBy(customerId, carYardId);

    return ResponseEntity.ok(customerAssignments);
  }
}
