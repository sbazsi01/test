package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.OrderRepository;
import hu.uni.eku.tzs.dao.entity.OrderEntity;
import hu.uni.eku.tzs.model.Order;
import hu.uni.eku.tzs.service.exceptions.OrderAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.OrderNotFoundException;
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
class OrderManagerImplTest {

    @Mock
    OrderRepository OrderRepository;

    @InjectMocks
    OrderManagerImpl service;

    @Test
    void recordOrderHappyPath() throws OrderAlreadyExistsException {
        // given
        Order hg2g = TestDataProvider.getTestOrder();
        OrderEntity hg2gEntity = TestDataProvider.getTestOrderEntity();
        when(OrderRepository.findById(any())).thenReturn(Optional.empty());
        when(OrderRepository.save(any())).thenReturn(hg2gEntity);
        // when
        Order actual = service.record(hg2g);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(hg2g);
//        assertThat(actual).isEqualToComparingFieldByFieldRecursively(hg2g);
    }

    @Test
    void recordOrderUnknownAuthor() throws OrderAlreadyExistsException {
        // given
        Order dune = TestDataProvider.getTestOrder();
        OrderEntity duneEntity = TestDataProvider.getTestOrderEntity();
        when(OrderRepository.findById(TestDataProvider.testOrderNumber)).thenReturn(Optional.empty());
        when(OrderRepository.save(duneEntity)).thenReturn(duneEntity);
        // when
        Order actual = service.record(dune);
        // then
        assertThat(actual).usingRecursiveComparison()
            .isEqualTo(dune);
    }

    @Test
    void recordOrderAlreadyExistsException() {
        // given
        Order hg2g = TestDataProvider.getTestOrder();
        OrderEntity hg2gEntity = TestDataProvider.getTestOrderEntity();
        when(OrderRepository.findById(TestDataProvider.testOrderNumber)).thenReturn(Optional.ofNullable(hg2gEntity));
        // when
        assertThatThrownBy(() -> {
            service.record(hg2g);
        }).isInstanceOf(OrderAlreadyExistsException.class);
    }

    @Test
    void readByIsbnHappyPath() throws OrderNotFoundException {
        // given
        when(OrderRepository.findById(TestDataProvider.testOrderNumber))
            .thenReturn(Optional.of(TestDataProvider.getTestOrderEntity()));
        Order expected = TestDataProvider.getTestOrder();
        // when
        Order actual = service.readByOrderNumber(TestDataProvider.testOrderNumber);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByIsbnOrderNotFoundException() {
        // given
        when(OrderRepository.findById(TestDataProvider.wrongOrderNumber)).thenReturn(Optional.empty());
        // when then
        assertThatThrownBy(() -> {
            service.readByOrderNumber(TestDataProvider.wrongOrderNumber);
        }).isInstanceOf(OrderNotFoundException.class)
            .hasMessageContaining(TestDataProvider.wrongOrderNumberString);
    }

    @Test
    void readAllHappyPath() {
        // given
        List<OrderEntity> OrderEntities = List.of(
            TestDataProvider.getTestOrderEntity()
        );
        Collection<Order> expectedOrders = List.of(
            TestDataProvider.getTestOrder()
        );
        when(OrderRepository.findAll()).thenReturn(OrderEntities);
        // when
        Collection<Order> actualOrders = service.readAll();
        // then
        assertThat(actualOrders)
            .usingRecursiveComparison()
            .isEqualTo(expectedOrders);
//            .containsExactlyInAnyOrderElementsOf(expectedOrders);
    }

    @Test
    void modifyOrderHappyPath() {
        // given
        Order hg2g = TestDataProvider.getTestOrder();
        OrderEntity hg2gEntity = TestDataProvider.getTestOrderEntity();
        when(OrderRepository.save(hg2gEntity)).thenReturn(hg2gEntity);
        // when
        Order actual = service.modify(hg2g);
        // then
        assertThat(actual).usingRecursiveComparison()
            .isEqualTo(hg2g);

    }

    protected static class TestDataProvider {

        public static final int testOrderNumber = 1000;
        public static final int wrongOrderNumber = -1;
        public static final String wrongOrderNumberString = "-1";

        public static Order getTestOrder() {
            return new Order(testOrderNumber, "2021.09.01",
                "2021.09.02", "2021.09.03",
                "delivered", "ok", CustomerManagerImplTest.TestDataProvider.getTestCustomer());
        }

        public static OrderEntity getTestOrderEntity() {
            return OrderEntity.builder()
                .orderNumber(testOrderNumber)
                .orderDate("2021.09.01")
                .requiredDate("2021.09.02")
                .shippedDate("2021.09.03")
                .status("delivered")
                .comments("ok")
                .customer(CustomerManagerImplTest.TestDataProvider.getTestCustomerEntity())
                .build();
        }
    }
}