package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.AuthorDto;
import hu.uni.eku.tzs.controller.dto.CustomerDto;
import hu.uni.eku.tzs.controller.dto.CustomerMapper;
import hu.uni.eku.tzs.controller.dto.EmployeeDto;
import hu.uni.eku.tzs.controller.dto.OfficeDto;
import hu.uni.eku.tzs.model.Customer;
import hu.uni.eku.tzs.model.Employee;
import hu.uni.eku.tzs.model.Office;
import hu.uni.eku.tzs.service.CustomerManager;
import hu.uni.eku.tzs.service.exceptions.CustomerAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.CustomerNotFoundException;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CustomerControllerTest {

    @Mock
    private CustomerManager customerManager;

    @Mock
    private CustomerMapper customerMapper;

    @InjectMocks
    private CustomerController controller;

    @Test
    void readAllHappyPath() {
        // given
        when(customerManager.readAllCustomers()).thenReturn(List.of(TestDataProvider.get_103()));
        when(customerMapper.customer2customerDto(any())).thenReturn(TestDataProvider.get_103Dto());
        Collection<CustomerDto> expected = List.of(TestDataProvider.get_103Dto());
        // when
        Collection<CustomerDto> actual = controller.readAllCustomers();
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void createCustomerHappyPath() throws CustomerAlreadyExistsException {
        // given
        Customer _103 = TestDataProvider.get_103();
        CustomerDto _103Dto = TestDataProvider.get_103Dto();
        when(customerMapper.customerDto2Customer(_103Dto)).thenReturn(_103);
        when(customerManager.record(_103)).thenReturn(_103);
        when(customerMapper.customer2customerDto(_103)).thenReturn(_103Dto);
        // when
        CustomerDto actual = controller.create(_103Dto);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(_103Dto);
    }

    @Test
    void createCustomerThrowsCustomerAlreadyExistsException() throws CustomerAlreadyExistsException {
        // given
        Customer _103 = TestDataProvider.get_103();
        CustomerDto _103Dto = TestDataProvider.get_103Dto();
        when(customerMapper.customerDto2Customer(_103Dto)).thenReturn(_103);
        when(customerManager.record(_103)).thenThrow(new CustomerAlreadyExistsException());
        // when then
        assertThatThrownBy(() -> {
            controller.create(_103Dto);
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updateHappyPath() {
        // given
        CustomerDto requestDto = TestDataProvider.get_103Dto();
        Customer _103 = TestDataProvider.get_103();
        when(customerMapper.customerDto2Customer(requestDto)).thenReturn(_103);
        when(customerManager.modify(_103)).thenReturn(_103);
        when(customerMapper.customer2customerDto(_103)).thenReturn(requestDto);
        CustomerDto expected = TestDataProvider.get_103Dto();
        // when
        CustomerDto response = controller.update(requestDto);
        // then
        assertThat(response).usingRecursiveComparison()
            .isEqualTo(expected);
    }


    @Test
    void deleteFromQueryParamHappyPath() throws CustomerNotFoundException {
        // given
        Customer _103 = TestDataProvider.get_103();
        when(customerManager.readByCustomerNumber(TestDataProvider._103)).thenReturn(_103);
        doNothing().when(customerManager).delete(_103);
        // when
        controller.delete(TestDataProvider._103);
        // then is not necessary, mock are checked by default
    }

    @Test
    void deleteFromQueryParamWhenCustomerNotFound() throws CustomerNotFoundException {
        // given
        final Integer notFoundCustomerNumber = TestDataProvider._103;
        doThrow(new CustomerNotFoundException()).when(customerManager).readByCustomerNumber(notFoundCustomerNumber);
        // when then
        assertThatThrownBy(() -> controller.delete(notFoundCustomerNumber))
            .isInstanceOf(ResponseStatusException.class);
    }

    private static class TestDataProvider {

        public static final Integer _1370_EMPLOYEENUMBER = 1370;

        public static Employee get_1370() {
            return new Employee(_1370_EMPLOYEENUMBER,"Hernandez","Gerard","x2028",
                "ghernande@classicmodelcars.com", getParisOfficeModel(),null,"Sales Rep");
        }

        public static EmployeeDto get_1370Dto() {
            return EmployeeDto.builder()
                .employeeNumber(_1370_EMPLOYEENUMBER)
                .lastName("Hernandez")
                .firstName("Gerard")
                .extension("x2028")
                .email("ghernande@classicmodelcars.com")
                .office(getParisOfficeDto())
                .reportsTo(null)
                .jobTitle("Sales Rep")
                .build();
        }

        public static String OFFICE_CODE_PARIS = "4";

        public static Office getParisOfficeModel() {
            return new Office(OFFICE_CODE_PARIS, "Paris", "+33 14 723 4404", "43 Rue Jouffroy D\\'abbans",
                null, null, "France", "75017", "EMEA");
        }

        public static OfficeDto getParisOfficeDto() {
            return OfficeDto.builder()
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

        public static final Integer _103 = 103;

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

        public static CustomerDto get_103Dto() {
            return CustomerDto.builder()
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
                .salesRepEmployeeNumber(get_1370Dto())
                .creditLimit(21000.0)
                .build();
        }
    }
}