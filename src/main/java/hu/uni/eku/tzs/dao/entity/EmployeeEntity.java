package hu.uni.eku.tzs.dao.entity;

import lombok.*;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "employees")
public class EmployeesEntity {
    @Id
    private int employeeNumber;

    private String lastName;

    private String extension;

    private String email;

    @ManyToOne
    @JoinColumn(name = "offices")
    private OfficesEntity office;

    private int reportsTo;

    private String jobTitle;


}
