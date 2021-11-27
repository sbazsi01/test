package hu.uni.eku.tzs.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {

    @Pattern(regexp = "^[0-9]+", message = "invalid order number")
    private Integer orderNumber;

    private String productCode;

    private Integer quantityOrdered;

    private Double priceEach;

    private int orderLineNumber;
}
