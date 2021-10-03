package hu.uni.eku.tzs.model;


import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import java.sql.Blob;


@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity

public class ProductLines {
    @Id
    private String productLine;

    private String textDescription;

    private String htmlDescription;
    @Lob
    private Blob image;

}
