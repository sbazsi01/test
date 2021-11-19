package hu.uni.eku.tzs.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.JoinColumn;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "products")
public class ProductEntity {
    @Id
    private String productCode;

    @Column
    private String productName;

    @ManyToOne(optional = false)
    @JoinColumn(name = "productLine", nullable = false)
    private ProductLinesEntity productLine;

    @Column
    private String productScale;

    @Column
    private String productVendor;

    @Column
    private String productDescription;

    @Column
    private int quantityInStock;

    @Column
    private double buyPrice;

    @Column
    private double msrp;
}
