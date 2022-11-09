package com.chaapaar.demo.service;

import com.chaapaar.demo.exception.BadRequestException;
import com.chaapaar.demo.exception.NotFoundException;
import com.chaapaar.demo.model.Customer;
import com.chaapaar.demo.payload.request.EditCustomerRequest;
import com.chaapaar.demo.payload.request.CustomerRequest;
import com.chaapaar.demo.payload.response.CustomerResponse;
import com.chaapaar.demo.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerResponse createCustomer(CustomerRequest request){
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setDescription(request.getDescription());
        customer.setEmail(request.getEmail());
        customerRepository.saveAndFlush(customer);
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .description(customer.getDescription())
                .email(customer.getEmail())
                .build();
    }
    public CustomerResponse update(Long id, EditCustomerRequest request){
        Customer customer = this.getEntity(id);
        if(request.getFirstName() != null) customer.setFirstName(request.getFirstName());
        if(request.getLastName() != null) customer.setLastName(request.getLastName());
        if(request.getDescription() != null) customer.setDescription(request.getDescription());
        if(request.getEmail() != null) customer.setEmail(request.getEmail());
        customerRepository.saveAndFlush(customer);
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .description(customer.getDescription())
                .email(customer.getEmail())
                .build();
    }
    public List<CustomerResponse> getAll(){
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(customer ->
                CustomerResponse.builder()
                        .id(customer.getId())
                        .firstName(customer.getFirstName())
                        .lastName(customer.getLastName())
                        .description(customer.getDescription())
                        .email(customer.getEmail())
                        .build()
                ).toList();
    }
    public Customer getEntity(Long id){
        return customerRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("Customer with id %d not found",id)));
    }
    public CustomerResponse get(Long id){
        Customer customer = this.getEntity(id);
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .description(customer.getDescription())
                .email(customer.getEmail())
                .build();
    }
    public String delete(Long id){
        // is present check
        this.get(id);
        try {
            customerRepository.deleteById(id);
        }catch (Exception e){
         throw new BadRequestException(e.getMessage());
        }
        return String.format("Customer with id %d successfully deleted.", id);
    }
}
