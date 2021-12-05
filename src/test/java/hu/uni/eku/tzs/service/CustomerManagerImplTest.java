package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.CustomerRepository;
import hu.uni.eku.tzs.dao.entity.CustomerEntity;
import hu.uni.eku.tzs.model.Customer;
import hu.uni.eku.tzs.service.exceptions.CustomerAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.CustomerNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerManagerImplTest {

    @Mock
    CustomerRepository customerRepository;

    @InjectMocks
    CustomerManagerImpl service;


    @Test
    void recordCustomerHappyPath() throws CustomerAlreadyExistsException {
        // given
        Customer TestCustomer = TestDataProvider.getTestCustomer();
        CustomerEntity TestCustomerEntity = TestDataProvider.getTestCustomerEntity();
        when(customerRepository.findById(any())).thenReturn(Optional.empty());
        when(customerRepository.save(any())).thenReturn(TestCustomerEntity);
        // when
        Customer actual = service.record(TestCustomer);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(TestCustomer);
    }

    @Test
    void recordCustomerUnknownEmployee() throws CustomerAlreadyExistsException {
        // given
        Customer TestCustomer = TestDataProvider.getTestCustomer();
        CustomerEntity TestCustomerEntity = TestDataProvider.getTestCustomerEntity();
        when(customerRepository.findById(TestDataProvider.getTestCustomer().getCustomerNumber())).thenReturn(Optional.empty());
        when(customerRepository.save(TestCustomerEntity)).thenReturn(TestCustomerEntity);
        // when
        Customer actual = service.record(TestCustomer);
        // then
        assertThat(actual).usingRecursiveComparison()
            .isEqualTo(TestCustomer);
    }

    @Test
    void recordCustomerAlreadyExistsException() {
        // given
        Customer _103 = TestDataProvider.getTestCustomer();
        CustomerEntity _103Entity = TestDataProvider.getTestCustomerEntity();
        when(customerRepository.findById(TestDataProvider.getTestCustomer().getCustomerNumber())).thenReturn(Optional.ofNullable(_103Entity));
        // when
        assertThatThrownBy(() -> {
            service.record(_103);
        }).isInstanceOf(CustomerAlreadyExistsException.class);
    }

    @Test
    void readByCustomerNumberHappyPath() throws CustomerNotFoundException {
        // given
        when(customerRepository.findById(TestDataProvider.getTestCustomer().getCustomerNumber()))
            .thenReturn(Optional.of(TestDataProvider.getTestCustomerEntity()));
        Customer expected = TestDataProvider.getTestCustomer();
        // when
        Customer actual = service.readByCustomerNumber(TestDataProvider.getTestCustomer().getCustomerNumber());
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByCustomerNumberCustomerNotFoundException() {
        // given
        when(customerRepository.findById(TestDataProvider.UNKNOWN_CUSTOMERNUMBER)).thenReturn(Optional.empty());
        // when then
        assertThatThrownBy(() -> {
            service.readByCustomerNumber(TestDataProvider.UNKNOWN_CUSTOMERNUMBER);
        }).isInstanceOf(CustomerNotFoundException.class)
            .hasMessageContaining(TestDataProvider.UNKNOWN_CUSTOMERNUMBER.toString());
    }

    @Test
    void readAllHappyPath() {
        // given
        List<CustomerEntity> customerEntities = List.of(
            TestDataProvider.getTestCustomerEntity()
        );
        Collection<Customer> expectedCustomers = List.of(
            TestDataProvider.getTestCustomer()

        );
        when(customerRepository.findAll()).thenReturn(customerEntities);
        // when
        Collection<Customer> actualCustomers = service.readAllCustomers();
        // then
        assertThat(actualCustomers)
            .usingRecursiveComparison()
            .isEqualTo(expectedCustomers);
    }

    @Test
    void modifyCustomerHappyPath() {
        // given
        Customer _103 = TestDataProvider.getTestCustomer();
        CustomerEntity _103Entity = TestDataProvider.getTestCustomerEntity();
        when(customerRepository.save(_103Entity)).thenReturn(_103Entity);
        // when
        Customer actual = service.modify(_103);
        // then
        assertThat(actual).usingRecursiveComparison()
            .isEqualTo(_103);

    }

    protected static class TestDataProvider {

        public static final Integer UNKNOWN_CUSTOMERNUMBER = 9999;

        public static Customer getTestCustomer() {
            return new Customer(1, "test",
                "test",
                "test",
                "test",
                "test",
                null,
                "test",
                null,
                "test",
                "test",
                EmployeeManagerImplTest.TestDataProvider.get_1370(),
                21000.0);
        }

        public static CustomerEntity getTestCustomerEntity() {
            return CustomerEntity.builder()
                .customerNumber(1)
                .customerName("test")
                .contactLastName("test")
                .contactFirstName("test")
                .phone("test")
                .addressLine1("test")
                .addressLine2(null)
                .city("test")
                .state(null)
                .postalCode("test")
                .country("test")
                .salesRepEmployeeNumber(EmployeeManagerImplTest.TestDataProvider.get_1370Entity())
                .creditLimit(21000.0)
                .build();
        }
    }
}