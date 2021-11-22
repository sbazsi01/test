package hu.uni.eku.tzs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    private Integer orderNumber;

    private String orderDate;

    private String requiredDate;

    private String shippedDate;

    private String status;

    private String comments;

    private Integer customerNumber;
}
