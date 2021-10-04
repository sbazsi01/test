package hu.uni.eku.tzs.dao.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "payments")
public class PaymentEntity {
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
    private Double amount;

}
