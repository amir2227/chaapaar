package com.chaapaar.demo.service;

import com.chaapaar.demo.exception.BadRequestException;
import com.chaapaar.demo.exception.NotFoundException;
import com.chaapaar.demo.model.Customer;
import com.chaapaar.demo.payload.request.EditCustomerRequest;
import com.chaapaar.demo.payload.request.CustomerRequest;
import com.chaapaar.demo.payload.response.CustomerResponse;
import com.chaapaar.demo.repository.CustomerRepository;
import com.chaapaar.demo.utils.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerResponse createCustomer(CustomerRequest request){
        Customer customer = new Customer();
        customer.setFirstName(request.getFirstName());
        customer.setLastName(request.getLastName());
        customer.setDescription(request.getDescription());
        customer.setEmail(request.getEmail());
        customerRepository.saveAndFlush(customer);
        return EntityMapper.mapToDto(customer);
    }
    public CustomerResponse update(Long id, EditCustomerRequest request){
        Customer customer = this.getEntity(id);
        if(request.getFirstName() != null) customer.setFirstName(request.getFirstName());
        if(request.getLastName() != null) customer.setLastName(request.getLastName());
        if(request.getDescription() != null) customer.setDescription(request.getDescription());
        if(request.getEmail() != null) customer.setEmail(request.getEmail());
        customerRepository.saveAndFlush(customer);
        return EntityMapper.mapToDto(customer);
    }
    @Transactional(readOnly = true)
    public List<CustomerResponse> getAll(){
        List<Customer> customers = customerRepository.findAll();
        return customers.stream().map(EntityMapper::mapToDto
        ).toList();
    }
    @Transactional(readOnly = true)
    public Customer getEntity(Long id){
        return customerRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("Customer with id %d not found",id)));
    }
    @Transactional(readOnly = true)
    public CustomerResponse get(Long id){
        Customer customer = this.getEntity(id);
        return EntityMapper.mapToDto(customer);
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
