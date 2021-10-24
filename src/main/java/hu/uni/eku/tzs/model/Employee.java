package hu.uni.eku.tzs.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    private Integer employeeNumber;

    private String lastName;

    private String firstName;

    private String extension;

    private String email;

    private Office office;

    // private Employee reportsTo;
    private Integer reportsTo;

    private String jobTitle;
}