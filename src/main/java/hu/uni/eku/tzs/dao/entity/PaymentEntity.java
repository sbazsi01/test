package hu.uni.eku.tzs.dao.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "payments")
public class PaymentsEntity {
    @Id
    private int customerNumber;

    private String checkNumber;

    private java.sql.Date paymentDate;

    private double amount;

}
