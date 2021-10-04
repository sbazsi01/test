package hu.uni.eku.tzs.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Orders {
    @Id
    private int orderNumber;

    private java.sql.Date orderDate;

    private java.sql.Date requiredDate;

    private java.sql.Date shippedDate;

    private String status;

    private String comments;

    private int customerNumber;

}
