package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.PaymentRepository;
import hu.uni.eku.tzs.dao.entity.CustomerEntity;
import hu.uni.eku.tzs.dao.entity.EmployeeEntity;
import hu.uni.eku.tzs.dao.entity.OfficeEntity;
import hu.uni.eku.tzs.dao.entity.PaymentEntity;
import hu.uni.eku.tzs.model.Customer;
import hu.uni.eku.tzs.model.Employee;
import hu.uni.eku.tzs.model.Office;
import hu.uni.eku.tzs.model.Payment;
import hu.uni.eku.tzs.service.exceptions.PaymentAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.PaymentNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.sql.Date;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PaymentManagerImplTest {
    @Mock
    PaymentRepository paymentRepository;

    @InjectMocks
    PaymentManagerImpl service;

    @Test
    void recordPayments() throws PaymentAlreadyExistsException {
        // given
        Payment atelierPaymentModel = TestDataProvider.getAtelierPaymentModel();
        PaymentEntity atelierPaymentEntity = TestDataProvider.getAtelierPaymentEntity();
        when(paymentRepository.findById(any())).thenReturn(Optional.empty());
        when(paymentRepository.save(any())).thenReturn(atelierPaymentEntity);
        // when
        Payment actual = service.record(atelierPaymentModel);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(atelierPaymentModel);
//        assertThat(actual).isEqualToComparingFieldByFieldRecursively(hg2g);
    }

    @Test
    void recordPaymentAlreadyExistsException() {
        // given
        Payment atelierPaymentModel = TestDataProvider.getAtelierPaymentModel();
        PaymentEntity atelierPaymentEntity = TestDataProvider.getAtelierPaymentEntity();
        when(paymentRepository.findById(TestDataProvider.ATELIER)).thenReturn(Optional.ofNullable(atelierPaymentEntity));
        // when
        assertThatThrownBy(() -> {
            service.record(atelierPaymentModel);
        }).isInstanceOf(PaymentAlreadyExistsException.class);
    }

    @Test
    void readByPayments() throws PaymentNotFoundException {
        // given
        when(paymentRepository.findById(TestDataProvider.ATELIER))
                .thenReturn(Optional.of(TestDataProvider.getAtelierPaymentEntity()));
        Payment expected = TestDataProvider.getAtelierPaymentModel();
        // when
        Payment actual = service.readByCheckNumber(TestDataProvider.ATELIER);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByPaymentNotFoundException() {
        // given
        when(paymentRepository.findById(TestDataProvider.UNKNOWN)).thenReturn(Optional.empty());
        // when then
        assertThatThrownBy(() -> {
            service.readByCheckNumber(TestDataProvider.UNKNOWN);
        }).isInstanceOf(PaymentNotFoundException.class);
//                .hasMessageContaining(TestDataProvider.UNKNOWN_NAME);
    }

    @Test
    void readAllPayment() {
        // given
        List<PaymentEntity> paymentEntities = List.of(
                TestDataProvider.getAtelierPaymentEntity(),
                TestDataProvider.getSignalPaymentEntity()
        );
        Collection<Payment> expectedPayment = List.of(
                TestDataProvider.getAtelierPaymentModel(),
                TestDataProvider.getSignalPaymentModel()
        );
        when(paymentRepository.findAll()).thenReturn(paymentEntities);
        // when
        Collection<Payment> actualPayment = service.readAll();
        // then
        assertThat(actualPayment)
                .usingRecursiveComparison()
                .isEqualTo(expectedPayment);
//            .containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @Test
    void modifyPayments() {
        // given
        Payment atelier = TestDataProvider.getAtelierPaymentModel();
        PaymentEntity atelierEntity = TestDataProvider.getAtelierPaymentEntity();
        when(paymentRepository.save(atelierEntity)).thenReturn(atelierEntity);
        // when
        Payment actual = service.modify(atelier);
        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(atelier);

    }

    private static class TestDataProvider {

        public static final String ATELIER = "AB661578";

        public static final String SIGNAL = "AB661579";

        public static final String UNKNOWN = "AB661580";


        public static final Integer _1166 = 1166;
        public static final Integer _1370 = 1370;

        public static final Integer _103 = 103;
        public static final Integer _112 = 112;

        public static String OFFICE_CODE_SAN_FRANCISCO = "1";
        public static String OFFICE_CODE_PARIS = "4";

        public static Customer getAtelierModel() {
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
                    getHernandezEmployeeModel(),
                    21000.0);
        }

        public static Customer getSignalModel() {
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
                    getThompsonEmployeeModel(),
                    71800.0);
        }

        public static CustomerEntity getAtelierEntity() {
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
                    .salesRepEmployeeNumber(getHernandezEmployeeEntity())
                    .creditLimit(21000.0)
                    .build();
        }

        public static CustomerEntity getSignalEntity() {
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
                    .salesRepEmployeeNumber(getThompsonEmployeeEntity())
                    .creditLimit(71800.0)
                    .build();
        }

        public static Employee getHernandezEmployeeModel() {
            return new Employee(_1370,
                    "Hernandez",
                    "Gerard",
                    "x2028",
                    "ghernande@classicmodelcars.com",
                    getParisOfficeModel(),
                    1102,
                    "Sales Rep"
            );
        }

        public static Employee getThompsonEmployeeModel() {
            return new Employee(
                    _1166,
                    "Thompson",
                    "Leslie",
                    "x4065",
                    "lthompson@classicmodelcars.com",
                    getSanFranciscoOfficeModel(),
                    1143,
                    "Sales Rep"
            );
        }

        public static EmployeeEntity getHernandezEmployeeEntity() {
            return EmployeeEntity.builder()
                    .employeeNumber(_1370)
                    .lastName("Hernandez")
                    .firstName("Gerard")
                    .extension("x2028")
                    .email("ghernande@classicmodelcars.com")
                    .office(getParisOfficeEntity())
                    .reportsTo(1102)
                    .jobTitle("Sales Rep")
                    .build();
        }

        public static EmployeeEntity getThompsonEmployeeEntity() {
            return EmployeeEntity.builder()
                    .employeeNumber(_1166)
                    .lastName("Thompson")
                    .firstName("Leslie")
                    .extension("x4065")
                    .email("lthompson@classicmodelcars.com")
                    .office(getSanFranciscoOfficeEntity())
                    .reportsTo(1143)
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
        
        public static Payment getAtelierPaymentModel() {
            return new Payment(
                    getAtelierModel(),
                    ATELIER,
                    new Date(2004,7,28),
                    9415.13
            );
        }

        public static PaymentEntity getAtelierPaymentEntity(){
            return PaymentEntity.builder()
                    .customerNumber(getAtelierEntity())
                    .checkNumber(ATELIER)
                    .paymentDate(new Date(2004,7,28))
                    .amount(9415.13)
                    .build();
        }

        public static Payment getSignalPaymentModel() {
            return new Payment(
                    getSignalModel(),
                    SIGNAL,
                    new Date(2004,9,28),
                    9425.13
            );
        }

        public static PaymentEntity getSignalPaymentEntity(){
            return PaymentEntity.builder()
                    .customerNumber(getSignalEntity())
                    .checkNumber(SIGNAL)
                    .paymentDate(new Date(2004,9,28))
                    .amount(9425.13)
                    .build();
        }

    }
}
