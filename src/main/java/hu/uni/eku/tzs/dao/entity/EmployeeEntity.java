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
    @Column(name = "employee_number", nullable = false)
    private int employeeNumber;

    @Column(name = "last_name", nullable = false, length = 50)
    private String lastName;

    @Column(name = "first_name", nullable = false, length = 50)
    private String firstName;

    @Column(name = "extension", nullable = false, length = 10)
    private String extension;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @ManyToOne(optional = false)
    @JoinColumn(name = "office_code", nullable = false)
    private OfficeEntity office;

    @ManyToOne
    @JoinColumn(name = "reports_to")
    private EmployeeEntity reportsTo;

    @Column(name = "job_title", nullable = false, length = 50)
    private String jobTitle;
}
