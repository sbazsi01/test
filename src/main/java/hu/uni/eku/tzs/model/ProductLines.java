package hu.uni.eku.tzs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Blob;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductLines {

    private String productLine;

    private String textDescription;

    private String htmlDescription;

    private byte[] image;
}
