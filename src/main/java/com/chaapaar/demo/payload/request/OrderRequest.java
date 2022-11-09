package com.chaapaar.demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.validation.constraints.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class OrderRequest {
    @NotNull
    @Min(value = 1)
    private Long customerId;
    @NotNull
    @Min(value = 1)
    private Long productId;
    @NotNull
    @Min(value = 1)
    private Integer count;

}
