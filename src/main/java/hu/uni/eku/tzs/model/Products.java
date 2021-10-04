package hu.uni.eku.tzs.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;


@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Products {
    @Id
    private String productCode;

    private String productName;

    private String productLine;

    private String productScale;

    private String productVendor;

    private String productDescription;

    private int quantityInStock;

    private double buyPrice;

    private double MSRP;

}
