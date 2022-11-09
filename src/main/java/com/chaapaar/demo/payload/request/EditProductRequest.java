package com.chaapaar.demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EditProductRequest {
    @Nullable
    @Size(min = 3,max = 100)
    private String name;
    @Nullable
    @Min(value = 0)
    private Double price;
}
