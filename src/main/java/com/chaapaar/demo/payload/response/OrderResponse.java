package com.chaapaar.demo.payload.response;


import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private ProductResponse product;
    private Integer count;
}
