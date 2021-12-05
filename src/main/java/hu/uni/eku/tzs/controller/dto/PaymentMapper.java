package hu.uni.eku.tzs.controller.dto;

import hu.uni.eku.tzs.model.Payment;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PaymentMapper {

    PaymentDto payment2paymentDto(Payment payment);

    Payment paymentDto2payment(PaymentDto dto);
}

