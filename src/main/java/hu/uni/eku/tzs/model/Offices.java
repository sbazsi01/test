package hu.uni.eku.tzs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Id;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Offices {
    @Id
    private String officeCode;

    private String city;

    private String phone;

    private String addressLine1;

    private String addressLine2;

    private String state;

    private String country;

    private String postalCode;

    private String territory;
}
