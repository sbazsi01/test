package hu.uni.eku.tzs.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "productLines")
public class ProductLinesEntity {
    @Id
    private String productLine;

    @Column
    private String textDescription;

    @Column
    private String htmlDescription;

    @Column
    @Lob
    private byte[] image;
}
