package hu.uni.eku.tzs.dao.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "products")
public class ProductsEntity {
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
