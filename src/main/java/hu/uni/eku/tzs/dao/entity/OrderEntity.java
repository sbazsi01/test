package hu.uni.eku.tzs.dao.entity;


import lombok.*;

import javax.persistence.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "orders")
public class OrdersEntity {
    @Id
    private int orderNumber;

    private java.sql.Date orderDate;

    private java.sql.Date requiredDate;

    private java.sql.Date shippedDate;

    private String status;

    private String comments;

    private int customerNumber;

}
