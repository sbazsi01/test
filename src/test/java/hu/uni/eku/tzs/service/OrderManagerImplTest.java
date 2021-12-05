package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.AuthorRepository;
import hu.uni.eku.tzs.dao.OrderRepository;
import hu.uni.eku.tzs.dao.entity.AuthorEntity;
import hu.uni.eku.tzs.dao.entity.BookEntity;
import hu.uni.eku.tzs.dao.entity.CustomerEntity;
import hu.uni.eku.tzs.dao.entity.EmployeeEntity;
import hu.uni.eku.tzs.dao.entity.OrderEntity;
import hu.uni.eku.tzs.model.Author;
import hu.uni.eku.tzs.model.Book;
import hu.uni.eku.tzs.model.Customer;
import hu.uni.eku.tzs.model.Employee;
import hu.uni.eku.tzs.model.Order;
import hu.uni.eku.tzs.service.exceptions.BookAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.BookNotFoundException;
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
    OrderRepository orderRepository;

    @InjectMocks
    OrderManagerImpl service;

    @Test
    void recordOrderHappyPath() throws OrderAlreadyExistsException {
        // given

        Order order66 = TestDataProvider.getOrder66();
        OrderEntity order66Entity = TestDataProvider.getOrder66Entity();
        when(orderRepository.findById(any())).thenReturn(Optional.empty());
        when(orderRepository.save(any())).thenReturn(order66Entity);
        // when
        Order actual = service.record(order66);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(order66);
//        assertThat(actual).isEqualToComparingFieldByFieldRecursively(hg2g);
    }

    @Test
    void recordOrderAlreadyExistsException() {
        // given
        Order hg2g = TestDataProvider.getOrder66();
        OrderEntity hg2gEntity = TestDataProvider.getOrder66Entity();
        when(orderRepository.findById(TestDataProvider.getOrder66().getOrderNumber())).thenReturn(Optional.ofNullable(hg2gEntity));
        // when
        assertThatThrownBy(() -> {
            service.record(hg2g);
        }).isInstanceOf(OrderAlreadyExistsException.class);
    }

    @Test
    void readByOrderNumberHappyPath() throws OrderNotFoundException {
        // given
        when(orderRepository.findById(TestDataProvider.getOrder66().getOrderNumber()))
                .thenReturn(Optional.of(TestDataProvider.getOrder66Entity()));
        Order expected = TestDataProvider.getOrder66();
        // when
        Order actual = service.readByOrderNumber(TestDataProvider.getOrder66().getOrderNumber());
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    @Test
    void readByOrderNumberOrderNotFound() {
        // given
        when(orderRepository.findById(999)).thenReturn(Optional.empty());
        // when then
        assertThatThrownBy(() -> {
            service.readByOrderNumber(999);
        }).isInstanceOf(OrderNotFoundException.class)
                .hasMessageContaining("999");
    }

    @Test
    void readAllHappyPath() {
        // given
        List<OrderEntity> bookEntities = List.of(
                TestDataProvider.getOrder66Entity()
        );
        Collection<Order> expectedOrders = List.of(
                TestDataProvider.getOrder66()
        );
        when(orderRepository.findAll()).thenReturn(bookEntities);
        // when
        Collection<Order> actualBooks = service.readAll();
        // then
        assertThat(actualBooks)
                .usingRecursiveComparison()
                .isEqualTo(expectedOrders);
//            .containsExactlyInAnyOrderElementsOf(expectedBooks);
    }

    /*@Test
    void modifyBookHappyPath() {
        // given
        Order hg2g = TestDataProvider.getOrder66();
        OrderEntity hg2gEntity = TestDataProvider.getOrder66Entity();
        when(orderRepository.save(hg2gEntity)).thenReturn(hg2gEntity);
        // when
        Order actual = service.modify(hg2g);
        // then
        assertThat(actual).usingRecursiveComparison()
            .isEqualTo(hg2g);

    }*/

    private static class TestDataProvider {

        public static Order getOrder66() {


            Customer c = new Customer(
                    1,
                    "PATRIK",
                    "MEGÖLI",
                    "MAGAT",
                    "MEG",
                    "MA",
                    "PATRIK",
                    "MEGÖLI",
                    "MAGAT",
                    "MEG",
                    "MA",
                    null,
                    1.1
            );
            return new Order(
                    1,
                    "",
                    "",
                    "",
                    "",
                    "",
                    c
            );
        }

        public static OrderEntity getOrder66Entity() {

            CustomerEntity c = new CustomerEntity(
                    1,
                    "PATRIK",
                    "MEGÖLI",
                    "MAGAT",
                    "MEG",
                    "MA",
                    "PATRIK",
                    "MEGÖLI",
                    "MAGAT",
                    "MEG",
                    "MA",
                    null,
                    1.1
            );
            return new OrderEntity(
                    1,
                    "",
                    "",
                    "",
                    "",
                    "",
                    c
            );
        }
    }
}