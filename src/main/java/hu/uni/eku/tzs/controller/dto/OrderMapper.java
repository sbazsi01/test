package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.Order;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    OrderDto order2orderDto(Order order);

    Order orderDto2Order(OrderDto dto);

}