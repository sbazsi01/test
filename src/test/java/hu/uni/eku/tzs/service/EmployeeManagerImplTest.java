package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.OfficeRepository;
import hu.uni.eku.tzs.dao.EmployeeRepository;
import hu.uni.eku.tzs.dao.entity.OfficeEntity;
import hu.uni.eku.tzs.dao.entity.EmployeeEntity;
import hu.uni.eku.tzs.model.Office;
import hu.uni.eku.tzs.model.Employee;
import hu.uni.eku.tzs.service.exceptions.EmployeeAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.EmployeeNotFoundException;
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
class EmployeeManagerImplTest {

    @Mock
    EmployeeRepository employeeRepository;

    @InjectMocks
    EmployeeManagerImpl service;

    @Test
    void recordEmployeeHappyPath() throws EmployeeAlreadyExistsException {
        // given
        Employee _1188 = TestDataProvider.get_1188();
        EmployeeEntity _1188Entity = TestDataProvider.get_1188Entity();
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());
        when(employeeRepository.save(any())).thenReturn(_1188Entity);
        // when
        Employee actual = service.record(_1188);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(_1188);
    }

    @Test
    void recordEmployeeUnknownOffice() throws EmployeeAlreadyExistsException {
        // given
        Employee _1056 = TestDataProvider.get_1056();
        EmployeeEntity _1056Entity = TestDataProvider.get_1056Entity();
        when(employeeRepository.findById(TestDataProvider._1056)).thenReturn(Optional.empty());
        when(employeeRepository.save(_1056Entity)).thenReturn(_1056Entity);
        // when
        Employee actual = service.record(_1056);
        // then
        assertThat(actual).usingRecursiveComparison()
            .isEqualTo(_1056);
    }

    @Test
    void recordEmployeeAlreadyExistsException() {
        // given
        Employee _1188 = TestDataProvider.get_1188();
        EmployeeEntity _1188Entity = TestDataProvider.get_1188Entity();
        when(employeeRepository.findById(TestDataProvider._1188)).thenReturn(Optional.ofNullable(_1188Entity));
        // when
        assertThatThrownBy(() -> {
            service.record(_1188);
        }).isInstanceOf(EmployeeAlreadyExistsException.class);
    }

    @Test
    void readByEmployeeNumberHappyPath() throws EmployeeNotFoundException {
        // given
        when(employeeRepository.findById(TestDataProvider._1188))
            .thenReturn(Optional.of(TestDataProvider.get_1188Entity()));
        Employee expected = TestDataProvider.get_1188();
        // when
        Employee actual = service.readByEmployeeNumber(TestDataProvider._1188);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByEmployeeNumberEmployeeNotFoundException() {
        // given
        when(employeeRepository.findById(TestDataProvider.UNKNOWN_EMPLOYEENUMBER)).thenReturn(Optional.empty());
        // when then
        assertThatThrownBy(() -> {
            service.readByEmployeeNumber(TestDataProvider.UNKNOWN_EMPLOYEENUMBER);
        }).isInstanceOf(EmployeeNotFoundException.class)
            .hasMessageContaining(TestDataProvider.UNKNOWN_EMPLOYEENUMBER.toString());
    }

    @Test
    void readAllHappyPath() {
        // given
        List<EmployeeEntity> employeeEntities = List.of(
            TestDataProvider.get_1056Entity(),
            TestDataProvider.get_1188Entity()
        );
        Collection<Employee> expectedEmployees = List.of(
            TestDataProvider.get_1056(),
            TestDataProvider.get_1188()
        );
        when(employeeRepository.findAll()).thenReturn(employeeEntities);
        // when
        Collection<Employee> actualEmployees = service.readAllEmployees();
        // then
        assertThat(actualEmployees)
            .usingRecursiveComparison()
            .isEqualTo(expectedEmployees);
    }

    @Test
    void modifyEmployeeHappyPath() {
        // given
        Employee _1188 = TestDataProvider.get_1188();
        EmployeeEntity _1188Entity = TestDataProvider.get_1188Entity();
        when(employeeRepository.save(_1188Entity)).thenReturn(_1188Entity);
        // when
        Employee actual = service.modify(_1188);
        // then
        assertThat(actual).usingRecursiveComparison()
            .isEqualTo(_1188);

    }

    private static class TestDataProvider {

        public static final Integer UNKNOWN_EMPLOYEENUMBER = 9999;
        public static final Integer _1188 = 1188;
        public static final Integer _1056 = 1056;

        public static Employee get_1056() {
            return new Employee(_1056,"Patterson","Mary","x4611",
                    "mpatterso@classicmodelcars.com", getSanFranciscoOfficeModel(),get_1188(),"VP Sales");
        }

        public static Employee get_1188() {
            return new Employee(_1188,"Firrelli","Julie","x2173",
                    "jfirrelli@classicmodelcars.com", getBostonOfficeModel(),null,"Sales Rep");
        }

        public static EmployeeEntity get_1056Entity() {
            return EmployeeEntity.builder()
                .employeeNumber(_1056)
                .lastName("Patterson")
                .firstName("Mary")
                .extension("x4611")
                .email("mpatterso@classicmodelcars.com")
                .office(getSanFranciscoOfficeEntity())
                .reportsTo(get_1188Entity())
                .jobTitle("VP Sales")
                .build();
        }

        public static EmployeeEntity get_1188Entity() {
            return EmployeeEntity.builder()
                .employeeNumber(_1188)
                .lastName("Firrelli")
                .firstName("Julie")
                .extension("x2173")
                .email("jfirrelli@classicmodelcars.com")
                .office(getBostonOfficeEntity())
                .reportsTo(null)
                .jobTitle("Sales Rep")
                .build();
        }

        public static String OFFICE_CODE_SAN_FRANCISCO = "1";
        public static String OFFICE_CODE_BOSTON = "2";

        public static Office getSanFranciscoOfficeModel() {
            return new Office(OFFICE_CODE_SAN_FRANCISCO,
                "San Francisco",
                "+1 650 219 4782",
                "100 Market Street",
                "Suite 300",
                "CA",
                "USA",
                "94080",
                "NA");
        }

        public static Office getBostonOfficeModel() {
            return new Office(
                OFFICE_CODE_BOSTON,
                "Boston",
                "+1 215 387 0825",
                "1550 Court Place",
                "Suite 102",
                "MA",
                "USA",
                "02107",
                "NA");
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

        public static OfficeEntity getBostonOfficeEntity() {
            return OfficeEntity.builder()
                    .officeCode(OFFICE_CODE_BOSTON)
                    .city("Boston")
                    .phone("+1 215 387 0825")
                    .addressLine1("1550 Court Place")
                    .addressLine2("Suite 102")
                    .state("MA")
                    .country("USA")
                    .postalCode("02107")
                    .territory("NA")
                    .build();
        }
    }
}