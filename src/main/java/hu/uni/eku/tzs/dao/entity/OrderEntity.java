package hu.uni.eku.tzs.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "orders")
public class OrderEntity {

    @Id
    @Column(name = "orderNumber", nullable = false)
    private int orderNumber;

    @Column(name = "orderDate", nullable = false)
    private String orderDate;

    @Column(name = "requiredDate", nullable = false)
    private String requiredDate;

    @Column(name = "shippedDate")
    private String shippedDate;

    @Column(name = "status", nullable = false, length = 15)
    private String status;

    @Lob
    @Column(name = "comments")
    private String comments;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customerNumber", nullable = false)
    private CustomerEntity customer;
}
