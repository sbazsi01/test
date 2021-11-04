package hu.uni.eku.tzs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private String productCode;

    private String productName;

    private ProductLines productLine;

    private String productScale;

    private String productVendor;

    private String productDescription;

    private int quantityInStock;

    private double buyPrice;

    private double MSRP;
}