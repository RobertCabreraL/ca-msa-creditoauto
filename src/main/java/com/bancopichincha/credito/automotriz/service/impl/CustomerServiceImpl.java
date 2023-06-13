package com.bancopichincha.credito.automotriz.service.impl;

import com.bancopichincha.credito.automotriz.domain.Customer;
import com.bancopichincha.credito.automotriz.repository.CustomerRepository;
import com.bancopichincha.credito.automotriz.service.CustomerService;
import com.bancopichincha.credito.automotriz.service.dto.CustomerDto;
import com.bancopichincha.credito.automotriz.service.mapper.CustomerMapper;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomerServiceImpl implements CustomerService {

  private final CustomerRepository customerRepository;
  private final CustomerMapper customerMapper;

  @Override
  public void saveCustomers(List<CustomerDto> customerDtos) {
    List<Customer> customers = customerMapper.mapToEntity(customerDtos);
    List<Customer> customersToSave =
        customers.stream()
            .filter(customer -> !alreadyExists(customer))
            .collect(Collectors.toList());

    customerRepository.saveAll(customersToSave);
  }

  private boolean alreadyExists(Customer customer) {
    return customerRepository.existsByIdentification(customer.getIdentification());
  }

  @Override
  public Optional<Customer> getByIdentification(String identification) {
    return customerRepository.findByIdentification(identification);
  }
}
