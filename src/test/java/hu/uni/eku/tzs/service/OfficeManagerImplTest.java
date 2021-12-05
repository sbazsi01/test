package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.OfficeRepository;
import hu.uni.eku.tzs.dao.entity.OfficeEntity;
import hu.uni.eku.tzs.model.Office;
import hu.uni.eku.tzs.service.exceptions.OfficeAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.OfficeNotFoundException;
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
class OfficeManagerImplTest {

    @Mock
    private OfficeRepository officeRepository;

    @InjectMocks
    private OfficeManagerImpl service;

    @Test
    void recordSanFranciscoOffice() throws OfficeAlreadyExistsException {
        // given
        Office sanFrancisco = TestDataProvider.getSanFranciscoOfficeModel();
        OfficeEntity sanFranciscoEntity = TestDataProvider.getSanFranciscoOfficeEntity();

        when(officeRepository.findById(any())).thenReturn(Optional.empty());
        when(officeRepository.save(any())).thenReturn(sanFranciscoEntity);
        // when
        Office actual = service.record(sanFrancisco);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(sanFrancisco);
    }

    @Test
    void recordOfficeWithNullParams() throws OfficeAlreadyExistsException {
        // given
        Office paris = TestDataProvider.getParisOfficeModel();
        OfficeEntity parisEntity = TestDataProvider.getParisOfficeEntity();

        when(officeRepository.findById(TestDataProvider.OFFICE_CODE_PARIS)).thenReturn(Optional.empty());
        when(officeRepository.save(parisEntity)).thenReturn(parisEntity);
        // when
        Office actual = service.record(paris);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(paris);
    }

    @Test
    void recordOfficeAlreadyExistsException() {
        // given
        Office sanFrancisco = TestDataProvider.getSanFranciscoOfficeModel();
        OfficeEntity sanFranciscoEntity = TestDataProvider.getSanFranciscoOfficeEntity();

        when(officeRepository.findById(sanFrancisco.getOfficeCode())).thenReturn(Optional.ofNullable(sanFranciscoEntity));
        // when
        assertThatThrownBy(() -> {
            service.record(sanFrancisco);
        }).isInstanceOf(OfficeAlreadyExistsException.class);
    }

    @Test
    void readByOfficeCodeSanFrancisco() throws OfficeNotFoundException {
        // given
        when(officeRepository.findById(TestDataProvider.OFFICE_CODE_SAN_FRANCISCO))
                .thenReturn(Optional.of(TestDataProvider.getSanFranciscoOfficeEntity()));

        Office expected = TestDataProvider.getSanFranciscoOfficeModel();
        Office actual = service.readByOfficeCode(TestDataProvider.OFFICE_CODE_SAN_FRANCISCO);
        // when
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByOfficeCodeNotFoundException() {
        // given
        when(officeRepository.findById(TestDataProvider.UNKNOWN_OFFICE_CODE)).thenReturn(Optional.empty());
        // when then
        assertThatThrownBy(() -> {
            service.readByOfficeCode(TestDataProvider.UNKNOWN_OFFICE_CODE);
        }).isInstanceOf(OfficeNotFoundException.class)
                .hasMessageContaining(String.valueOf(TestDataProvider.UNKNOWN_OFFICE_CODE));
    }

    @Test
    void readAllOfficesFromNA() {
        // given
        List<OfficeEntity> officeEntities = List.of(
                TestDataProvider.getSanFranciscoOfficeEntity(),
                TestDataProvider.getBostonOfficeEntity()
        );
        Collection<Office> expectedOffices = List.of(
                TestDataProvider.getSanFranciscoOfficeModel(),
                TestDataProvider.getBostonOfficeModel()
        );
        when(officeRepository.findAll()).thenReturn(officeEntities);
        // when
        Collection<Office> actualOffices = service.readAllOffices();
        // then
        assertThat(actualOffices)
                .usingRecursiveComparison()
                .isEqualTo(expectedOffices);
    }

    @Test
    void modifyOfficeSanFrancisco() {
        // given
        Office sanFrancisco = TestDataProvider.getSanFranciscoOfficeModel();
        OfficeEntity sanFranciscoEntity = TestDataProvider.getSanFranciscoOfficeEntity();
        when(officeRepository.save(sanFranciscoEntity)).thenReturn(sanFranciscoEntity);
        // when
        Office actual = service.modify(sanFrancisco);
        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(sanFrancisco);
    }

    protected static class TestDataProvider {

        public static String OFFICE_CODE_SAN_FRANCISCO = "1";
        public static String OFFICE_CODE_BOSTON = "2";
        public static String OFFICE_CODE_PARIS = "4";
        public static String UNKNOWN_OFFICE_CODE = "10";

        public static Office getSanFranciscoOfficeModel() {
            return new Office(OFFICE_CODE_SAN_FRANCISCO, "San Francisco", "+1 650 219 4782", "100 Market Street",
                    "Suite 300", "CA", "USA", "94080", "NA");
        }

        public static Office getBostonOfficeModel() {
            return new Office(OFFICE_CODE_BOSTON, "Boston", "+1 215 387 0825",
                    "1550 Court Place", "Suite 102", "MA", "USA", "02107", "NA");
        }

        public static Office getParisOfficeModel() {
            return new Office(OFFICE_CODE_PARIS, "Paris", "+33 14 723 4404", "43 Rue Jouffroy D'abbans",
                    null, null, "France", "75017", "EMEA");
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

        public static OfficeEntity getParisOfficeEntity() {
            return OfficeEntity.builder()
                    .officeCode(OFFICE_CODE_PARIS)
                    .city("Paris")
                    .phone("+33 14 723 4404")
                    .addressLine1("43 Rue Jouffroy D'abbans")
                    .country("France")
                    .postalCode("75017")
                    .territory("EMEA")
                    .build();
        }

    }

}
