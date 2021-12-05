package hu.uni.eku.tzs.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor

@Entity(name = "orders")
public class OrderEntity {

    @Id
    @Column(name = "order_number", nullable = false)
    private int orderNumber;

    @Column(name = "order_date", nullable = false)
    private String orderDate;

    @Column(name = "required_date", nullable = false)
    private String requiredDate;

    @Column(name = "shipped_date")
    private String shippedDate;

    @Column(name = "status", nullable = false, length = 15)
    private String status;

    @Lob
    @Column(name = "comments")
    private String comments;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customer_number", nullable = false)
    private CustomerEntity customer;

}