package hu.uni.eku.tzs.dao.entity;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.sql.Blob;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "productlines")
public class ProductLinesEntity {
    @Id
    private String productLine;

    private String textDescription;

    private String htmlDescription;
    @Lob
    private Blob image;

}
