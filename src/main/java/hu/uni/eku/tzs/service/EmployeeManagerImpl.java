package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.EmployeeRepository;
import hu.uni.eku.tzs.dao.entity.EmployeeEntity;
import hu.uni.eku.tzs.model.Employee;
import hu.uni.eku.tzs.service.exceptions.EmployeeAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeManagerImpl implements EmployeeManager {

    protected final EmployeeRepository employeeRepository;

    public static Employee convertEmployeeEntity2Model(EmployeeEntity employeeEntity) {
        Employee reportsTo;
        if (employeeEntity.getReportsTo() == null) {
            reportsTo = null;
        } else {
            reportsTo = convertEmployeeEntity2Model(employeeEntity.getReportsTo());
        }

        return new Employee(
            employeeEntity.getEmployeeNumber(),
            employeeEntity.getLastName(),
            employeeEntity.getFirstName(),
            employeeEntity.getExtension(),
            employeeEntity.getEmail(),
            OfficeManagerImpl.convertOfficeEntity2Model(employeeEntity.getOffice()),
            reportsTo,
            employeeEntity.getJobTitle()
        );
    }

    protected static EmployeeEntity convertEmployeeModel2Entity(Employee employee) {
        EmployeeEntity reportsTo;
        if (employee.getReportsTo() == null) {
            reportsTo = null;
        } else {
            reportsTo = convertEmployeeModel2Entity(employee.getReportsTo());
        }
        return EmployeeEntity.builder()
            .employeeNumber(employee.getEmployeeNumber())
            .lastName(employee.getLastName())
            .firstName(employee.getFirstName())
            .extension(employee.getExtension())
            .email(employee.getEmail())
            .office(OfficeManagerImpl.convertOfficeModel2Entity(employee.getOffice()))
            .reportsTo(reportsTo)
            .jobTitle(employee.getJobTitle())
            .build();
    }

    @Override
    public Employee record(Employee employee) throws EmployeeAlreadyExistsException {
        if (employeeRepository.findById(employee.getEmployeeNumber()).isPresent()) {
            throw new EmployeeAlreadyExistsException();
        }
        EmployeeEntity employeeEntity = employeeRepository.save(
            convertEmployeeModel2Entity(employee)
        );
        return convertEmployeeEntity2Model(employeeEntity);
    }

    @Override
    public Employee readByEmployeeNumber(Integer employeeNumber) throws EmployeeNotFoundException {
        Optional<EmployeeEntity> entity = employeeRepository.findById(employeeNumber);
        if (entity.isEmpty()) {
            throw new EmployeeNotFoundException(String.format("Cannot find employee with number %d", employeeNumber));
        }

        return convertEmployeeEntity2Model(entity.get());
    }

    @Override
    public Collection<Employee> readAllEmployees() {
        return employeeRepository.findAll().stream().map(EmployeeManagerImpl::convertEmployeeEntity2Model)
            .collect(Collectors.toList());
    }

    @Override
    public Collection<Employee> readAllByOffice(String officeCode) {
        return employeeRepository.findAllByOffice(officeCode).stream()
            .map(EmployeeManagerImpl::convertEmployeeEntity2Model)
            .collect(Collectors.toList());
    }

    @Override
    public Employee modify(Employee employee) {
        EmployeeEntity entity = convertEmployeeModel2Entity(employee);
        return convertEmployeeEntity2Model(employeeRepository.save(entity));
    }

    @Override
    public void delete(Employee employee) {
        employeeRepository.delete(convertEmployeeModel2Entity(employee));

    }
}
