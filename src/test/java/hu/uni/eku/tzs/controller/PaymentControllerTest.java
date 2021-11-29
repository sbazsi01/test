package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.*;
import hu.uni.eku.tzs.model.Customer;
import hu.uni.eku.tzs.model.Employee;
import hu.uni.eku.tzs.model.Office;
import hu.uni.eku.tzs.model.Payment;
import hu.uni.eku.tzs.service.PaymentManager;
import hu.uni.eku.tzs.service.exceptions.PaymentAlreadyExistsException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Date;
import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class PaymentControllerTest {

    @Mock
    private PaymentManager paymentManager;

    @Mock
    private PaymentMapper paymentMapper;

    @InjectMocks
    private PaymentController controller;

    @Test
    void readPayment() {
        // given
        when(paymentManager.readAll()).thenReturn(List.of(TestDataProvider.getAtelierPaymentModel()));
        when(paymentMapper.payment2paymentDto(any())).thenReturn(TestDataProvider.getAtelierPaymentDto());
        Collection<PaymentDto> expected = List.of(TestDataProvider.getAtelierPaymentDto());
        // when
        Collection<PaymentDto> actual = controller.readAllPayments();
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);


    }

    @Test
    void createPayment() throws PaymentAlreadyExistsException {
        // given
        Payment payment = TestDataProvider.getAtelierPaymentModel();
        PaymentDto paymentDto = TestDataProvider.getAtelierPaymentDto();
        when(paymentMapper.paymentDto2payment(paymentDto)).thenReturn(payment);
        when(paymentManager.record(payment)).thenReturn(payment);
        when(paymentMapper.payment2paymentDto(payment)).thenReturn(paymentDto);
        // when
        PaymentDto actual = controller.create(paymentDto);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(paymentDto);
    }

    @Test
    void createPaymentAlreadyExistsException() throws PaymentAlreadyExistsException {
        // given
        Payment payment = TestDataProvider.getAtelierPaymentModel();
        PaymentDto paymentDto = TestDataProvider.getAtelierPaymentDto();
        when(paymentMapper.paymentDto2payment(paymentDto)).thenReturn(payment);
        when(paymentManager.record(payment)).thenThrow(new PaymentAlreadyExistsException());
        // when then
        assertThatThrownBy(() -> {
            controller.create(paymentDto);
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updatePayment() {
        // given
        PaymentDto requestDto = TestDataProvider.getAtelierPaymentDto();
        Payment payment = TestDataProvider.getAtelierPaymentModel();
        when(paymentMapper.paymentDto2payment(requestDto)).thenReturn(payment);
        when(paymentManager.modify(payment)).thenReturn(payment);
        when(paymentMapper.payment2paymentDto(payment)).thenReturn(requestDto);
        PaymentDto expected = TestDataProvider.getAtelierPaymentDto();
        // when
        PaymentDto response = controller.update(requestDto);
        // then
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(expected);
    }



    private static class TestDataProvider {

        public static Office getParisOfficeModel() {
            return new Office(
                    "4",
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

        public static OfficeDto getParisOfficeDto() {
            return OfficeDto.builder()
                    .officeCode("4")
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

        public static Employee getHernandezEmployeeModel() {
            return new Employee(1370,
                    "Hernandez",
                    "Gerard",
                    "x2028",
                    "ghernande@classicmodelcars.com",
                    getParisOfficeModel(),
                    1102,
                    "Sales Rep"
            );
        }

        public static EmployeeDto getHernandezEmployeeDto() {
            return EmployeeDto.builder()
                    .employeeNumber(1370)
                    .firstName("Hernandez")
                    .lastName("Gerard")
                    .extension("x2028")
                    .email("ghernande@classicmodelcars.com")
                    .office(getParisOfficeDto())
                    .reportsTo(1102)
                    .jobTitle("Sales Rep")
                    .build();
        }

        public static Customer getAtelierModel() {
            return new Customer(103, "Atelier graphique",
                    "Schmitt",
                    "Carine ",
                    "40.32.2555",
                    "54, rue Royale",
                    null,
                    "Nantes",
                    null,
                    "44000",
                    "France",
                    getHernandezEmployeeModel(),
                    21000.0);
        }

        public static Payment getAtelierPaymentModel() {
            return new Payment(
                    getAtelierModel(),
                    "AB661578",
                    new Date(2004,7,28),
                    9415.13
            );
        }

        public static CustomerDto getAtelierDto(){
            return CustomerDto.builder()
                    .customerNumber(103)
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
                    .salesRepEmployeeNumber(getHernandezEmployeeDto())
                    .creditLimit(21000.0)
                    .build();
        }

        public static PaymentDto getAtelierPaymentDto(){
            return new PaymentDto(
                    getAtelierDto(),
                    "AB661578",
                    new Date(2004, 7, 28),
                    9415.13
            );
        }
        
    }
}


