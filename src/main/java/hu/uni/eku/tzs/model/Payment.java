package hu.uni.eku.tzs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Payment {

    private Customer customerNumber;

    private String checkNumber;

    Date paymentDate;

    private double amount;
}