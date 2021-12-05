package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.OrderDetailRepository;
import hu.uni.eku.tzs.dao.entity.OrderDetailEntity;
import hu.uni.eku.tzs.dao.entity.OrderDetailId;
import hu.uni.eku.tzs.model.OrderDetail;
import hu.uni.eku.tzs.service.exceptions.OrderDetailAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.OrderDetailNotFoundException;
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
class OrderDetailManagerImplTest {

    @Mock
    OrderDetailRepository orderDetailRepository;

    @InjectMocks
    OrderDetailManagerImpl service;

    @Test
    void recordOrderDetailHappyPath() throws OrderDetailAlreadyExistsException {

        // given
        OrderDetail o66 = TestDataProvider.getOrder66();
        OrderDetailEntity entity = TestDataProvider.getOrder66Entity();
        when(orderDetailRepository.findById(any())).thenReturn(Optional.empty());
        when(orderDetailRepository.save(any())).thenReturn(entity);
        // when
        OrderDetail actual = service.record(o66,TestDataProvider.orderNumber,TestDataProvider.productCode);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(o66);
        // assertThat(actual).isEqualToComparingFieldByFieldRecursively(hg2g);
    }


    @Test
    void recordOrderDetailAlreadyExistsException() {

        // given
        OrderDetailId orderDetailId = new OrderDetailId(TestDataProvider.orderNumber,TestDataProvider.productCode);
        OrderDetail hg2g = TestDataProvider.getOrder66();
        OrderDetailEntity hg2gEntity = TestDataProvider.getOrder66Entity();
        when(orderDetailRepository.findById(orderDetailId)).thenReturn(Optional.ofNullable(hg2gEntity));
        // when
        assertThatThrownBy(() -> {
            service.record(hg2g,orderDetailId);
        }).isInstanceOf(OrderDetailAlreadyExistsException.class);
    }

    @Test
    void readOrderNumberHappyPath() throws OrderDetailNotFoundException {

        // given
        OrderDetailId orderDetailId = new OrderDetailId(TestDataProvider.orderNumber,TestDataProvider.productCode);
        when(orderDetailRepository.findById(orderDetailId))
            .thenReturn(Optional.of(TestDataProvider.getOrder66Entity()));
        OrderDetail expected = TestDataProvider.getOrder66();
        // when
        OrderDetail actual = service.readByOrderDetailId(orderDetailId);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByOrderNumberOrderDetailNotFoundException() {
        // given
        OrderDetailId orderDetailId = new OrderDetailId(TestDataProvider.WrongOrderNumber,TestDataProvider.productCode);
        when(orderDetailRepository.findById(orderDetailId)).thenReturn(Optional.empty());
        // when then
        assertThatThrownBy(() -> {
            service.readByOrderDetailId(orderDetailId);
        }).isInstanceOf(OrderDetailNotFoundException.class)
            .hasMessageContaining(String.format("Cannot find order detail with product code %s and order number %s",
                 orderDetailId.productCode,orderDetailId.orderNumber));
    }

    @Test
    void readAllHappyPath() {
        // given
        List<OrderDetailEntity> orderDetailEntities = List.of(
            TestDataProvider.getOrder66Entity()
        );
        Collection<OrderDetail> expectedOrderDetails = List.of(
            TestDataProvider.getOrder66()
        );
        when(orderDetailRepository.findAll()).thenReturn(orderDetailEntities);
        // when
        Collection<OrderDetail> actualOrderDetails = service.readAll();
        // then
        assertThat(actualOrderDetails)
            .usingRecursiveComparison()
            .isEqualTo(expectedOrderDetails);
    }

    @Test
    void modifyOrderDetailHappyPath() {
        // given
        OrderDetail hg2g = TestDataProvider.getOrder66();
        OrderDetailEntity hg2gEntity = TestDataProvider.getOrder66Entity();
        when(orderDetailRepository.save(hg2gEntity)).thenReturn(hg2gEntity);
        // when
        OrderDetail actual = service.modify(hg2g);
        // then
        assertThat(actual).usingRecursiveComparison()
            .isEqualTo(hg2g);

    }

    private static class TestDataProvider {

        public static final Integer WrongOrderNumber = -1;

        public static final Integer orderNumber = 66;

        public static final String productCode = "1";

        public static final Integer quantityOrdered = 1;

        public static final Double priceEach = 1.0;

        public static final Integer orderLineNumber = 1;

        public static OrderDetail getOrder66() {

            return new OrderDetail(OrderManagerImplTest.TestDataProvider.getTestOrder(), ProductManagerImplTest.TestDataProvider.getHarleyDavidsonProductModel(), quantityOrdered, priceEach, orderLineNumber);
        }

        public static OrderDetailEntity getOrder66Entity() {
            return new OrderDetailEntity(OrderManagerImplTest.TestDataProvider.getTestOrderEntity(), ProductManagerImplTest.TestDataProvider.getHarleyDavidsonProductEntity(), quantityOrdered, priceEach, orderLineNumber);
        }

    }
}