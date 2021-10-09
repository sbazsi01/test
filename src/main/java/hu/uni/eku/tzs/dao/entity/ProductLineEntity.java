package hu.uni.eku.tzs.dao.entity;


import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.sql.Blob;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "productlines")
public class ProductLineEntity {

    @Id
    @Column(name = "product_line", nullable = false, length = 50)
    private String productLine;

    @Column(name = "text_description", length = 4000)
    private String textDescription;

    @Lob
    @Column(name = "html_description")
    private String htmlDescription;

    @Column(name = "image")
    private byte[] image;

}
