package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.CustomerDto;
import hu.uni.eku.tzs.controller.dto.OrderDto;
import hu.uni.eku.tzs.controller.dto.OrderMapper;
import hu.uni.eku.tzs.model.Customer;
import hu.uni.eku.tzs.model.Order;
import hu.uni.eku.tzs.service.OrderManager;
import hu.uni.eku.tzs.service.exceptions.OrderAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.OrderNotFoundException;
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
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class OrderControllerTest {

    @Mock
    private OrderManager orderManager;

    @Mock
    private OrderMapper orderMapper;

    @InjectMocks
    private OrderController controller;

    @Test
    void readAllHappyPath() {
        // given
        when(orderManager.readAll()).thenReturn(List.of(OrderControllerTest.TestDataProvider.getTestOrder()));
        when(orderMapper.order2orderDto(any())).thenReturn(OrderControllerTest.TestDataProvider.getOrderDto());
        Collection<OrderDto> expected = List.of(OrderControllerTest.TestDataProvider.getOrderDto());
        // when
        Collection<OrderDto> actual = controller.readAllOrders();
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);


    }

    @Test
    void createOrderHappyPath() throws OrderAlreadyExistsException {
        // given
        Order testOrder = OrderControllerTest.TestDataProvider.getTestOrder();
        OrderDto testOrderDto = OrderControllerTest.TestDataProvider.getOrderDto();
        when(orderMapper.orderDto2Order(testOrderDto)).thenReturn(testOrder);
        when(orderManager.record(testOrder)).thenReturn(testOrder);
        when(orderMapper.order2orderDto(testOrder)).thenReturn(testOrderDto);
        // when
        OrderDto actual = controller.create(testOrderDto);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testOrderDto);
    }

    @Test
    void createOrderThrowsOrderAlreadyExistsException() throws OrderAlreadyExistsException {
        // given
        Order testOrder = OrderControllerTest.TestDataProvider.getTestOrder();
        OrderDto testOrderDtO = OrderControllerTest.TestDataProvider.getOrderDto();
        when(orderMapper.orderDto2Order(testOrderDtO)).thenReturn(testOrder);
        when(orderManager.record(testOrder)).thenThrow(new OrderAlreadyExistsException());
        // when then
        assertThatThrownBy(() -> controller.create(testOrderDtO)).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updateHappyPath() {
        // given
        OrderDto requestDto = OrderControllerTest.TestDataProvider.getOrderDto();
        Order orderTest = OrderControllerTest.TestDataProvider.getTestOrder();
        when(orderMapper.orderDto2Order(requestDto)).thenReturn(orderTest);
        when(orderManager.modify(orderTest)).thenReturn(orderTest);
        when(orderMapper.order2orderDto(orderTest)).thenReturn(requestDto);
        OrderDto expected = OrderControllerTest.TestDataProvider.getOrderDto();
        // when
        OrderDto response = controller.update(requestDto);
        // then
        assertThat(response).usingRecursiveComparison()
            .isEqualTo(expected);
    }

    @Test
    void deleteFromQueryParamHappyPath() throws OrderNotFoundException {
        // given
        Order orderTest = OrderControllerTest.TestDataProvider.getTestOrder();
        when(orderManager.readByOrderNumber(OrderControllerTest.TestDataProvider.testOrderNumber)).thenReturn(orderTest);
        doNothing().when(orderManager).delete(orderTest);
        // when
        controller.delete(OrderControllerTest.TestDataProvider.testOrderNumber);
        // then is not necessary, mock are checked by default
    }

    @Test
    void deleteFromQueryParamWhenOrderNotFound() throws OrderNotFoundException {
        // given
        final int notFoundOrderNumber = OrderControllerTest.TestDataProvider.testOrderNumber;
        doThrow(new OrderNotFoundException("")).when(orderManager).readByOrderNumber(notFoundOrderNumber);
        // when then
        assertThatThrownBy(() -> controller.delete(notFoundOrderNumber))
            .isInstanceOf(ResponseStatusException.class);
    }

    public static class TestDataProvider {

        public static final int testOrderNumber = 1000;
        public static Customer c = new Customer(1, "test", "test", "test", "test", "test", "test", "test", "test", "test", "test", null, 1.5);

        public static Order getTestOrder() {
            return new Order(testOrderNumber, "2021.09.01",
                "2021.09.02", "2021.09.03",
                "delivered", "ok", c);
        }

        public static CustomerDto cDto() {
            return CustomerDto.builder()
                .customerNumber(1)
                .customerName("test")
                .contactLastName("test")
                .contactFirstName("test")
                .phone("test")
                .addressLine1("test")
                .addressLine2("test")
                .city("test")
                .state("test")
                .postalCode("test")
                .country("test")
                .salesRepEmployeeNumber(null)
                .creditLimit(1.5)
                .build();
        }

        public static OrderDto getOrderDto() {
            return OrderDto.builder()
                .orderNumber(testOrderNumber)
                .orderDate("2021.09.01")
                .requiredDate("2021.09.02")
                .shippedDate("2021.09.03")
                .status("delivered")
                .comments("ok")
                .customer(cDto())
                .build();
        }
    }
}
