package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.CustomerRepository;
import hu.uni.eku.tzs.dao.entity.CustomerEntity;
import hu.uni.eku.tzs.dao.entity.EmployeeEntity;
import hu.uni.eku.tzs.dao.entity.OfficeEntity;
import hu.uni.eku.tzs.model.Customer;
import hu.uni.eku.tzs.model.Employee;
import hu.uni.eku.tzs.model.Office;
import hu.uni.eku.tzs.service.exceptions.CustomerAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.CustomerNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CustomerManagerImpl implements CustomerManager {

    private final CustomerRepository customerRepository;

    private static Customer convertCustomerEntity2Model(CustomerEntity customerEntity) {

        return new Customer(
                customerEntity.getCustomerNumber(),
                customerEntity.getCustomerName(),
                customerEntity.getContactLastName(),
                customerEntity.getContactFirstName(),
                customerEntity.getPhone(),
                customerEntity.getAddressLine1(),
                customerEntity.getAddressLine2(),
                customerEntity.getCity(),
                customerEntity.getState(),
                customerEntity.getPostalCode(),
                customerEntity.getCountry(),
            new Employee(
                customerEntity.getSalesRepEmployeeNumber().getEmployeeNumber(),
                customerEntity.getSalesRepEmployeeNumber().getLastName(),
                customerEntity.getSalesRepEmployeeNumber().getFirstName(),
                customerEntity.getSalesRepEmployeeNumber().getExtension(),
                customerEntity.getSalesRepEmployeeNumber().getEmail(),
                new Office(
                    customerEntity.getSalesRepEmployeeNumber().getOffice().getOfficeCode(),
                    customerEntity.getSalesRepEmployeeNumber().getOffice().getCity(),
                    customerEntity.getSalesRepEmployeeNumber().getOffice().getPhone(),
                    customerEntity.getSalesRepEmployeeNumber().getOffice().getAddressLine1(),
                    customerEntity.getSalesRepEmployeeNumber().getOffice().getAddressLine2(),
                    customerEntity.getSalesRepEmployeeNumber().getOffice().getState(),
                    customerEntity.getSalesRepEmployeeNumber().getOffice().getCountry(),
                    customerEntity.getSalesRepEmployeeNumber().getOffice().getPostalCode(),
                    customerEntity.getSalesRepEmployeeNumber().getOffice().getTerritory()
                ),
                customerEntity.getSalesRepEmployeeNumber().getReportsTo(),
                customerEntity.getSalesRepEmployeeNumber().getJobTitle()
            ),
                customerEntity.getCreditLimit()
        );
    }

    private static CustomerEntity convertCustomerModel2Entity(Customer customer) {
        return CustomerEntity.builder()
                .customerNumber(customer.getCustomerNumber())
                .customerName(customer.getCustomerName())
                .contactLastName(customer.getContactLastName())
                .contactFirstName(customer.getContactFirstName())
                .phone(customer.getPhone())
                .addressLine1(customer.getAddressLine1())
                .addressLine2(customer.getAddressLine2())
                .city(customer.getCity())
                .state(customer.getState())
                .postalCode(customer.getPostalCode())
                .country(customer.getCountry())
                .salesRepEmployeeNumber(convertEmployeeModel2Entity(customer.getSalesRepEmployeeNumber()))
                .creditLimit(customer.getCreditLimit())
                .build();
    }
    
    private static EmployeeEntity convertEmployeeModel2Entity(Employee employee) {
        return EmployeeEntity.builder()
            .employeeNumber(employee.getEmployeeNumber())
            .lastName(employee.getLastName())
            .firstName(employee.getFirstName())
            .extension(employee.getExtension())
            .email(employee.getEmail())
            .office(convertOfficeModel2Entity(employee.getOffice()))
            .reportsTo(employee.getReportsTo())
            .jobTitle(employee.getJobTitle())
            .build();
    }

    private static OfficeEntity convertOfficeModel2Entity(Office office) {
        return OfficeEntity.builder()
            .officeCode(office.getOfficeCode())
            .addressLine1(office.getAddressLine1())
            .addressLine2(office.getAddressLine2())
            .city(office.getCity())
            .country(office.getCountry())
            .state(office.getState())
            .phone(office.getPhone())
            .postalCode(office.getPostalCode())
            .territory(office.getTerritory())
            .build();
    }

    @Override
    public Customer record(Customer customer) throws CustomerAlreadyExistsException {
        if (customerRepository.findById(customer.getCustomerNumber()).isPresent()) {
            throw new CustomerAlreadyExistsException();
        }
        CustomerEntity customerEntity = customerRepository.save(
               convertCustomerModel2Entity(customer)
        );
        return convertCustomerEntity2Model(customerEntity);
    }

    @Override
    public Customer readByCustomerNumber(String customerNumber) throws CustomerNotFoundException {
        Optional<CustomerEntity> entity = customerRepository.findById(customerNumber);
        if (entity.isEmpty()) {
            throw new CustomerNotFoundException(String.format("Cannot find customer"
               + " with customer number %s", customerNumber));
        }

        return convertCustomerEntity2Model(entity.get());
    }

    @Override
    public Collection<Customer> readAll() {
        return customerRepository.findAll().stream().map(CustomerManagerImpl::convertCustomerEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public Customer modify(Customer customer) {
        CustomerEntity entity = convertCustomerModel2Entity(customer);
        return convertCustomerEntity2Model(customerRepository.save(entity));
    }

    @Override
    public void delete(Customer customer) {
        customerRepository.delete(convertCustomerModel2Entity(customer));
    }
}
