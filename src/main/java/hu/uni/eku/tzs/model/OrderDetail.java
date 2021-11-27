package hu.uni.eku.tzs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderDetail {

    @Id
    private Integer orderNumber;

    private String productCode;

    private Integer quantityOrdered;

    private Double priceEach;

    private Integer orderLineNumber;
}
