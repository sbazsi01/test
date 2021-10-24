package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.EmployeeRepository;
import hu.uni.eku.tzs.dao.OfficeRepository;
import hu.uni.eku.tzs.dao.entity.EmployeeEntity;
import hu.uni.eku.tzs.dao.entity.OfficeEntity;
import hu.uni.eku.tzs.model.Employee;
import hu.uni.eku.tzs.model.Office;
import hu.uni.eku.tzs.service.exceptions.EmployeeAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.EmployeeNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Service;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;
import static hu.uni.eku.tzs.service.OfficeManagerImpl.convertOfficeModel2Entity;

@Service
@RequiredArgsConstructor
public class EmployeeManagerImpl implements EmployeeManager {

    private final EmployeeRepository employeeRepository;

    private final OfficeRepository officeRepository;

    private final OfficeManagerImpl officeManager;

    private Employee convertEmployeeEntity2Model(EmployeeEntity employeeEntity) {
        /*Employee reportsTo;
        if(employeeEntity.getReportsTo()==null){
            reportsTo=null;
        }
        else{
            reportsTo=readByEmployeeNumber(employeeEntity.getReportsTo().getEmployeeNumber());
        }*/

        return new Employee(
            employeeEntity.getEmployeeNumber(),
            employeeEntity.getLastName(),
            employeeEntity.getFirstName(),
            employeeEntity.getExtension(),
            employeeEntity.getEmail(),
            officeManager.readByOfficeCode(employeeEntity.getOffice().getOfficeCode()),
           /* reportsTo,*/
            employeeEntity.getReportsTo(),
            employeeEntity.getJobTitle()
        );
    }

    private static EmployeeEntity convertEmployeeModel2Entity(Employee employee) {
        return EmployeeEntity.builder()
            .employeeNumber(employee.getEmployeeNumber())
            .lastName(employee.getLastName())
            .firstName(employee.getFirstName())
            .extension(employee.getExtension())
            .email(employee.getEmail())
            .office(convertOfficeModel2Entity(employee.getOffice()))
            // .reportsTo(convertEmployeeModel2Entity(employee.getReportsTo()))
            .reportsTo(employee.getReportsTo())
            .jobTitle(employee.getJobTitle())
            .build();
    }

    @Override
    public Employee record(Employee employee) throws EmployeeAlreadyExistsException {
        if (employeeRepository.findById(employee.getEmployeeNumber()).isPresent()) {
            throw new EmployeeAlreadyExistsException();
        }
      //  EmployeeEntity reportsTo = this.readOrRecordEmployee(employee.getReportsTo());
        OfficeEntity officeEntity = this.readOrRecordOffice(employee.getOffice());
        EmployeeEntity employeeEntity = employeeRepository.save(
            EmployeeEntity.builder()
                .employeeNumber(employee.getEmployeeNumber())
                .lastName(employee.getLastName())
                .firstName(employee.getFirstName())
                .extension(employee.getExtension())
                .email(employee.getEmail())
                .office(convertOfficeModel2Entity(employee.getOffice()))
               // .reportsTo(convertEmployeeModel2Entity(employee.getReportsTo()))
                .reportsTo(employee.getReportsTo())
                .jobTitle(employee.getJobTitle())
                .build()
        );
        return convertEmployeeEntity2Model(employeeEntity);
    }

    @SneakyThrows
    @Override
    public Employee readByEmployeeNumber(Integer employeeNumber)  {
        Optional<EmployeeEntity> entity = employeeRepository.findById(employeeNumber);
        if (entity.isEmpty()) {
            throw new EmployeeNotFoundException(String.format("Cannot find employee with number %d", employeeNumber));
        }

        return convertEmployeeEntity2Model(entity.get());
    }

    @Override
    public Collection<Employee> readAll() {
        return employeeRepository.findAll().stream().map(this::convertEmployeeEntity2Model)
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

    private EmployeeEntity readOrRecordEmployee(Employee employee) {
        if (employeeRepository.findById(employee.getEmployeeNumber()).isPresent()) {
            return employeeRepository.findById(employee.getEmployeeNumber()).get();
        }
        return employeeRepository.save(
            EmployeeEntity.builder()
                .employeeNumber(employee.getEmployeeNumber())
                .lastName(employee.getLastName())
                .firstName(employee.getFirstName())
                .extension(employee.getExtension())
                .email(employee.getEmail())
                .office(convertOfficeModel2Entity(employee.getOffice()))
                // .reportsTo(convertEmployeeModel2Entity(employee.getReportsTo()))
                .reportsTo(employee.getReportsTo())
                .jobTitle(employee.getJobTitle())
                .build()
        );
    }

    private OfficeEntity readOrRecordOffice(Office office) {
        if (officeRepository.findById(office.getOfficeCode()).isPresent()) {
            return officeRepository.findById(office.getOfficeCode()).get();
        }
        return officeRepository.save(
                OfficeEntity.builder()
                        .officeCode(office.getOfficeCode())
                        .addressLine1(office.getAddressLine1())
                        .addressLine2(office.getAddressLine2())
                        .city(office.getCity())
                        .country(office.getCountry())
                        .state(office.getState())
                        .phone(office.getPhone())
                        .postalCode(office.getPostalCode())
                        .territory(office.getTerritory())
                        .build()
        );
    }
}
