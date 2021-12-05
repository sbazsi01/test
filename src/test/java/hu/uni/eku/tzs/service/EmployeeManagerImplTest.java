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
        Employee _1370 = TestDataProvider.get_1370();
        EmployeeEntity _1370Entity = TestDataProvider.get_1370Entity();
        when(employeeRepository.findById(any())).thenReturn(Optional.empty());
        when(employeeRepository.save(any())).thenReturn(_1370Entity);
        // when
        Employee actual = service.record(_1370);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(_1370);
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
        Employee _1370 = TestDataProvider.get_1370();
        EmployeeEntity _1370Entity = TestDataProvider.get_1370Entity();
        when(employeeRepository.findById(TestDataProvider._1370)).thenReturn(Optional.ofNullable(_1370Entity));
        // when
        assertThatThrownBy(() -> {
            service.record(_1370);
        }).isInstanceOf(EmployeeAlreadyExistsException.class);
    }

    @Test
    void readByEmployeeNumberHappyPath() throws EmployeeNotFoundException {
        // given
        when(employeeRepository.findById(TestDataProvider._1370))
            .thenReturn(Optional.of(TestDataProvider.get_1370Entity()));
        Employee expected = TestDataProvider.get_1370();
        // when
        Employee actual = service.readByEmployeeNumber(TestDataProvider._1370);
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
            TestDataProvider.get_1370Entity()
        );
        Collection<Employee> expectedEmployees = List.of(
            TestDataProvider.get_1056(),
            TestDataProvider.get_1370()
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
        Employee _1370 = TestDataProvider.get_1370();
        EmployeeEntity _1370Entity = TestDataProvider.get_1370Entity();
        when(employeeRepository.save(_1370Entity)).thenReturn(_1370Entity);
        // when
        Employee actual = service.modify(_1370);
        // then
        assertThat(actual).usingRecursiveComparison()
            .isEqualTo(_1370);

    }

    protected static class TestDataProvider {

        public static final Integer UNKNOWN_EMPLOYEENUMBER = 9999;
        public static final Integer _1370 = 1;
        public static final Integer _1056 = 1056;

        public static Employee get_1056() {
            return new Employee(_1056,"Patterson","Mary","x4611",
                    "mpatterso@classicmodelcars.com", OfficeManagerImplTest.TestDataProvider.getSanFranciscoOfficeModel(),get_1370(),"VP Sales");
        }

        public static Employee get_1370() {
            return new Employee(1,
                "Hernandez",
                "Gerard",
                "x2028",
                "ghernande@classicmodelcars.com",
                OfficeManagerImplTest.TestDataProvider.getParisOfficeModel(),
                null,
                "Sales Rep"
            );
        }

        public static EmployeeEntity get_1056Entity() {
            return EmployeeEntity.builder()
                .employeeNumber(_1056)
                .lastName("Patterson")
                .firstName("Mary")
                .extension("x4611")
                .email("mpatterso@classicmodelcars.com")
                .office(OfficeManagerImplTest.TestDataProvider.getSanFranciscoOfficeEntity())
                .reportsTo(get_1370Entity())
                .jobTitle("VP Sales")
                .build();
        }

        public static EmployeeEntity get_1370Entity() {
            return EmployeeEntity.builder()
                .employeeNumber(1)
                .lastName("Hernandez")
                .firstName("Gerard")
                .extension("x2028")
                .email("ghernande@classicmodelcars.com")
                .office(OfficeManagerImplTest.TestDataProvider.getParisOfficeEntity())
                .reportsTo(null)
                .jobTitle("Sales Rep")
                .build();
        }
    }
}