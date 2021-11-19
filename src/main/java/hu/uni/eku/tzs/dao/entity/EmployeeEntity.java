package hu.uni.eku.tzs.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity(name = "employees")
public class EmployeeEntity {

    @Id
    @Column(name = "employeeNumber", nullable = false)
    private Integer employeeNumber;

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

    @Column(name = "reportsTo")
    private Integer reportsTo;

    @Column(name = "jobTitle", nullable = false, length = 50)
    private String jobTitle;
}