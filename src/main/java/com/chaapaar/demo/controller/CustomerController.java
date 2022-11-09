package com.chaapaar.demo.controller;

import com.chaapaar.demo.exception.HandleValidationExceptions;
import com.chaapaar.demo.payload.request.EditCustomerRequest;
import com.chaapaar.demo.payload.request.CustomerRequest;
import com.chaapaar.demo.payload.response.CustomerResponse;
import com.chaapaar.demo.service.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customer")
@RequiredArgsConstructor
public class CustomerController extends HandleValidationExceptions {
    private final CustomerService customerService;

    @PostMapping
    public ResponseEntity<CustomerResponse> create(@Valid @RequestBody CustomerRequest request){
        CustomerResponse response = customerService.createCustomer(request);
        URI uri = URI.create("/api/v1/customer");
        return ResponseEntity.created(uri).body(response);
    }
    @GetMapping
    public ResponseEntity<List<CustomerResponse>> getAll(){
        return ResponseEntity.ok(customerService.getAll());
    }
    @GetMapping(path = "{id}")
    public ResponseEntity<CustomerResponse> get(@PathVariable("id") Long id){
        return ResponseEntity.ok(customerService.get(id));
    }
    @PatchMapping(path = "{id}")
    public ResponseEntity<CustomerResponse> edit(@PathVariable("id") Long id,@Valid @RequestBody EditCustomerRequest request){
        return ResponseEntity.ok(customerService.update(id,request));
    }
    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(customerService.delete(id));
    }
}
