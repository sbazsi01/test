package hu.uni.eku.tzs.dao.entity;

import lombok.*;

import javax.persistence.*;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "customers")
public class CustomerEntity {

    @Id
    @Column(name = "customerNumber", nullable = false)
    private int customerNumber;

    @Column(name = "customerName", nullable = false, length = 50)
    private String customerName;

    @Column(name = "contactLastName", nullable = false, length = 50)
    private String contactLastName;

    @Column(name = "contactFirstName", nullable = false, length = 50)
    private String contactFirstName;

    @Column(name = "phone", nullable = false, length = 50)
    private String phone;

    @Column(name = "addressLine1", nullable = false, length = 50)
    private String addressLine1;

    @Column(name = "addressLine2", length = 50)
    private String addressLine2;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "state", length = 50)
    private String state;

    @Column(name = "postalCode", length = 15)
    private String postalCode;

    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @ManyToOne
    @JoinColumn(name = "salesRepEmployee")
    private EmployeeEntity salesRepEmployee;

    @Column(name = "creditLimit")
    private double creditLimit;

}
