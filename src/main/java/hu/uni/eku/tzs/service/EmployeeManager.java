package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Employee;
import hu.uni.eku.tzs.service.exceptions.EmployeeAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.EmployeeNotFoundException;
import java.util.Collection;

public interface EmployeeManager {

    Employee record(Employee employee) throws EmployeeAlreadyExistsException;

    Employee readByEmployeeNumber(Integer employeeNumber) throws EmployeeNotFoundException;

    Collection<Employee> readAllEmployees();

    Collection<Employee> readAllByOffice(String officeCode);

    Employee modify(Employee employee);

    void delete(Employee employee) throws EmployeeNotFoundException;

}
