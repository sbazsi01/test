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
        when(customerManager.readAll()).thenReturn(List.of(TestDataProvider.get_102()));
        when(customerMapper.customer2customerDto(any())).thenReturn(TestDataProvider.get_102Dto());
        Collection<CustomerDto> expected = List.of(TestDataProvider.get_102Dto());
        // when
        Collection<CustomerDto> actual = controller.readAllCustomers();
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void createCustomerHappyPath() throws CustomerAlreadyExistsException {
        // given
        Customer _102 = TestDataProvider.get_102();
        CustomerDto _102Dto = TestDataProvider.get_102Dto();
        when(customerMapper.customerDto2Customer(_102Dto)).thenReturn(_102);
        when(customerManager.record(_102)).thenReturn(_102);
        when(customerMapper.customer2customerDto(_102)).thenReturn(_102Dto);
        // when
        CustomerDto actual = controller.create(_102Dto);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(_102Dto);
    }

    @Test
    void createCustomerThrowsCustomerAlreadyExistsException() throws CustomerAlreadyExistsException {
        // given
        Customer _102 = TestDataProvider.get_102();
        CustomerDto _102Dto = TestDataProvider.get_102Dto();
        when(customerMapper.customerDto2Customer(_102Dto)).thenReturn(_102);
        when(customerManager.record(_102)).thenThrow(new CustomerAlreadyExistsException());
        // when then
        assertThatThrownBy(() -> {
            controller.create(_102Dto);
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updateHappyPath() {
        // given
        CustomerDto requestDto = TestDataProvider.get_102Dto();
        Customer _102 = TestDataProvider.get_102();
        when(customerMapper.customerDto2Customer(requestDto)).thenReturn(_102);
        when(customerManager.modify(_102)).thenReturn(_102);
        when(customerMapper.customer2customerDto(_102)).thenReturn(requestDto);
        CustomerDto expected = TestDataProvider.get_102Dto();
        // when
        CustomerDto response = controller.update(requestDto);
        // then
        assertThat(response).usingRecursiveComparison()
            .isEqualTo(expected);
    }


    @Test
    void deleteFromQueryParamHappyPath() throws CustomerNotFoundException {
        // given
        Customer _102 = TestDataProvider.get_102();
        when(customerManager.readByCustomerNumber(TestDataProvider._102)).thenReturn(_102);
        doNothing().when(customerManager).delete(_102);
        // when
        controller.delete(TestDataProvider._102);
        // then is not necessary, mock are checked by default
    }

    @Test
    void deleteFromQueryParamWhenCustomerNotFound() throws CustomerNotFoundException {
        // given
        final String notFoundCustomerNumber = TestDataProvider._102;
        doThrow(new CustomerNotFoundException()).when(customerManager).readByCustomerNumber(notFoundCustomerNumber);
        // when then
        assertThatThrownBy(() -> controller.delete(notFoundCustomerNumber))
            .isInstanceOf(ResponseStatusException.class);
    }

    private static class TestDataProvider {
//(103,'Atelier graphique','Schmitt','Carine ','40.32.2555','54, rue Royale',NULL,'Nantes',NULL,'44000','France',1370,21000),
// (112,'Signal Gift Stores','King','Jean','7025551838','8489 Strong St.',NULL,'Las Vegas','NV','83030','USA',1166,71800)
        //(1370,'Hernandez','Gerard','x2028','ghernande@classicmodelcars.com','4',1102,'Sales Rep')


        public static final Integer _1370_EMPLOYEENUMBER = 1370;

        public static Employee get_1370() {
            return new Employee(_1370_EMPLOYEENUMBER,"Hernandez","Gerard","x2028",
                "ghernande@classicmodelcars.com", getParisOfficeModel(),1102,"Sales Rep");
        }

        public static EmployeeDto get_1370Dto() {
            return EmployeeDto.builder()
                .employeeNumber(_1370_EMPLOYEENUMBER)
                .lastName("Hernandez")
                .firstName("Gerard")
                .extension("x2028")
                .email("ghernande@classicmodelcars.com")
                .office(getParisOfficeDto())
                .reportsTo(1102)
                .jobTitle("Sales Rep")
                .build();
        }

        public static String OFFICE_CODE_PARIS = "4";

        //('4','Paris','+33 14 723 4404','43 Rue Jouffroy D\'abbans',NULL,NULL,'France','75017','EMEA')
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


        public static final String _102 = "102";

        public static Customer get_102() {
            return new Customer(_102, "Atelier graphique",
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

        public static CustomerDto get_102Dto() {
            return CustomerDto.builder()
                .customerNumber(_102)
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