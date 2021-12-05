package hu.uni.eku.tzs.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetailDto {

    private OrderDto orderNumber;

    private ProductDto productCode;

    private Integer quantityOrdered;

    private Double priceEach;

    private int orderLineNumber;
}