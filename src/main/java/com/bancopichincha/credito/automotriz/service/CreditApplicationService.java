package com.bancopichincha.credito.automotriz.service;

import com.bancopichincha.credito.automotriz.service.dto.CreditApplicationRequest;
import com.bancopichincha.credito.automotriz.service.dto.CreditApplicationResponse;
import java.util.List;

public interface CreditApplicationService {
  CreditApplicationResponse create(CreditApplicationRequest creditApplicationRequest);

  CreditApplicationResponse update(Long id, CreditApplicationRequest creditApplicationRequest);

  void delete(Long id);

  List<CreditApplicationResponse> findAll();
}
