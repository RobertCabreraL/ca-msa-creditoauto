package com.bancopichincha.credito.automotriz.controller;

import com.bancopichincha.credito.automotriz.service.SaleExecutiveService;
import com.bancopichincha.credito.automotriz.service.dto.SaleExecutiveResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("sale_executives")
public class SaleExecutiveController {

  private final SaleExecutiveService saleExecutiveService;

  @GetMapping
  public ResponseEntity<List<SaleExecutiveResponse>> findAll() {
    log.info("findAll - init");

    List<SaleExecutiveResponse> saleExecutives = saleExecutiveService.findAll();
    return ResponseEntity.ok(saleExecutives);
  }

  @GetMapping("/find")
  public ResponseEntity<List<SaleExecutiveResponse>> findAllBy(@RequestParam Long carYardId) {
    log.info("findAll - init");

    List<SaleExecutiveResponse> saleExecutives = saleExecutiveService.findAllBy(carYardId);
    return ResponseEntity.ok(saleExecutives);
  }
}
