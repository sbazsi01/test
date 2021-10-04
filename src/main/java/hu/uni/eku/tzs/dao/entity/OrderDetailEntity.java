package hu.uni.eku.tzs.dao.entity;

import lombok.*;

import javax.persistence.*;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "orderdetails")
public class OrderDetailEntity {

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
    private Integer quantityOrdered;

    @Column(name = "priceEach", nullable = false)
    private Double priceEach;

    @Column(name = "orderLineNumber", nullable = false)
    private Integer orderLineNumber;

}
