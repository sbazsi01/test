package hu.uni.eku.tzs.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.Valid;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProductDto {

    private String productCode;

    private String productName;

    @Valid
    private ProductLinesDto productLine;

    private String productScale;

    private String productVendor;

    private String productDescription;

    private int quantityInStock;

    private double buyPrice;

    private double MSRP;
}
