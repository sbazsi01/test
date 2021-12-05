package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.controller.dto.ProductLinesDto;
import hu.uni.eku.tzs.dao.AuthorRepository;
import hu.uni.eku.tzs.dao.ProductLinesRepository;
import hu.uni.eku.tzs.dao.entity.AuthorEntity;
import hu.uni.eku.tzs.dao.entity.BookEntity;
import hu.uni.eku.tzs.dao.entity.ProductLinesEntity;
import hu.uni.eku.tzs.model.Author;
import hu.uni.eku.tzs.model.Book;
import hu.uni.eku.tzs.model.ProductLines;
import hu.uni.eku.tzs.service.exceptions.BookAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.BookNotFoundException;
import hu.uni.eku.tzs.service.exceptions.ProductLinesAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.ProductLinesNotFoundException;
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
class ProductLinesManagerImplTest {
    @Mock
    ProductLinesRepository productLinesRepository;

    @InjectMocks
    ProductLinesManagerImpl service;

    @Test
    void recordClassicCars() throws ProductLinesAlreadyExistsException {
        // given
        ProductLines classicCars = TestDataProvider.getClassicCarsModel();
        ProductLinesEntity classicCarsEntity = TestDataProvider.getClassicCarsEntity();
        when(productLinesRepository.findById(any())).thenReturn(Optional.empty());
        when(productLinesRepository.save(any())).thenReturn(classicCarsEntity);
        // when
        ProductLines actual = service.record(classicCars);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(classicCars);
//        assertThat(actual).isEqualToComparingFieldByFieldRecursively(hg2g);
    }

    @Test
    void recordProductLinesAlreadyExistsException() {
        // given
        ProductLines classicCars = TestDataProvider.getClassicCarsModel();
        ProductLinesEntity classicCarsEntity = TestDataProvider.getClassicCarsEntity();
        when(productLinesRepository.findById(TestDataProvider.CLASSICCARS_NAME)).thenReturn(Optional.ofNullable(classicCarsEntity));
        // when
        assertThatThrownBy(() -> {
            service.record(classicCars);
        }).isInstanceOf(ProductLinesAlreadyExistsException.class);
    }

    @Test
    void readByProductLineClassicCars() throws ProductLinesNotFoundException {
        // given
        when(productLinesRepository.findById(TestDataProvider.CLASSICCARS_NAME))
                .thenReturn(Optional.of(TestDataProvider.getClassicCarsEntity()));
        ProductLines expected = TestDataProvider.getClassicCarsModel();
        // when
        ProductLines actual = service.readByProductLine(TestDataProvider.CLASSICCARS_NAME);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByProductLineNotFoundException() {
        // given
        when(productLinesRepository.findById(TestDataProvider.UNKNOWN_NAME)).thenReturn(Optional.empty());
        // when then
        assertThatThrownBy(() -> {
            service.readByProductLine(TestDataProvider.UNKNOWN_NAME);
        }).isInstanceOf(ProductLinesNotFoundException.class);
//                .hasMessageContaining(TestDataProvider.UNKNOWN_NAME);
    }

    @Test
    void readAllProductLines() {
        // given
        List<ProductLinesEntity> productLinesEntities = List.of(
                TestDataProvider.getClassicCarsEntity(),
                TestDataProvider.getMotorCycleEntity()
        );
        Collection<ProductLines> expectedProductLines = List.of(
                TestDataProvider.getClassicCarsModel(),
                TestDataProvider.getMotorCycleModel()
        );
        when(productLinesRepository.findAll()).thenReturn(productLinesEntities);
        // when
        Collection<ProductLines> actualProductLines = service.readAll();
        // then
        assertThat(actualProductLines)
                .usingRecursiveComparison()
                .isEqualTo(expectedProductLines);
//            .containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    @Test
    void modifyClassicCars() {
        // given
        ProductLines classicCars = TestDataProvider.getClassicCarsModel();
        ProductLinesEntity classicCarsEntity = TestDataProvider.getClassicCarsEntity();
        when(productLinesRepository.save(classicCarsEntity)).thenReturn(classicCarsEntity);
        // when
        ProductLines actual = service.modify(classicCars);
        // then
        assertThat(actual).usingRecursiveComparison()
                .isEqualTo(classicCars);

    }

    protected static class TestDataProvider {

        public static final String CLASSICCARS_TEXT = "Attention car enthusiasts: Make your wildest car ownership dreams come true. Whether you are looking for classic muscle cars, dream sports cars or movie-inspired miniatures, you will find great choices in this category. These replicas feature superb attention to detail and craftsmanship and offer features such as working steering system, opening forward compartment, opening rear trunk with removable spare wheel, 4-wheel independent spring suspension, and so on. The models range in size from 1:10 to 1:24 scale and include numerous limited edition and several out-of-production vehicles. All models include a certificate of authenticity from their manufacturers and come fully assembled and ready for display in the home or office.";

        public static final String CLASSICCARS_NAME = "ClassicCars";

        public static final String MOTORCYCLE_TEXT = "Our motorcycles are state of the art replicas of classic as well as contemporary motorcycle legends such as Harley Davidson, Ducati and Vespa. Models contain stunning details such as official logos, rotating wheels, working kickstand, front suspension, gear-shift lever, footbrake lever, and drive chain. Materials used include diecast and plastic. The models range in size from 1:10 to 1:50 scale and include numerous limited edition and several out-of-production vehicles. All models come fully assembled and ready for display in the home or office. Most include a certificate of authenticity.";

        public static final String MOTORCYCLE_NAME = "MotorCycle";

        public static final String UNKNOWN_NAME = "Planes";

        public static ProductLines getClassicCarsModel() {
            return new ProductLines(CLASSICCARS_NAME,CLASSICCARS_TEXT,null, null);
        }

        public static ProductLinesEntity getClassicCarsEntity() {
            return ProductLinesEntity.builder()
                    .productLine(CLASSICCARS_NAME)
                    .textDescription(CLASSICCARS_TEXT)
                    .build();
        }

        public static ProductLines getMotorCycleModel () {
            return new ProductLines(MOTORCYCLE_TEXT,MOTORCYCLE_NAME,null, null);
        }

        public static ProductLinesEntity getMotorCycleEntity () {
            return ProductLinesEntity.builder()
                    .productLine(MOTORCYCLE_TEXT)
                    .textDescription(MOTORCYCLE_NAME)
                    .build();
        }

    }
}
