package com.chaapaar.demo.service;

import com.chaapaar.demo.exception.BadRequestException;
import com.chaapaar.demo.exception.NotFoundException;
import com.chaapaar.demo.model.Product;
import com.chaapaar.demo.payload.request.EditProductRequest;
import com.chaapaar.demo.payload.request.ProductRequest;
import com.chaapaar.demo.payload.response.ProductResponse;
import com.chaapaar.demo.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public ProductResponse createProduct(ProductRequest request){
        Product product = new Product();
        product.setName(request.getName());
        product.setPrice(request.getPrice());
        productRepository.saveAndFlush(product);
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
    public ProductResponse update(Long id, EditProductRequest request){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("Product with id %d not found",id)));
        if(request.getName() != null) product.setName(request.getName());
        if(request.getPrice() != null) product.setPrice(request.getPrice());
        productRepository.saveAndFlush(product);
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
    public List<ProductResponse> getAll(){
        List<Product> products = productRepository.findAll();
        return products.stream().map(product ->
            ProductResponse.builder()
                .name(product.getName())
                .id(product.getId())
                .price(product.getPrice()).build()
        ).toList();
    }
    public ProductResponse get(Long id){
        Product product = productRepository.findById(id)
                .orElseThrow(()-> new NotFoundException(String.format("Product with id %d not found",id)));
        return ProductResponse.builder()
                .id(product.getId())
                .name(product.getName())
                .price(product.getPrice())
                .build();
    }
    public String delete(Long id){
        // is present check
        this.get(id);
        try {
            productRepository.deleteById(id);
        }catch (Exception e){
         throw new BadRequestException(e.getMessage());
        }
        return String.format("Product with id %d successfully deleted.", id);
    }
}
