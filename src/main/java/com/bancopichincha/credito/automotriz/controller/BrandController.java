package com.bancopichincha.credito.automotriz.controller;

import com.bancopichincha.credito.automotriz.service.BrandService;
import com.bancopichincha.credito.automotriz.service.dto.BrandResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("brands")
public class BrandController {

  private final BrandService brandService;

  @GetMapping
  public ResponseEntity<List<BrandResponse>> findAll() {
    log.info("getBrands - init");

    List<BrandResponse> brands = brandService.findAll();
    return ResponseEntity.ok(brands);
  }
}
