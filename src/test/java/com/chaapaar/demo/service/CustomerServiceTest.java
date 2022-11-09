package com.chaapaar.demo.service;

import com.chaapaar.demo.exception.NotFoundException;
import com.chaapaar.demo.model.Customer;
import com.chaapaar.demo.payload.request.CustomerRequest;
import com.chaapaar.demo.payload.request.EditCustomerRequest;
import com.chaapaar.demo.payload.response.CustomerResponse;
import com.chaapaar.demo.repository.CustomerRepository;
import com.chaapaar.demo.utils.EntityMapper;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @Mock
    private CustomerRepository customerRepository;

    @Test
    @DisplayName("should customer saved in database")
    void shouldCreateCustomer() {
        CustomerService customerService = new CustomerService(customerRepository);
        CustomerRequest customerRequest = new CustomerRequest("amir","abbasi","some text","mail@gmail.com");
        CustomerResponse response = customerService.createCustomer(customerRequest);
        Assertions.assertThat(response.getFirstName()).isEqualTo("amir");
        Assertions.assertThat(response.getEmail()).isEqualTo("mail@gmail.com");
    }


    @Test
    @DisplayName("should find customer by id")
    void shouldFindCustomerById() {
        CustomerService customerService = new CustomerService(customerRepository);
        Customer customer = new Customer(12L,"amir","abbasi","some text","mail@gmail.com");
        CustomerResponse expectedCustomerResponse = CustomerResponse.builder()
                .id(12L)
                .email("mail@gmail.com")
                .firstName("amir")
                .lastName("abbasi")
                .description("some text")
                .build();
        Mockito.when(customerRepository.findById(12L)).thenReturn(Optional.of(customer));
        CustomerResponse actualCustomerResponse = customerService.get(12L);

        Assertions.assertThat(actualCustomerResponse.getId()).isEqualTo(expectedCustomerResponse.getId());
        Assertions.assertThat(actualCustomerResponse.getEmail()).isEqualTo(expectedCustomerResponse.getEmail());
    }
    @Test
    @DisplayName("should throw exception when customer not found")
    void shouldFailWhenCustomerNotFound() {
        CustomerService customerService = new CustomerService(customerRepository);
        assertThatThrownBy(()->{
            customerService.get(12L);
        }).isInstanceOf(NotFoundException.class)
                .hasMessage("Customer with id 12 not found");
    }


}