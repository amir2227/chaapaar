package com.chaapaar.demo.controller;

import com.chaapaar.demo.exception.HandleValidationExceptions;
import com.chaapaar.demo.payload.request.EditOrderRequest;
import com.chaapaar.demo.payload.request.OrderRequest;
import com.chaapaar.demo.payload.response.OrderResponse;
import com.chaapaar.demo.payload.response.OrderResponseList;
import com.chaapaar.demo.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/order")
@RequiredArgsConstructor
public class OrderController extends HandleValidationExceptions {
    private final OrderService orderService;

    @PostMapping(path = "{customerId}")
    public ResponseEntity<OrderResponseList> create(@PathVariable("customerId") Long customerId, @Valid @RequestBody List<OrderRequest> request){
        OrderResponseList response = orderService.createOrder(request,customerId);
        URI uri = URI.create("/api/v1/order/{customerId}");
        return ResponseEntity.created(uri).body(response);
    }
    @GetMapping(path = "{customerId}/all")
    public ResponseEntity<OrderResponseList> getAllCustomerOrders(@PathVariable("customerId") Long customerId){
        return ResponseEntity.ok(orderService.getCustomerOrders(customerId));
    }
    @GetMapping(path = "{id}")
    public ResponseEntity<OrderResponse> get(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderService.get(id));
    }
    @PatchMapping(path = "{id}/{customerId}")
    public ResponseEntity<OrderResponse> edit(@PathVariable("id") Long id,
                                                 @PathVariable("customerId") Long customerId,
                                                 @Valid @RequestBody EditOrderRequest request){
        return ResponseEntity.ok(orderService.update(id,customerId,request));
    }
    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(orderService.delete(id));
    }
}
