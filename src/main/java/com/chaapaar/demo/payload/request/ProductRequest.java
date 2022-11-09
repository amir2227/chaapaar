package com.chaapaar.demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class ProductRequest {
    @NotNull
    @Size(min = 3,max = 100)
    private String name;
    @NotNull
    @Min(value = 0)
    private  Double price;
}
