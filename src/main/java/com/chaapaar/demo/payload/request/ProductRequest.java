package com.chaapaar.demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductRequest {
    @NotBlank
    @Size(min = 3,max = 128)
    private String name;
    @NotBlank
    @Min(value = 0)
    private  Float price;
}
