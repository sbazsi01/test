package hu.uni.eku.tzs.controller.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeDto {

    @NotNull
    private Integer employeeNumber;

    @NotBlank(message = "last name of employee cannot be empty")
    @Size(max = 50, message = "last name cannot be longer than 50 characters")
    private String lastName;

    @NotBlank(message = "first name of employee cannot be empty")
    @Size(max = 50, message = "first name cannot be longer than 50 characters")
    private String firstName;

    @NotBlank(message = "extension of employee cannot be empty")
    @Size(max = 10, message = "extension cannot be longer than 10 characters")
    private String extension;

    @NotEmpty
    @Email
    @Size(max = 100, message = "email cannot be longer than 100 characters")
    private String email;

    @Valid
    private OfficeDto office;

    //    private EmployeeDto reportsTo;
    private Integer reportsTo;

    @NotBlank(message = "job title of employee cannot be empty")
    @Size(max = 50, message = "job title cannot be longer than 50 characters")
    private String jobTitle;
}
