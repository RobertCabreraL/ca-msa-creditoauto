package com.bancopichincha.credito.automotriz.service;

import com.bancopichincha.credito.automotriz.domain.Customer;
import com.bancopichincha.credito.automotriz.service.dto.CustomerDto;
import java.util.List;
import java.util.Optional;

public interface CustomerService {
  void saveCustomers(List<CustomerDto> customerDtos);

  Optional<Customer> getByIdentification(String customerIdentification);
}
