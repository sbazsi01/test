package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.CustomerRepository;
import hu.uni.eku.tzs.dao.EmployeeRepository;
import hu.uni.eku.tzs.dao.OfficeRepository;
import hu.uni.eku.tzs.dao.entity.CustomerEntity;
import hu.uni.eku.tzs.dao.entity.EmployeeEntity;
import hu.uni.eku.tzs.model.Customer;
import hu.uni.eku.tzs.model.Employee;
import hu.uni.eku.tzs.service.exceptions.CustomerAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.CustomerNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerManagerImpl extends EmployeeManagerImpl implements CustomerManager  {

    private final CustomerRepository customerRepository;

    public CustomerManagerImpl(EmployeeRepository employeeRepository, OfficeRepository officeRepository,
                               CustomerRepository customerRepository) {
        super(employeeRepository, officeRepository);
        this.customerRepository = customerRepository;
    }

    private static Customer convertCustomerEntity2Model(CustomerEntity customerEntity) {
        Employee employee = null;
        if (customerEntity.getSalesRepEmployeeNumber() != null) {
            employee = EmployeeManagerImpl.convertEmployeeEntity2Model(customerEntity.getSalesRepEmployeeNumber());
        }
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
                employee,
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

    @Override
    public Customer record(Customer customer) throws CustomerAlreadyExistsException {
        if (customerRepository.findById(customer.getCustomerNumber()).isPresent()) {
            throw new CustomerAlreadyExistsException();
        }
        EmployeeEntity employeeEntity = this.readOrRecordEmployee(customer.getSalesRepEmployeeNumber());
        CustomerEntity customerEntity = customerRepository.save(
            CustomerEntity.builder()
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
            .salesRepEmployeeNumber(employeeEntity)
            .creditLimit(customer.getCreditLimit())
            .build()
        );
        return convertCustomerEntity2Model(customerEntity);
    }

    @Override
    public Customer readByCustomerNumber(Integer customerNumber) throws CustomerNotFoundException {
        Optional<CustomerEntity> entity = customerRepository.findById(customerNumber);
        if (entity.isEmpty()) {
            throw new CustomerNotFoundException(String.format("Cannot find customer"
               + " with customer number %s", customerNumber));
        }

        return convertCustomerEntity2Model(entity.get());
    }

    @Override
    public Collection<Customer> readAllCustomers() {
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
