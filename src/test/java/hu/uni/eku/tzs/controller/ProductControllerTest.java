package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.ProductDto;
import hu.uni.eku.tzs.controller.dto.ProductLinesDto;
import hu.uni.eku.tzs.controller.dto.ProductMapper;
import hu.uni.eku.tzs.model.Product;
import hu.uni.eku.tzs.model.ProductLines;
import hu.uni.eku.tzs.service.ProductManager;
import hu.uni.eku.tzs.service.exceptions.ProductAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ProductNotFoundException;
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
class ProductControllerTest {

    @Mock
    private ProductManager productManager;

    @Mock
    private ProductMapper productMapper;

    @InjectMocks
    private ProductController controller;

    @Test
    void readAllSanFrancisco() {
        // given
        when(productManager.readAll()).thenReturn(List.of(TestDataProvider.getHarleyDavidsonProductModel()));
        when(productMapper.product2productDto(any())).thenReturn(TestDataProvider.getHarleyDavidsonProductDto());
        // when
        Collection<ProductDto> expected = List.of(TestDataProvider.getHarleyDavidsonProductDto());

        Collection<ProductDto> actual = controller.readAllProducts();
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void createProductSanFrancisco() throws ProductAlreadyExistsException {
        // given
        ProductDto harleyDavidsonProductDto = TestDataProvider.getHarleyDavidsonProductDto();
        Product harleyDavidsonProductModel = TestDataProvider.getHarleyDavidsonProductModel();
        when(productMapper.productDto2product(harleyDavidsonProductDto)).thenReturn(harleyDavidsonProductModel);
        when(productManager.record(harleyDavidsonProductModel)).thenReturn(harleyDavidsonProductModel);
        when(productMapper.product2productDto(harleyDavidsonProductModel)).thenReturn(harleyDavidsonProductDto);
        // when
        ProductDto actual = controller.create(harleyDavidsonProductDto);
        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(harleyDavidsonProductDto);
    }

    @Test
    void createProductThrowsAlreadyExistsException() throws ProductAlreadyExistsException {
        // given
        ProductDto harleyDavidsonProductDto = TestDataProvider.getHarleyDavidsonProductDto();
        Product harleyDavidsonProductModel = TestDataProvider.getHarleyDavidsonProductModel();
        when(productMapper.productDto2product(harleyDavidsonProductDto)).thenReturn(harleyDavidsonProductModel);
        when(productManager.record(harleyDavidsonProductModel)).thenThrow(new ProductAlreadyExistsException());
        // when
        assertThatThrownBy(() -> controller.create(harleyDavidsonProductDto))
                .isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updateSanFrancisco() {
        // given
        ProductDto harleyDavidsonProductDto = TestDataProvider.getHarleyDavidsonProductDto();
        Product harleyDavidsonProductModel = TestDataProvider.getHarleyDavidsonProductModel();
        when(productMapper.productDto2product(harleyDavidsonProductDto)).thenReturn(harleyDavidsonProductModel);
        when(productManager.modify(harleyDavidsonProductModel)).thenReturn(harleyDavidsonProductModel);
        when(productMapper.product2productDto(harleyDavidsonProductModel)).thenReturn(harleyDavidsonProductDto);
        // when
        ProductDto expected = TestDataProvider.getHarleyDavidsonProductDto();

        ProductDto actual = controller.update(harleyDavidsonProductDto);
        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(expected);
    }

    @Test
    void deleteFromQueryParamSanFrancisco() throws ProductNotFoundException {
        // given
        Product harleyDavidsonProductModel = TestDataProvider.getHarleyDavidsonProductModel();
        when(productManager.readByProductCode(harleyDavidsonProductModel.getProductCode())).thenReturn(harleyDavidsonProductModel);
        doNothing().when(productManager).delete(harleyDavidsonProductModel);
        // when then
        controller.delete(TestDataProvider.HARLEY_DAVIDSON_CODE);
    }

    @Test
    void deleteFromQueryParamWhenProductNotFound() throws ProductNotFoundException {
        // given
        final String notFoundProductCode = TestDataProvider.HARLEY_DAVIDSON_CODE;
        doThrow(new ProductNotFoundException()).when(productManager).readByProductCode(notFoundProductCode);
        // when then
        assertThatThrownBy(() -> controller.delete(notFoundProductCode))
                .isInstanceOf(ResponseStatusException.class);
    }

    private static class TestDataProvider {

        public static final String HARLEY_DAVIDSON_CODE = "S10_1678";
        private static final String HARLEY_DAVIDSON_NAME = "1969 Harley Davidson Ultimate Chopper";
        private static final String HARLEY_DAVIDSON_DESC = "This replica features working kickstand, front suspension, gear-shift lever, footbrake lever, drive chain, wheels and steering. All parts are particularly delicate due to their precise scale and require special care and attention.";

        public static Product getHarleyDavidsonProductModel() {
            return new Product(HARLEY_DAVIDSON_CODE, HARLEY_DAVIDSON_NAME,
                    getMotorcyclesProductLine(), "1:10", "Min Lin Diecast", HARLEY_DAVIDSON_DESC, 7933,
                    48.81, 95.7);
        }

        public static ProductDto getHarleyDavidsonProductDto() {
            return ProductDto.builder()
                    .productCode(HARLEY_DAVIDSON_CODE)
                    .productName(HARLEY_DAVIDSON_NAME)
                    .productLine(getMotorcyclesProductLineDto())
                    .productScale("1:10")
                    .productVendor("Min Lin Diecast")
                    .productDescription(HARLEY_DAVIDSON_DESC)
                    .quantityInStock(7933)
                    .buyPrice(48.81)
                    .MSRP(95.7)
                    .build();
        }

        public static ProductLines getMotorcyclesProductLine() {
            return new ProductLines("Motorcycles","motor_desc",null, null);
        }

        public static ProductLinesDto getMotorcyclesProductLineDto() {
            return ProductLinesDto.builder()
                    .productLine("Motorcycles")
                    .textDescription("motor_desc")
                    .build();
        }

    }

}
