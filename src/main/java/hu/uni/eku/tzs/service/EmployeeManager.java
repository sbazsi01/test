package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Employee;
import hu.uni.eku.tzs.model.Office;
import hu.uni.eku.tzs.service.exceptions.EmployeeAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.EmployeeNotFoundException;
import hu.uni.eku.tzs.service.exceptions.OfficeNotFoundException;

import java.util.Collection;

public interface EmployeeManager {

    Employee record(Employee employee) throws EmployeeAlreadyExistsException;

    Employee readByEmployeeNumber(Integer employeeNumber) throws EmployeeNotFoundException;

    Collection<Employee> readAll();

    Employee modify(Employee employee);

    void delete(Employee employee) throws EmployeeNotFoundException;

}
