package com.bancopichincha.credito.automotriz.configuration;

import static com.bancopichincha.credito.automotriz.domain.enums.CustomerCreditSubject.CREDIT_SUBJECT;
import static com.bancopichincha.credito.automotriz.domain.enums.CustomerCreditSubject.NO_CREDIT_SUBJECT;

import com.bancopichincha.credito.automotriz.domain.enums.CustomerCreditSubject;
import com.bancopichincha.credito.automotriz.service.CustomerService;
import com.bancopichincha.credito.automotriz.service.dto.CustomerDto;
import com.bancopichincha.credito.automotriz.util.CsvReaderUtil;
import com.bancopichincha.credito.automotriz.util.CustomerLoaderUtil;
import com.bancopichincha.credito.automotriz.util.DateUtil;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CustomerLoader {

  private final CustomerService customerService;

  @Value("${csv.files.customers}")
  private String csvFinalPath;

  public void loadCustomerData() {
    try {
      List<String[]> data = CsvReaderUtil.loadCsvData(csvFinalPath, true);
      List<CustomerDto> customerDtos = mapDataToCustomerDto(data);
      customerService.saveCustomers(customerDtos);
    } catch (Exception e) {
      e.printStackTrace();
    }
  }

  private List<CustomerDto> mapDataToCustomerDto(List<String[]> csvData) {
    List<CustomerDto> customers = new ArrayList<>();

    for (String[] data : csvData) {
      CustomerDto customerDto = mapDataToCustomerDto(data);
      log.info("customerDto : " + customerDto);

      customers.add(customerDto);
    }

    return customers;
  }

  private CustomerDto mapDataToCustomerDto(String[] data) {

    return CustomerDto.builder()
        .identification(data[0])
        .name(data[1].trim())
        .lastname(data[2].trim())
        .age(Integer.valueOf(data[3]))
        .birthday(DateUtil.parseToDate(data[4]))
        .address(data[5])
        .phone(data[6])
        .maritalStatus(data[7])
        .partnerIdentification(data[8])
        .partnerName(data[9])
        .creditSubject(defineCreditSubject(data[10]))
        .build();
  }

  private CustomerCreditSubject defineCreditSubject(String creditSubjectData) {
    return creditSubjectData.equals(CustomerLoaderUtil.CREDIT_SUBJECT)
        ? CREDIT_SUBJECT
        : NO_CREDIT_SUBJECT;
  }
}
