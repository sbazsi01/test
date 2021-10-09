package hu.uni.eku.tzs.dao.entity;


import lombok.*;

import javax.persistence.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "products")
public class ProductEntity {

    @Id
    @Column(name = "product_code", nullable = false, length = 15)
    private String productCode;

    @Column(name = "product_name", nullable = false, length = 70)
    private String productName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "product_line", nullable = false)
    private ProductLineEntity productLine;

    @Column(name = "product_scale", nullable = false, length = 10)
    private String productScale;

    @Column(name = "product_vendor", nullable = false, length = 50)
    private String productVendor;

    @Lob
    @Column(name = "product_description", nullable = false)
    private String productDescription;

    @Column(name = "quantity_in_stock", nullable = false)
    private int quantityInStock;

    @Column(name = "buy_price", nullable = false)
    private double buyPrice;

    @Column(name = "MSRP", nullable = false)
    private double msrp;

}
