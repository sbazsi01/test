package hu.uni.eku.tzs.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.IdClass;
import javax.persistence.OneToOne;
import javax.persistence.MapsId;
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

