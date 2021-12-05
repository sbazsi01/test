package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.BookDto;
import hu.uni.eku.tzs.controller.dto.ProductLinesDto;
import hu.uni.eku.tzs.controller.dto.ProductLinesMapper;
import hu.uni.eku.tzs.model.Book;
import hu.uni.eku.tzs.model.ProductLines;
import hu.uni.eku.tzs.service.ProductLinesManager;
import hu.uni.eku.tzs.service.exceptions.BookAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ProductLinesAlreadyExistsException;
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
class ProductLinesControllerTest {
    @Mock
    private ProductLinesManager productLinesManager;

    @Mock
    private ProductLinesMapper productLinesMapper;

    @InjectMocks
    private ProductLinesController controller;

    @Test
    void readClassicCars() {
        // given
        when(productLinesManager.readAll()).thenReturn(List.of(TestDataProvider.getClassicCarsModel()));
        when(productLinesMapper.productLines2productLinesDto(any())).thenReturn(TestDataProvider.getClassicCarsDto());
        Collection<ProductLinesDto> expected = List.of(TestDataProvider.getClassicCarsDto());
        // when
        Collection<ProductLinesDto> actual = controller.readAllProductLines();
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);


    }

    @Test
    void createClassicCars() throws ProductLinesAlreadyExistsException{
        // given
        ProductLines classicCars = TestDataProvider.getClassicCarsModel();
        ProductLinesDto classicCarsDto = TestDataProvider.getClassicCarsDto();
        when(productLinesMapper.productLinesDto2productLines(classicCarsDto)).thenReturn(classicCars);
        when(productLinesManager.record(classicCars)).thenReturn(classicCars);
        when(productLinesMapper.productLines2productLinesDto(classicCars)).thenReturn(classicCarsDto);
        // when
        ProductLinesDto actual = controller.create(classicCarsDto);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(classicCarsDto);
    }

    @Test
    void createProductLinesAlreadyExistsException() throws ProductLinesAlreadyExistsException {
        // given
        ProductLines classicCars = TestDataProvider.getClassicCarsModel();
        ProductLinesDto classicCarsDto = TestDataProvider.getClassicCarsDto();
        when(productLinesMapper.productLinesDto2productLines(classicCarsDto)).thenReturn(classicCars);
        when(productLinesManager.record(classicCars)).thenThrow(new ProductLinesAlreadyExistsException());
        // when then
        assertThatThrownBy(() -> {
            controller.create(classicCarsDto);
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updateClassicCars() {
        // given
        ProductLinesDto requestDto = TestDataProvider.getClassicCarsDto();
        ProductLines classicCars = TestDataProvider.getClassicCarsModel();
        when(productLinesMapper.productLinesDto2productLines(requestDto)).thenReturn(classicCars);
        when(productLinesManager.modify(classicCars)).thenReturn(classicCars);
        when(productLinesMapper.productLines2productLinesDto(classicCars)).thenReturn(requestDto);
        ProductLinesDto expected = TestDataProvider.getClassicCarsDto();
        // when
        ProductLinesDto response = controller.update(requestDto);
        // then
        assertThat(response).usingRecursiveComparison()
                .isEqualTo(expected);
    }



    public static class TestDataProvider {

        public static final String TEXT = "Attention car enthusiasts: Make your wildest car ownership dreams come true. Whether you are looking for classic muscle cars, dream sports cars or movie-inspired miniatures, you will find great choices in this category. These replicas feature superb attention to detail and craftsmanship and offer features such as working steering system, opening forward compartment, opening rear trunk with removable spare wheel, 4-wheel independent spring suspension, and so on. The models range in size from 1:10 to 1:24 scale and include numerous limited edition and several out-of-production vehicles. All models include a certificate of authenticity from their manufacturers and come fully assembled and ready for display in the home or office.";

        public static final String NAME = "ClassicCars";

        public static ProductLines getClassicCarsModel() {
            return new ProductLines(NAME,TEXT,null, null);
        }

        public static ProductLinesDto getClassicCarsDto() {
            return ProductLinesDto.builder()
                    .productLine(NAME)
                    .textDescription(TEXT)
                    .htmlDescription(null)
                    .image(null)
                    .build();
        }

    }
}
