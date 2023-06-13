package com.bancopichincha.credito.automotriz.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.bancopichincha.credito.automotriz.domain.Customer;
import com.bancopichincha.credito.automotriz.repository.CustomerRepository;
import com.bancopichincha.credito.automotriz.service.mapper.CustomerMapper;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class CustomerServiceImplTest {
  @Mock private CustomerRepository customerRepository;
  @Mock private CustomerMapper customerMapper;
  @InjectMocks private CustomerServiceImpl customerService;

  @Test
  public void shouldReturnCustomerWhenFoundOnGetByIdentification() {
    Customer customer = createDummyCustomer();

    when(customerRepository.findByIdentification(any())).thenReturn(Optional.of(customer));

    Optional<Customer> result = customerService.getByIdentification(any());

    assertTrue(result.isPresent());
    assertEquals(customer, result.get());
  }

  @Test
  public void shouldReturnEmptyOptionalWhenNotFoundOnGetByIdentification() {
    when(customerRepository.findByIdentification(any())).thenReturn(Optional.empty());
    Optional<Customer> result = customerService.getByIdentification(any());
    assertTrue(result.isEmpty());
  }

  @Test
  public void shouldSaveOnSaveCustomers() {
    List<Customer> customers = Collections.singletonList(createDummyCustomer());

    when(customerMapper.mapToEntity(anyList())).thenReturn(customers);
    when(customerRepository.existsByIdentification(any())).thenReturn(false);

    customerService.saveCustomers(anyList());

    verify(customerRepository).saveAll(anyList());
  }

  private Customer createDummyCustomer() {
    return Customer.builder().build();
  }
}
