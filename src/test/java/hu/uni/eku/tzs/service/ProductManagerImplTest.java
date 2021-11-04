package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.ProductRepository;
import hu.uni.eku.tzs.dao.entity.ProductEntity;
import hu.uni.eku.tzs.dao.entity.ProductLinesEntity;
import hu.uni.eku.tzs.model.Product;
import hu.uni.eku.tzs.model.ProductLines;
import hu.uni.eku.tzs.service.exceptions.ProductAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ProductNotFoundException;
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
class ProductManagerImplTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductManagerImpl service;

    @Test
    void recordSanFranciscoProduct() throws ProductAlreadyExistsException {
        // given
        Product harleyDavidsonProductModel = TestDataProvider.getHarleyDavidsonProductModel();
        ProductEntity harleyDavidsonProductEntity = TestDataProvider.getHarleyDavidsonProductEntity();

        when(productRepository.findById(any())).thenReturn(Optional.empty());
        when(productRepository.save(any())).thenReturn(harleyDavidsonProductEntity);
        // when
        Product actual = service.record(harleyDavidsonProductModel);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(harleyDavidsonProductModel);
    }

    @Test
    void recordProductWithNullParams() throws ProductAlreadyExistsException {
        // given
        Product alpineRenaultProductModel = TestDataProvider.getAlpineRenaultProductModel();
        ProductEntity alpineRenaultProductEntity = TestDataProvider.getAlpineRenaultProductEntity();

        when(productRepository.findById(TestDataProvider.ALPINE_RENAULT_CODE)).thenReturn(Optional.empty());
        when(productRepository.save(alpineRenaultProductEntity)).thenReturn(alpineRenaultProductEntity);
        // when
        Product actual = service.record(alpineRenaultProductModel);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(alpineRenaultProductModel);
    }

    @Test
    void recordProductAlreadyExistsException() {
        // given
        Product harleyDavidsonProductModel = TestDataProvider.getHarleyDavidsonProductModel();
        ProductEntity harleyDavidsonProductEntity = TestDataProvider.getHarleyDavidsonProductEntity();

        when(productRepository.findById(harleyDavidsonProductModel.getProductCode())).thenReturn(Optional.ofNullable(harleyDavidsonProductEntity));
        // when
        assertThatThrownBy(() -> {
            service.record(harleyDavidsonProductModel);
        }).isInstanceOf(ProductAlreadyExistsException.class);
    }

    @Test
    void readByProductCodeSanFrancisco() throws ProductNotFoundException {
        // given
        when(productRepository.findById(TestDataProvider.HARLEY_DAVIDSON_CODE))
                .thenReturn(Optional.of(TestDataProvider.getHarleyDavidsonProductEntity()));

        Product expected = TestDataProvider.getHarleyDavidsonProductModel();
        Product actual = service.readByProductCode(TestDataProvider.HARLEY_DAVIDSON_CODE);
        // when
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByProductCodeNotFoundException() {
        // given
        when(productRepository.findById("S10_4757")).thenReturn(Optional.empty());
        // when then
        assertThatThrownBy(() -> {
            service.readByProductCode("S10_4757");
        }).isInstanceOf(ProductNotFoundException.class)
                .hasMessageContaining("S10_4757");
    }

    @Test
    void readAllMotorcycles() {
        // given
        List<ProductEntity> productEntities = List.of(
                TestDataProvider.getHarleyDavidsonProductEntity(),
                TestDataProvider.getMotoGuzziProductEntity()
        );
        Collection<Product> expectedProducts = List.of(
                TestDataProvider.getHarleyDavidsonProductModel(),
                TestDataProvider.getMotoGuzziProductModel()
        );
        when(productRepository.findAllByProductLine("Motorcycles")).thenReturn(productEntities);
        // when
        Collection<Product> actualProducts = service.readAllByProductLine("Motorcycles");
        // then
        assertThat(actualProducts)
                .usingRecursiveComparison()
                .isEqualTo(expectedProducts);
    }

    @Test
    void modifyProductSanFrancisco() {
        // given
        Product harleyDavidsonProductModel = TestDataProvider.getHarleyDavidsonProductModel();
        ProductEntity harleyDavidsonProductEntity = TestDataProvider.getHarleyDavidsonProductEntity();
        when(productRepository.save(harleyDavidsonProductEntity)).thenReturn(harleyDavidsonProductEntity);
        // when
        Product actual = service.modify(harleyDavidsonProductModel);
        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(harleyDavidsonProductModel);
    }

    private static class TestDataProvider {

        public static final String HARLEY_DAVIDSON_CODE = "S10_1678";
        private static final String HARLEY_DAVIDSON_NAME = "1969 Harley Davidson Ultimate Chopper";
        private static final String HARLEY_DAVIDSON_DESC = "This replica features working kickstand, front suspension, gear-shift lever, footbrake lever, drive chain, wheels and steering. All parts are particularly delicate due to their precise scale and require special care and attention.";
        public static final String MOTO_GUZZI_CODE = "S10_2016";
        private static final String MOTO_GUZZI_NAME = "1996 Moto Guzzi 1100i";
        private static final String MOTO_GUZZI_DESC = "Official Moto Guzzi logos and insignias, saddle bags located on side of motorcycle, detailed engine, working steering, working suspension, two leather seats, luggage rack, dual exhaust pipes, small saddle bag located on handle bars, two-tone paint with chrome accents, superior die-cast detail , rotating wheels , working kick stand, diecast metal with plastic parts and baked enamel finish.";
        public static final String ALPINE_RENAULT_CODE = "S10_1949";
        private static final String ALPINE_RENAULT_NAME = "1952 Alpine Renault 1300";
        private static final String ALPINE_RENAULT_DESC = "Turnable front wheels; steering function; detailed interior; detailed engine; opening hood; opening trunk; opening doors; and detailed chassis.";

        public static Product getHarleyDavidsonProductModel() {
            return new Product(HARLEY_DAVIDSON_CODE, HARLEY_DAVIDSON_NAME,
                    getMotorcyclesProductLine(), "1:10", "Min Lin Diecast", HARLEY_DAVIDSON_DESC,
                    7933,
                    48.81, 95.7);
        }

        public static ProductEntity getHarleyDavidsonProductEntity() {
            return ProductEntity.builder()
                    .productCode(HARLEY_DAVIDSON_CODE)
                    .productName(HARLEY_DAVIDSON_NAME)
                    .productLine(getMotorcyclesProductLineEntity())
                    .productScale("1:10")
                    .productVendor("Min Lin Diecast")
                    .productDescription(HARLEY_DAVIDSON_DESC)
                    .quantityInStock(7933)
                    .buyPrice(48.81)
                    .MSRP(95.7)
                    .build();
        }

        public static Product getMotoGuzziProductModel() {
            return new Product(MOTO_GUZZI_CODE, MOTO_GUZZI_NAME,
                    getMotorcyclesProductLine(), "1:10", "Highway 66 Mini Classics", MOTO_GUZZI_DESC,
                    6625,
                    68.99, 118.94);
        }

        public static ProductEntity getMotoGuzziProductEntity() {
            return ProductEntity.builder()
                    .productCode(MOTO_GUZZI_CODE)
                    .productName(MOTO_GUZZI_NAME)
                    .productLine(getMotorcyclesProductLineEntity())
                    .productScale("1:10")
                    .productVendor("Highway 66 Mini Classics")
                    .productDescription(MOTO_GUZZI_DESC)
                    .quantityInStock(6625)
                    .buyPrice(68.99)
                    .MSRP(118.94)
                    .build();
        }

        public static Product getAlpineRenaultProductModel() {
            return new Product(ALPINE_RENAULT_CODE, ALPINE_RENAULT_NAME,
                    getClassicCarsProductLine(), "1:10", "Classic Metal Creations", ALPINE_RENAULT_DESC,
                    7305,
                    98.58, 214.3);
        }

        public static ProductEntity getAlpineRenaultProductEntity() {
            return ProductEntity.builder()
                    .productCode(ALPINE_RENAULT_CODE)
                    .productName(ALPINE_RENAULT_NAME)
                    .productLine(getClassicCarsProductLineEntity())
                    .productScale("1:10")
                    .productVendor("Classic Metal Creations")
                    .productDescription(ALPINE_RENAULT_DESC)
                    .quantityInStock(7305)
                    .buyPrice(98.58)
                    .MSRP(214.3)
                    .build();
        }

        public static ProductLines getMotorcyclesProductLine() {
            return new ProductLines("Motorcycles","motor_desc",null, null);
        }

        public static ProductLinesEntity getMotorcyclesProductLineEntity() {
            return ProductLinesEntity.builder()
                    .productLine("Motorcycles")
                    .textDescription("motor_desc")
                    .build();
        }

        public static ProductLines getClassicCarsProductLine() {
            return new ProductLines("Classic Cars","cc_desc",null, null);
        }

        public static ProductLinesEntity getClassicCarsProductLineEntity() {
            return ProductLinesEntity.builder()
                    .productLine("Classic Cars")
                    .textDescription("cc_desc")
                    .build();
        }

    }

}
