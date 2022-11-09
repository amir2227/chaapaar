package com.chaapaar.demo.payload.response;


import lombok.Builder;
import lombok.Data;
import java.util.List;

@Data
@Builder
public class OrderResponseList {
    private CustomerResponse customer;
    private Double totalPrice;
    private List<OrderResponse> orders;
}
