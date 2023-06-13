package com.bancopichincha.credito.automotriz.service.dto;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.bancopichincha.credito.automotriz.domain.enums.CustomerCreditSubject;
import java.util.Date;
import org.junit.jupiter.api.Test;

class CustomerDtoTest {

  @Test
  void testCustomerDto() {
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

    CustomerDto customerDto =
        CustomerDto.builder()
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

    assertEquals(identification, customerDto.getIdentification());
    assertEquals(name, customerDto.getName());
    assertEquals(lastname, customerDto.getLastname());
    assertEquals(age, customerDto.getAge());
    assertEquals(birthday, customerDto.getBirthday());
    assertEquals(address, customerDto.getAddress());
    assertEquals(phone, customerDto.getPhone());
    assertEquals(maritalStatus, customerDto.getMaritalStatus());
    assertEquals(partnerIdentification, customerDto.getPartnerIdentification());
    assertEquals(partnerName, customerDto.getPartnerName());
    assertEquals(creditSubject, customerDto.getCreditSubject());
  }
}
