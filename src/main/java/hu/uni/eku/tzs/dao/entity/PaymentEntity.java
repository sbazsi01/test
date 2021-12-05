package hu.uni.eku.tzs.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import java.sql.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "payments")
public class PaymentEntity {

    @ManyToOne(optional = false)
    @JoinColumn(name = "customers", nullable = false)
    private CustomerEntity customerNumber;

    @Id
    private String checkNumber;

    @Column
    Date paymentDate;

    @Column
    private double amount;
}
