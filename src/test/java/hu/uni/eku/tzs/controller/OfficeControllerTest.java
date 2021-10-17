package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.OfficeDto;
import hu.uni.eku.tzs.controller.dto.OfficeMapper;
import hu.uni.eku.tzs.model.Office;
import hu.uni.eku.tzs.service.OfficeManager;
import hu.uni.eku.tzs.service.exceptions.OfficeAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.OfficeNotFoundException;
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
class OfficeControllerTest {

    @Mock
    private OfficeManager officeManager;

    @Mock
    private OfficeMapper officeMapper;

    @InjectMocks
    private OfficeController controller;

    @Test
    void readAllSanFrancisco() {
        when(officeManager.readAll()).thenReturn(List.of(TestDataProvider.getSanFranciscoOfficeModel()));
        when(officeMapper.office2officeDto(any())).thenReturn(TestDataProvider.getSanFranciscoOfficeDto());

        Collection<OfficeDto> expected = List.of(TestDataProvider.getSanFranciscoOfficeDto());

        Collection<OfficeDto> actual = controller.readAllOffices();

        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void createOfficeSanFrancisco() throws OfficeAlreadyExistsException {
        OfficeDto sanFranciscoDto = TestDataProvider.getSanFranciscoOfficeDto();
        Office sanFrancisco = TestDataProvider.getSanFranciscoOfficeModel();
        when(officeMapper.officeDto2Office(sanFranciscoDto)).thenReturn(sanFrancisco);
        when(officeManager.record(sanFrancisco)).thenReturn(sanFrancisco);
        when(officeMapper.office2officeDto(sanFrancisco)).thenReturn(sanFranciscoDto);

        OfficeDto actual = controller.create(sanFranciscoDto);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(sanFranciscoDto);
    }

    @Test
    void createOfficeThrowsAlreadyExistsException() throws OfficeAlreadyExistsException {
        OfficeDto sanFranciscoDto = TestDataProvider.getSanFranciscoOfficeDto();
        Office sanFrancisco = TestDataProvider.getSanFranciscoOfficeModel();
        when(officeMapper.officeDto2Office(sanFranciscoDto)).thenReturn(sanFrancisco);
        when(officeManager.record(sanFrancisco)).thenThrow(new OfficeAlreadyExistsException());

        assertThatThrownBy(() -> controller.create(sanFranciscoDto))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updateSanFrancisco() {
        OfficeDto requestDto = TestDataProvider.getSanFranciscoOfficeDto();
        Office sanFrancisco = TestDataProvider.getSanFranciscoOfficeModel();
        when(officeMapper.officeDto2Office(requestDto)).thenReturn(sanFrancisco);
        when(officeManager.modify(sanFrancisco)).thenReturn(sanFrancisco);
        when(officeMapper.office2officeDto(sanFrancisco)).thenReturn(requestDto);

        OfficeDto expected = TestDataProvider.getSanFranciscoOfficeDto();

        OfficeDto actual = controller.update(requestDto);

        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void deleteFromQueryParamSanFrancisco() throws OfficeNotFoundException {
        Office sanFrancisco = TestDataProvider.getSanFranciscoOfficeModel();
        when(officeManager.readByOfficeCode(sanFrancisco.getOfficeCode())).thenReturn(sanFrancisco);
        doNothing().when(officeManager).delete(sanFrancisco);

        controller.delete(TestDataProvider.OFFICE_CODE_SAN_FRANCISCO);
    }

    @Test
    void deleteFromQueryParamWhenOfficeNotFound() throws OfficeNotFoundException {
        final int notFoundOfficeCode = TestDataProvider.OFFICE_CODE_SAN_FRANCISCO;
        doThrow(new OfficeNotFoundException()).when(officeManager).readByOfficeCode(notFoundOfficeCode);

        assertThatThrownBy(() -> controller.delete(notFoundOfficeCode))
                .isInstanceOf(ResponseStatusException.class);
    }

    private static class TestDataProvider {

        public static int OFFICE_CODE_SAN_FRANCISCO = 1;

        public static Office getSanFranciscoOfficeModel() {
            return new Office(OFFICE_CODE_SAN_FRANCISCO, "San Francisco", "+1 650 219 4782", "100 Market Street",
                    "Suite 300", "CA", "USA", "94080", "NA");
        }

        public static OfficeDto getSanFranciscoOfficeDto() {
            return OfficeDto.builder()
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

    }

}
