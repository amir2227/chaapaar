package com.chaapaar.demo.utils;

import com.chaapaar.demo.model.Customer;
import com.chaapaar.demo.model.Order;
import com.chaapaar.demo.model.Product;
import com.chaapaar.demo.payload.response.CustomerResponse;
import com.chaapaar.demo.payload.response.OrderResponse;
import com.chaapaar.demo.payload.response.ProductResponse;

public class EntityMapper {

    public static CustomerResponse mapToDto(Customer customer){
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getLastName())
                .description(customer.getDescription())
                .email(customer.getEmail())
                .build();
    }
    public static ProductResponse mapToDto(Product product){
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
    public static OrderResponse mapToDto(Order order){
        return OrderResponse.builder()
                .id(order.getId())
                .count(order.getCount())
                .product(ProductResponse.builder()
                        .id(order.getProduct().getId())
                        .name(order.getProduct().getName())
                        .price(order.getProduct().getPrice())
                        .build())
                .build();
    }
}
