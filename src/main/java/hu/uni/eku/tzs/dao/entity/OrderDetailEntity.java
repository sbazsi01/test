package hu.uni.eku.tzs.dao.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Id;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "orderdetails")
@IdClass(OrderDetailId.class)
public class OrderDetailEntity implements Serializable {

    @Id
    @OneToOne
    @JoinColumn(name = "orderNumber")
    @MapsId
    private OrderEntity order;

    @Id
    @OneToOne
    @JoinColumn(name = "productCode")
    @MapsId
    private ProductEntity product;


    @Column(name = "quantityOrdered", nullable = false)
    private int quantityOrdered;

    @Column(name = "priceEach", nullable = false)
    private double priceEach;

    @Column(name = "orderLineNumber", nullable = false)
    private int orderLineNumber;

}

