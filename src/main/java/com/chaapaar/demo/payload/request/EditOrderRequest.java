package com.chaapaar.demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;
import javax.validation.constraints.Min;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class EditOrderRequest {
    @Nullable
    @Min(value = 1)
    private Long productId;
    @Nullable
    @Min(value = 1)
    private Integer count;
}
