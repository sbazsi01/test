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
public class Employees {
    @Id
    private int employeeNumber;

    private String lastName;

    private String extension;

    private String email;

    private String officeCode;

    private int reportsTo;

    private String jobTitle;


}
