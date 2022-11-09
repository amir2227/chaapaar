package com.chaapaar.demo.controller;

import com.chaapaar.demo.payload.request.CustomerRequest;
import com.chaapaar.demo.payload.response.CustomerResponse;
import com.chaapaar.demo.service.CustomerService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Arrays;


@WebMvcTest(controllers = CustomerController.class)
class CustomerControllerTest {

    @MockBean
    private CustomerService customerService;
    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("should save customer with status code 201 when POST endpoint - /api/v1/customer")
    public void shouldSaveCustomer() throws Exception{

        ObjectMapper objectMapper = new ObjectMapper();
        CustomerRequest customerRequest = new CustomerRequest("amir","abbasi","some text","asas@as.cs");
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/customer")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerRequest)))
                .andExpect(MockMvcResultMatchers.status().is(201));
    }
    @Test
    @DisplayName("should list all customers with status code 200 when GET endpoint - /api/v1/customer")
    public void shouldListAllCustomer() throws Exception {
        CustomerResponse customerResponse = CustomerResponse.builder()
                .id(1L)
                .firstName("amir")
                .lastName("abbasi")
                .description("some text")
                .email("asas@as.cs")
                .build();
        CustomerResponse customerResponse2 = CustomerResponse.builder()
                .id(2L)
                .firstName("amir2")
                .lastName("reza")
                .description("some text")
                .email("asd@reza.cs")
                .build();

        Mockito.when(customerService.getAll()).thenReturn(Arrays.asList(customerResponse,customerResponse2));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/customer"))
                .andExpect(MockMvcResultMatchers.status().is(200))
                .andExpect(MockMvcResultMatchers.content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].id", Matchers.is(1)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[0].firstName", Matchers.is("amir")))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].id", Matchers.is(2)))
                .andExpect(MockMvcResultMatchers.jsonPath("$[1].firstName", Matchers.is("amir2")));
    }
}