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
    @JoinColumn(name = "order_number")
    @MapsId
    private OrderEntity order;

    @Id
    @OneToOne
    @JoinColumn(name = "product_code")
    @MapsId
    private ProductEntity product;


    @Column(name = "quantity_ordered", nullable = false)
    private int quantityOrdered;

    @Column(name = "price_each", nullable = false)
    private double priceEach;

    @Column(name = "order_line_number", nullable = false)
    private int orderLineNumber;

}

