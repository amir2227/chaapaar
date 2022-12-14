package com.chaapaar.demo.service;

import com.chaapaar.demo.exception.BadRequestException;
import com.chaapaar.demo.exception.NotFoundException;
import com.chaapaar.demo.model.Customer;
import com.chaapaar.demo.model.Order;
import com.chaapaar.demo.model.Product;
import com.chaapaar.demo.payload.request.OrderRequest;
import com.chaapaar.demo.payload.request.EditOrderRequest;
import com.chaapaar.demo.payload.response.CustomerResponse;
import com.chaapaar.demo.payload.response.OrderResponse;
import com.chaapaar.demo.payload.response.OrderResponseList;
import com.chaapaar.demo.payload.response.ProductResponse;
import com.chaapaar.demo.repository.OrderRepository;
import com.chaapaar.demo.utils.EntityMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    private static final DecimalFormat df = new DecimalFormat("0.000");

    public OrderResponseList createOrder(List<OrderRequest> request, Long customerId){
        if(request.isEmpty()) throw new BadRequestException("order list is empty");
        List<OrderResponse> orderResponses = new ArrayList<>();
        Double totalPrice = 0d;
        // check customer
        Customer customer = customerService.getEntity(customerId);
        for(OrderRequest orderRequest : request){
           Product product = productService.getEntity(orderRequest.getProductId());
           totalPrice += product.getPrice() * orderRequest.getCount();
           Order order = new Order(null,orderRequest.getCount(),customer,product);
           orderRepository.saveAndFlush(order);
           orderResponses.add(EntityMapper.mapToDto(order));
        }
        return OrderResponseList.builder()
                .orders(orderResponses)
                .totalPrice(Double.valueOf(df.format(totalPrice)))
                .customer(EntityMapper.mapToDto(customer))
                .build();
    }
    public Order getEntity(Long id){
        return orderRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("Order with id %d not found",id)));
    }
    public OrderResponse get(Long id){
        Order order = this.getEntity(id);
        return EntityMapper.mapToDto(order);
    }
    public OrderResponse update(Long id,Long customerId, EditOrderRequest request){
        Order order = this.getEntity(id);
        if(order.getCustomer().getId() != customerId)
            throw new BadRequestException("this is not your order");
        if(request.getCount() != null) order.setCount(request.getCount());
        if(request.getProductId() != null){
            if(request.getCount() == null) throw new BadRequestException("count required");
            Product product = productService.getEntity(request.getProductId());
            order.setProduct(product);
        }
        orderRepository.saveAndFlush(order);
        return EntityMapper.mapToDto(order);
    }
    public OrderResponseList getCustomerOrders(Long customerId){
        CustomerResponse customerResponse = customerService.get(customerId);
        List<Order> orders = orderRepository.findByCustomer_id(customerId);
        List<OrderResponse> orderResponses = new ArrayList<>();
        Double totalPrice = 0d;
        for(Order order : orders){
          orderResponses.add(EntityMapper.mapToDto(order));
          totalPrice += order.getCount() * order.getProduct().getPrice();
        }
        return OrderResponseList.builder()
                .customer(customerResponse)
                .totalPrice(Double.valueOf(df.format(totalPrice)))
                .orders(orderResponses)
                .build();
    }

    public String delete(Long id){
        // is present check
        this.get(id);
        try {
            orderRepository.deleteById(id);
        }catch (Exception e){
         throw new BadRequestException(e.getMessage());
        }
        return String.format("Order with id %d successfully deleted.", id);
    }
}
