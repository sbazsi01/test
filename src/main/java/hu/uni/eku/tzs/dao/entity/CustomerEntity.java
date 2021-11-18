package hu.uni.eku.tzs.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "customers")
public class CustomerEntity {
    @Id
    private String customerNumber;

    @Column
    private String customerName;

    @Column
    private String contactLastName;

    @Column
    private String contactFirstName;

    @Column
    private String phone;

    @Column
    private String addressLine1;

    @Column
    private String addressLine2;

    @Column
    private String city;

    @Column
    private String state;

    @Column
    private String postalCode;

    @Column
    private String country;

    @ManyToOne
    @JoinColumn(name = "salesRepEmployeeNumber")
    private EmployeeEntity salesRepEmployeeNumber;

    @Column
    private Double creditLimit;
}
