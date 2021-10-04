package hu.uni.eku.tzs.dao.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "orderdetails")
public class OrderDetailsEntity {
    @Id
    private int orderNumber;

    @Id
    private String productCode;

    private int quantityOrdered;

    private double priceEach;

    private short orderLineNumber;


}
