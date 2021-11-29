package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.EmployeeRepository;
import hu.uni.eku.tzs.dao.CustomerRepository;
import hu.uni.eku.tzs.dao.OfficeRepository;
import hu.uni.eku.tzs.dao.entity.EmployeeEntity;
import hu.uni.eku.tzs.dao.entity.CustomerEntity;
import hu.uni.eku.tzs.dao.entity.OfficeEntity;
import hu.uni.eku.tzs.model.Employee;
import hu.uni.eku.tzs.model.Customer;
import hu.uni.eku.tzs.model.Office;
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

    @Mock
    EmployeeRepository employeeRepository;

    @Mock
    OfficeRepository officeRepository;

    @InjectMocks
    CustomerManagerImpl service;


    @Test
    void recordCustomerHappyPath() throws CustomerAlreadyExistsException {
        // given
        Employee _1370 = TestDataProvider.get_1370();
        EmployeeEntity _1370Entity = TestDataProvider.get_1370Entity();
        Customer _103 = TestDataProvider.get_103();
        CustomerEntity _103Entity = TestDataProvider.get_103Entity();
        when(customerRepository.findById(any())).thenReturn(Optional.empty());
        when(employeeRepository.findById(_1370.getEmployeeNumber())).thenReturn(Optional.ofNullable(_1370Entity));
        when(customerRepository.save(any())).thenReturn(_103Entity);
        // when
        Customer actual = service.record(_103);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(_103);
    }

    @Test
    void recordCustomerUnknownEmployee() throws CustomerAlreadyExistsException {
        // given
        Employee _1166 = TestDataProvider.get_1166();
        EmployeeEntity _1166Entity = TestDataProvider.get_1166Entity();
        Customer _112 = TestDataProvider.get_112();
        CustomerEntity _112Entity = TestDataProvider.get_112Entity();
        Office SanFrancisco = TestDataProvider.getSanFranciscoOfficeModel();
        OfficeEntity SanFranciscoEntity = TestDataProvider.getSanFranciscoOfficeEntity();
        when(customerRepository.findById(TestDataProvider._112)).thenReturn(Optional.empty());
        when(officeRepository.findById(SanFrancisco.getOfficeCode())).thenReturn(Optional.empty());
        when(employeeRepository.findById(_1166.getEmployeeNumber())).thenReturn(Optional.empty());
        when(employeeRepository.save(_1166Entity)).thenReturn(_1166Entity);
        when(customerRepository.save(_112Entity)).thenReturn(_112Entity);
        when(officeRepository.save(SanFranciscoEntity)).thenReturn(SanFranciscoEntity);
        // when
        Customer actual = service.record(_112);
        // then
        assertThat(actual).usingRecursiveComparison()
            .isEqualTo(_112);
    }

    @Test
    void recordCustomerAlreadyExistsException() {
        // given
        Customer _103 = TestDataProvider.get_103();
        CustomerEntity _103Entity = TestDataProvider.get_103Entity();
        when(customerRepository.findById(TestDataProvider._103)).thenReturn(Optional.ofNullable(_103Entity));
        // when
        assertThatThrownBy(() -> {
            service.record(_103);
        }).isInstanceOf(CustomerAlreadyExistsException.class);
    }

    @Test
    void readByCustomerNumberHappyPath() throws CustomerNotFoundException {
        // given
        when(customerRepository.findById(TestDataProvider._103))
            .thenReturn(Optional.of(TestDataProvider.get_103Entity()));
        Customer expected = TestDataProvider.get_103();
        // when
        Customer actual = service.readByCustomerNumber(TestDataProvider._103);
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
            TestDataProvider.get_112Entity(),
            TestDataProvider.get_103Entity()
        );
        Collection<Customer> expectedCustomers = List.of(
            TestDataProvider.get_112(),
            TestDataProvider.get_103()
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
        Customer _103 = TestDataProvider.get_103();
        CustomerEntity _103Entity = TestDataProvider.get_103Entity();
        when(customerRepository.save(_103Entity)).thenReturn(_103Entity);
        // when
        Customer actual = service.modify(_103);
        // then
        assertThat(actual).usingRecursiveComparison()
            .isEqualTo(_103);

    }

    private static class TestDataProvider {

        public static final Integer UNKNOWN_CUSTOMERNUMBER = 9999;
        public static final Integer _1166 = 1166;
        public static final Integer _1370 = 1370;

        public static final Integer _103 = 103;
        public static final Integer _112 = 112;

        public static String OFFICE_CODE_SAN_FRANCISCO = "1";
        public static String OFFICE_CODE_PARIS = "4";

        public static Customer get_103() {
            return new Customer(_103, "Atelier graphique",
                "Schmitt",
                "Carine ",
                "40.32.2555",
                "54, rue Royale",
                null,
                "Nantes",
                null,
                "44000",
                "France",
                get_1370(),
                21000.0);
        }

        public static Customer get_112() {
            return new Customer(_112, "Signal Gift Stores",
                "King",
                "Jean",
                "7025551838",
                "8489 Strong St.",
                null,
                "Las Vegas",
                "NV",
                "83030",
                "USA",
                get_1166(),
                71800.0);
        }

        public static CustomerEntity get_103Entity() {
            return CustomerEntity.builder()
                .customerNumber(_103)
                .customerName("Atelier graphique")
                .contactLastName("Schmitt")
                .contactFirstName("Carine ")
                .phone("40.32.2555")
                .addressLine1("54, rue Royale")
                .addressLine2(null)
                .city("Nantes")
                .state(null)
                .postalCode("44000")
                .country("France")
                .salesRepEmployeeNumber(get_1370Entity())
                .creditLimit(21000.0)
                .build();
        }

        public static CustomerEntity get_112Entity() {
            return CustomerEntity.builder()
                .customerNumber(_112)
                .customerName("Signal Gift Stores")
                .contactLastName("King")
                .contactFirstName("Jean")
                .phone("7025551838")
                .addressLine1("8489 Strong St.")
                .addressLine2(null)
                .city("Las Vegas")
                .state("NV")
                .postalCode("83030")
                .country("USA")
                .salesRepEmployeeNumber(get_1166Entity())
                .creditLimit(71800.0)
                .build();
        }

        public static Employee get_1370() {
            return new Employee(_1370,
                "Hernandez",
                "Gerard",
                "x2028",
                "ghernande@classicmodelcars.com",
                getParisOfficeModel(),
                null,
                "Sales Rep"
            );
        }

        public static Employee get_1166() {
            return new Employee(
                _1166,
                "Thompson",
                "Leslie",
                "x4065",
                "lthompson@classicmodelcars.com",
                getSanFranciscoOfficeModel(),
                null,
                "Sales Rep"
            );
        }

        public static EmployeeEntity get_1370Entity() {
            return EmployeeEntity.builder()
                .employeeNumber(_1370)
                .lastName("Hernandez")
                .firstName("Gerard")
                .extension("x2028")
                .email("ghernande@classicmodelcars.com")
                .office(getParisOfficeEntity())
                .reportsTo(null)
                .jobTitle("Sales Rep")
                .build();
        }

        public static EmployeeEntity get_1166Entity() {
            return EmployeeEntity.builder()
                .employeeNumber(_1166)
                .lastName("Thompson")
                .firstName("Leslie")
                .extension("x4065")
                .email("lthompson@classicmodelcars.com")
                .office(getSanFranciscoOfficeEntity())
                .reportsTo(null)
                .jobTitle("Sales Rep")
                .build();
        }

        public static Office getSanFranciscoOfficeModel() {
            return new Office(
                OFFICE_CODE_SAN_FRANCISCO,
                "San Francisco",
                "+1 650 219 4782",
                "100 Market Street",
                "Suite 300",
                "CA",
                "USA",
                "94080",
                "NA"
            );
        }

        public static Office getParisOfficeModel() {
            return new Office(
                OFFICE_CODE_PARIS,
                "Paris",
                "+33 14 723 4404",
                "43 Rue Jouffroy D\\'abbans",
                null,
                null,
                "France",
                "75017",
                "EMEA"
            );
        }

        public static OfficeEntity getSanFranciscoOfficeEntity() {
            return OfficeEntity.builder()
                .officeCode(OFFICE_CODE_SAN_FRANCISCO)
                .city("San Francisco")
                .phone("+1 650 219 4782")
                .addressLine1("100 Market Street")
                .addressLine2("Suite 300")
                .state("CA")
                .country("USA")
                .postalCode("94080")
                .territory("NA")
                .build();
        }

        public static OfficeEntity getParisOfficeEntity() {
            return OfficeEntity.builder()
                .officeCode(OFFICE_CODE_PARIS)
                .city("Paris")
                .phone("+33 14 723 4404")
                .addressLine1("43 Rue Jouffroy D\\'abbans")
                .addressLine2(null)
                .state(null)
                .country("France")
                .postalCode("75017")
                .territory("EMEA")
                .build();
        }
    }
}