package hu.uni.eku.tzs.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "orderdetails")
@IdClass(OrderDetailId.class)
public class OrderDetailEntity implements Serializable {

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "orderNumber", nullable = false)
    private OrderEntity orderNumber;

    @Id
    @ManyToOne(optional = false)
    @JoinColumn(name = "productCode", nullable = false)
    private ProductEntity productCode;

    @Column(name = "quantityOrdered", nullable = false)
    private int quantityOrdered;

    @Column(name = "priceEach", nullable = false)
    private double priceEach;

    @Column(name = "orderLineNumber", nullable = false)
    private int orderLineNumber;
}
