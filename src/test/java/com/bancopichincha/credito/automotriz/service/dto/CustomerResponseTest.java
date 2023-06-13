package com.bancopichincha.credito.automotriz.service.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bancopichincha.credito.automotriz.domain.enums.CustomerCreditSubject;
import java.util.Date;
import org.junit.jupiter.api.Test;

class CustomerResponseTest {

  @Test
  void testCustomerResponse() {
    String identification = "123456789";
    String name = "John";
    String lastname = "Doe";
    Integer age = 30;
    Date birthday = new Date();
    String address = "Test Address";
    String phone = "123456789";
    String maritalStatus = "Single";
    String partnerIdentification = "987654321";
    String partnerName = "Jane Doe";
    CustomerCreditSubject creditSubject = CustomerCreditSubject.CREDIT_SUBJECT;

    CustomerResponse customerResponse =
        CustomerResponse.builder()
            .identification(identification)
            .name(name)
            .lastname(lastname)
            .age(age)
            .birthday(birthday)
            .address(address)
            .phone(phone)
            .maritalStatus(maritalStatus)
            .partnerIdentification(partnerIdentification)
            .partnerName(partnerName)
            .creditSubject(creditSubject)
            .build();

    assertEquals(identification, customerResponse.getIdentification());
    assertEquals(name, customerResponse.getName());
    assertEquals(lastname, customerResponse.getLastname());
    assertEquals(age, customerResponse.getAge());
    assertEquals(birthday, customerResponse.getBirthday());
    assertEquals(address, customerResponse.getAddress());
    assertEquals(phone, customerResponse.getPhone());
    assertEquals(maritalStatus, customerResponse.getMaritalStatus());
    assertEquals(partnerIdentification, customerResponse.getPartnerIdentification());
    assertEquals(partnerName, customerResponse.getPartnerName());
    assertEquals(creditSubject, customerResponse.getCreditSubject());
  }
}
