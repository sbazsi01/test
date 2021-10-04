package hu.uni.eku.tzs.dao.entity;


import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "orders")
public class OrderEntity {

    @Id
    @Column(name = "orderNumber", nullable = false)
    private Integer orderNumber;

    @Column(name = "orderDate", nullable = false)
    private LocalDate orderDate;

    @Column(name = "requiredDate", nullable = false)
    private LocalDate requiredDate;

    @Column(name = "shippedDate")
    private LocalDate shippedDate;

    @Column(name = "status", nullable = false, length = 15)
    private String status;

    @Lob
    @Column(name = "comments")
    private String comments;

    @ManyToOne(optional = false)
    @JoinColumn(name = "customerNumber", nullable = false)
    private CustomerEntity customer;

}
