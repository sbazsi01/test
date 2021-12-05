package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.EmployeeDto;
import hu.uni.eku.tzs.controller.dto.EmployeeMapper;
import hu.uni.eku.tzs.controller.dto.OfficeDto;
import hu.uni.eku.tzs.model.Employee;
import hu.uni.eku.tzs.model.Office;
import hu.uni.eku.tzs.service.EmployeeManager;
import hu.uni.eku.tzs.service.exceptions.EmployeeAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.EmployeeNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeControllerTest {

    @Mock
    private EmployeeManager employeeManager;

    @Mock
    private EmployeeMapper employeeMapper;

    @InjectMocks
    private EmployeeController controller;

    @Test
    void readAllHappyPath() {
        // given
        when(employeeManager.readAllEmployees()).thenReturn(List.of(TestDataProvider.get_1056()));
        when(employeeMapper.employee2employeeDto(any())).thenReturn(TestDataProvider.get_1056Dto());
        Collection<EmployeeDto> expected = List.of(TestDataProvider.get_1056Dto());
        // when
        Collection<EmployeeDto> actual = controller.readAllEmployees();
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void createEmployeeHappyPath() throws EmployeeAlreadyExistsException {
        // given
        Employee _1056 = TestDataProvider.get_1056();
        EmployeeDto _1056Dto = TestDataProvider.get_1056Dto();
        when(employeeMapper.employeeDto2Employee(_1056Dto)).thenReturn(_1056);
        when(employeeManager.record(_1056)).thenReturn(_1056);
        when(employeeMapper.employee2employeeDto(_1056)).thenReturn(_1056Dto);
        // when
        EmployeeDto actual = controller.create(_1056Dto);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(_1056Dto);
    }

    @Test
    void createEmployeeThrowsEmployeeAlreadyExistsException() throws EmployeeAlreadyExistsException {
        // given
        Employee _1056 = TestDataProvider.get_1056();
        EmployeeDto _1056Dto = TestDataProvider.get_1056Dto();
        when(employeeMapper.employeeDto2Employee(_1056Dto)).thenReturn(_1056);
        when(employeeManager.record(_1056)).thenThrow(new EmployeeAlreadyExistsException());
        // when then
        assertThatThrownBy(() -> {
            controller.create(_1056Dto);
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updateHappyPath() {
        // given
        EmployeeDto requestDto = TestDataProvider.get_1056Dto();
        Employee _1056 = TestDataProvider.get_1056();
        when(employeeMapper.employeeDto2Employee(requestDto)).thenReturn(_1056);
        when(employeeManager.modify(_1056)).thenReturn(_1056);
        when(employeeMapper.employee2employeeDto(_1056)).thenReturn(requestDto);
        EmployeeDto expected = TestDataProvider.get_1056Dto();
        // when
        EmployeeDto response = controller.update(requestDto);
        // then
        assertThat(response).usingRecursiveComparison()
            .isEqualTo(expected);
    }

    @Test
    void deleteFromQueryParamHappyPath() throws EmployeeNotFoundException {
        // given
        Employee _1056 = TestDataProvider.get_1056();
        when(employeeManager.readByEmployeeNumber(TestDataProvider._1056_EMPLOYEENUMBER)).thenReturn(_1056);
        doNothing().when(employeeManager).delete(_1056);
        // when
        controller.delete(TestDataProvider._1056_EMPLOYEENUMBER);
    }

    @Test
    void deleteFromQueryParamWhenEmployeeNotFound() throws EmployeeNotFoundException {
        // given
        final Integer notFoundEmployeeNumber = TestDataProvider._1056_EMPLOYEENUMBER;
        doThrow(new EmployeeNotFoundException()).when(employeeManager).readByEmployeeNumber(notFoundEmployeeNumber);
        // when then
        assertThatThrownBy(() -> controller.delete(notFoundEmployeeNumber))
            .isInstanceOf(ResponseStatusException.class);
    }

    public static class TestDataProvider {

        public static final Integer _1056_EMPLOYEENUMBER = 1056;

        public static Employee get_1056() {
            return new Employee(
                _1056_EMPLOYEENUMBER,
                "Patterson",
                "Mary",
                "x4611",
                "mpatterson@classicmodelcars.com",
                OfficeControllerTest.TestDataProvider.getSanFranciscoOfficeModel(),
                new Employee(
                    1188,
                    "Atterson",
                    "Michael",
                    "x5611",
                    "matterson@classicmodelcars.com",
                    OfficeControllerTest.TestDataProvider.getSanFranciscoOfficeModel(),
                    null,
                    "VP Sales"),
                "VP Sales");
        }

        public static EmployeeDto get_1056Dto() {
            return EmployeeDto.builder()
                .employeeNumber(_1056_EMPLOYEENUMBER)
                .lastName("Patterson")
                .firstName("Mary")
                .extension("x4611")
                .email("mpatterson@classicmodelcars.com")
                .office(OfficeControllerTest.TestDataProvider.getSanFranciscoOfficeDto())
                .reportsTo(EmployeeDto.builder()
                    .employeeNumber(1188)
                    .lastName("Atterson")
                    .firstName("Michael")
                    .extension("x5611")
                    .email("matterson@classicmodelcars.com")
                    .office(OfficeControllerTest.TestDataProvider.getSanFranciscoOfficeDto())
                    .reportsTo(null)
                    .jobTitle("VP Sales")
                    .build())
                .jobTitle("VP Sales")
                .build();
        }
    }
}