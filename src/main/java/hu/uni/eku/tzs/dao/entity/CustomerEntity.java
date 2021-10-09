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
    @Column(name = "customer_number", nullable = false)
    private int customerNumber;

    @Column(name = "customer_name", nullable = false, length = 50)
    private String customerName;

    @Column(name = "contact_last_name", nullable = false, length = 50)
    private String contactLastName;

    @Column(name = "contact_first_name", nullable = false, length = 50)
    private String contactFirstName;

    @Column(name = "phone", nullable = false, length = 50)
    private String phone;

    @Column(name = "address_line1", nullable = false, length = 50)
    private String addressLine1;

    @Column(name = "address_line2", length = 50)
    private String addressLine2;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "state", length = 50)
    private String state;

    @Column(name = "postal_code", length = 15)
    private String postalCode;

    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @ManyToOne
    @JoinColumn(name = "sales_rep_employee_number")
    private EmployeeEntity salesRepEmployee;

    @Column(name = "credit_limit")
    private double creditLimit;

}
