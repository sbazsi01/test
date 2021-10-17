package hu.uni.eku.tzs.dao.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private int officeCode;

    @Column
    private String city;

    @Column
    private String phone;

    @Column
    private String addressLine1;

    @Column
    private String addressLine2;

    @Column
    private String state;

    @Column
    private String country;

    @Column
    private String postalCode;

    @Column
    private String territory;
}
