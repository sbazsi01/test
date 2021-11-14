package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.CustomerRepository;
import hu.uni.eku.tzs.dao.entity.CustomerEntity;
import hu.uni.eku.tzs.model.Customer;
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
                customerEntity.getSalesRepEmployeeNumber(),
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
                .salesRepEmployeeNumber(customer.getSalesRepEmployeeNumber())
                .creditLimit(customer.getCreditLimit())
                .build();
    }

    @Override
    public Customer record(Customer customer) throws CustomerAlreadyExistsException {
        if (CustomerRepository.findById(customer.getCustomerNumber()).isPresent()) {
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
            throw new CustomerNotFoundException(String.format("Cannot find customer with customer number %s", customerNumber));
        }

        return convertCustomerEntity2Model(entity.get());
    }

    @Override
    public Collection<Customer> readAll() {
        return CustomerRepository.findAll().stream().map(CustomerManagerImpl::convertCustomerEntity2Model)
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
