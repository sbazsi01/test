package hu.uni.eku.tzs.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "payments")
@IdClass(PaymentId.class)
public class PaymentEntity implements Serializable {
    @Id
    @OneToOne
    @JoinColumn(name = "customer_number")
    @MapsId
    private CustomerEntity customer;

    @Id
    @Column(name = "check_number", nullable = false, length = 50)
    private String checkNumber;

    @Column(name = "payment_date", nullable = false)
    private String paymentDate;

    @Column(name = "amount", nullable = false)
    private double amount;

}
