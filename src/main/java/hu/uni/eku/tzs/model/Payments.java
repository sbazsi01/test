package hu.uni.eku.tzs.model;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Payments {
    @Id
    private int customerNumber;

    private String checkNumber;

    private java.sql.Date paymentDate;

    private double amount;

}
