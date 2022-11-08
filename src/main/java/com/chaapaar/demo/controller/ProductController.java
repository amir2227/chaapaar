package com.chaapaar.demo.controller;

import com.chaapaar.demo.exception.HandleValidationExceptions;
import com.chaapaar.demo.payload.request.EditProductRequest;
import com.chaapaar.demo.payload.request.ProductRequest;
import com.chaapaar.demo.payload.response.ProductResponse;
import com.chaapaar.demo.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/product")
@RequiredArgsConstructor
public class ProductController extends HandleValidationExceptions {
    private ProductService productService;

    @PostMapping
    public ResponseEntity<ProductResponse> create(@RequestBody ProductRequest request){
        ProductResponse response = productService.createProduct(request);
        URI uri = URI.create("/api/v1/produc");
        return ResponseEntity.created(uri).body(response);
    }
    @GetMapping
    public ResponseEntity<List<ProductResponse>> getAll(){
        return ResponseEntity.ok(productService.getAll());
    }
    @GetMapping(path = "{id}")
    public ResponseEntity<ProductResponse> get(@PathVariable("id") Long id){
        return ResponseEntity.ok(productService.get(id));
    }
    @PatchMapping(path = "{id}")
    public ResponseEntity<ProductResponse> edit(@PathVariable("id") Long id, @RequestBody EditProductRequest request){
        return ResponseEntity.ok(productService.update(id,request));
    }
    @DeleteMapping(path = "{id}")
    public ResponseEntity<String> delete(@PathVariable("id") Long id){
        return ResponseEntity.ok(productService.delete(id));
    }
}
