package hu.uni.eku.tzs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    private Order orderNumber;

    private Product productCode;

    private Integer quantityOrdered;

    private Double priceEach;

    private Integer orderLineNumber;
}
