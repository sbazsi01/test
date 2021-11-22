package hu.uni.eku.tzs.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDto {

    private CustomerDto customerNumber;

    private String checkNumber;

    Date paymentDate;

    private double amount;
}
