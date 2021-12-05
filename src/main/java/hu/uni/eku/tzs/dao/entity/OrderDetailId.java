package hu.uni.eku.tzs.dao.entity;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import javax.persistence.JoinColumn;
import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
public class OrderDetailId implements Serializable {

    @JoinColumn(name = "orderNumber", nullable = false)
    public Integer orderNumber = 10;

    @JoinColumn(name = "productCode", nullable = false)
    public String productCode = "agy";
}
