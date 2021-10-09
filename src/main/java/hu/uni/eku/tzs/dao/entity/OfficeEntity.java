package hu.uni.eku.tzs.dao.entity;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "offices")
public class OfficeEntity {

    @Id
    @Column(name = "office_code", nullable = false, length = 10)
    private String officeCode;

    @Column(name = "city", nullable = false, length = 50)
    private String city;

    @Column(name = "phone", nullable = false, length = 50)
    private String phone;

    @Column(name = "address_line1", nullable = false, length = 50)
    private String addressLine1;

    @Column(name = "address_line2", length = 50)
    private String addressLine2;

    @Column(name = "state", length = 50)
    private String state;

    @Column(name = "country", nullable = false, length = 50)
    private String country;

    @Column(name = "postal_code", nullable = false, length = 15)
    private String postalCode;

    @Column(name = "territory", nullable = false, length = 10)
    private String territory;

}
