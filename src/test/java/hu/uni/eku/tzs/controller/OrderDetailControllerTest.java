package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.OrderDetailDto;
import hu.uni.eku.tzs.controller.dto.OrderDetailMapper;
import hu.uni.eku.tzs.model.OrderDetail;
import hu.uni.eku.tzs.service.OrderDetailManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collection;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
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
        when(orderDetailManager.readAll()).thenReturn(List.of(TestDataProvider.getOrder66()));
        when(orderDetailMapper.orderDetail2orderDetailDto(any())).thenReturn(TestDataProvider.getOrder66Dto());
        Collection<OrderDetailDto> expected = List.of(TestDataProvider.getOrder66Dto());
        // when
        Collection<OrderDetailDto> actual = controller.readAllOrderDetails();
        //then
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);


    }

    private static class TestDataProvider {


        public static OrderDetail getOrder66() {
            return new OrderDetail(
                    null,
                    null,
                    1,
                    1.0,
                    1
            );
        }

        public static OrderDetailDto getOrder66Dto() {
            return OrderDetailDto.builder()
                    .orderNumber(null)
                    .productCode(null)
                    .quantityOrdered(1)
                    .priceEach(1.0)
                    .orderLineNumber(1)
                    .build();
        }
    }
}