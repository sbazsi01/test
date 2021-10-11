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
    @JoinColumn(name = "customerNumber")
    @MapsId
    private CustomerEntity customer;

    @Id
    @Column(name = "checkNumber", nullable = false, length = 50)
    private String checkNumber;

    @Column(name = "paymentDate", nullable = false)
    private LocalDate paymentDate;

    @Column(name = "amount", nullable = false)
    private double amount;

}
