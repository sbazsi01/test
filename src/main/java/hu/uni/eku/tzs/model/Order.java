package hu.uni.eku.tzs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    private int orderNumber;

    private String orderDate;

    private String requiredDate;

    private String shippedDate;

    private String status;

    private String comments;

    private Customer customerNumber;

}