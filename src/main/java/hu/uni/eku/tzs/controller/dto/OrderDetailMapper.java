package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.OrderDetail;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderDetailMapper {

    OrderDetailDto orderDetail2orderDetailDto(OrderDetail orderDetail);

    OrderDetail orderDetailDto2orderDetail(OrderDetailDto dto);
}
