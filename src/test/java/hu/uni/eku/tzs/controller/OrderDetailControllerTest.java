/*package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.OrderDetailDto;
import hu.uni.eku.tzs.controller.dto.OrderDetailMapper;
import hu.uni.eku.tzs.model.OrderDetail;
import hu.uni.eku.tzs.service.OrderDetailManager;
import hu.uni.eku.tzs.service.exceptions.OrderDetailAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.OrderDetailNotFoundException;
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
class OrderDetailControllerTest {

    @Mock
    private OrderDetailManager orderDetailManager;

    @Mock
    private OrderDetailMapper orderDetailMapper;

    @InjectMocks
    private OrderDetailController controller;

    @Test
    void readAllHappyPath() {
        // given
        when(orderDetailManager.readAll()).thenReturn(List.of(TestDataProvider.testOrderDetail()));
        when(orderDetailMapper.orderDetail2orderDetailDto(any())).thenReturn(TestDataProvider.testOrderDetailDto());
        Collection<OrderDetailDto> expected = List.of(TestDataProvider.testOrderDetailDto());
        // when
        Collection<OrderDetailDto> actual = controller.readAllOrderDetails();
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);


    }

    @Test
    void createOrderDetailHappyPath() throws OrderDetailAlreadyExistsException {

        // given
        OrderDetail test = TestDataProvider.testOrderDetail();
        OrderDetailDto testDto = TestDataProvider.testOrderDetailDto();
        when(orderDetailMapper.orderDetailDto2orderDetail(testDto)).thenReturn(test);
        when(orderDetailManager.record(test)).thenReturn(test);
        when(orderDetailMapper.orderDetail2orderDetailDto(test)).thenReturn(testDto);
        // when
        OrderDetailDto actual = controller.create(testDto);
        // then
        assertThat(actual).usingRecursiveComparison().isEqualTo(testDto);
    }

    @Test
    void createOrderDetailThrowsOrderDetailAlreadyExistsException() throws OrderDetailAlreadyExistsException {

        // given
        OrderDetail test = TestDataProvider.testOrderDetail();
        OrderDetailDto duneDto = TestDataProvider.testOrderDetailDto();
        when(orderDetailMapper.orderDetailDto2orderDetail(duneDto)).thenReturn(test);
        when(orderDetailManager.record(test)).thenThrow(new OrderDetailAlreadyExistsException());
        // when then
        assertThatThrownBy(() -> {
            controller.create(duneDto);
        }).isInstanceOf(ResponseStatusException.class);
    }

    @Test
    void updateHappyPath() {

        // given
        OrderDetailDto requestDto = TestDataProvider.testOrderDetailDto();
        OrderDetail dune = TestDataProvider.testOrderDetail();
        when(orderDetailMapper.orderDetailDto2orderDetail(requestDto)).thenReturn(dune);
        when(orderDetailManager.modify(dune)).thenReturn(dune);
        when(orderDetailMapper.orderDetail2orderDetailDto(dune)).thenReturn(requestDto);
        OrderDetailDto expected = TestDataProvider.testOrderDetailDto();
        // when
        OrderDetailDto response = controller.update(requestDto);
        // then
        assertThat(response).usingRecursiveComparison()
            .isEqualTo(expected);
    }


    @Test
    void deleteFromQueryParamHappyPath() throws OrderDetailNotFoundException {
        // given
        OrderDetail test = TestDataProvider.testOrderDetail();
        when(orderDetailManager.readByOrderNumber(TestDataProvider.testOrderDetail().getOrderNumber())).thenReturn(test);
        doNothing().when(orderDetailManager).delete(test);
        // when
        controller.delete(TestDataProvider.testOrderDetail().getOrderNumber());
        // then is not necessary, mock are checked by default
    }

    @Test
    void deleteFromQueryParamWhenOrderDetailNotFound() throws OrderDetailNotFoundException {
        // given
        final Integer notFoundOrderDetailOrderNumber = TestDataProvider.testOrderDetail().getOrderNumber();
        doThrow(new OrderDetailNotFoundException()).when(orderDetailManager.readByOrderNumber(notFoundOrderDetailOrderNumber));
        // when then
        assertThatThrownBy(() -> controller.delete(notFoundOrderDetailOrderNumber))
            .isInstanceOf(ResponseStatusException.class);
    }

    private static class TestDataProvider {
        
        public static OrderDetail testOrderDetail() {
            return new OrderDetail(1,"1",1,1.0,1);
        }

        public static OrderDetailDto testOrderDetailDto() {
            return new OrderDetailDto(1,"1",1,1.0,1);
        }
    }


}*/