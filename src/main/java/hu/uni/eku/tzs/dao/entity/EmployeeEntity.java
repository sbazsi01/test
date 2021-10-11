package hu.uni.eku.tzs.dao.entity;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "employees")
public class EmployeeEntity {


    @Id
    @Column(name = "employeeNumber", nullable = false)
    private int employeeNumber;

    @Column(name = "lastName", nullable = false, length = 50)
    private String lastName;

    @Column(name = "firstName", nullable = false, length = 50)
    private String firstName;

    @Column(name = "extension", nullable = false, length = 10)
    private String extension;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @ManyToOne(optional = false)
    @JoinColumn(name = "officeCode", nullable = false)
    private OfficeEntity office;

    @ManyToOne
    @JoinColumn(name = "reportsTo")
    private EmployeeEntity reportsTo;

    @Column(name = "jobTitle", nullable = false, length = 50)
    private String jobTitle;
}
