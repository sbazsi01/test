package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.OrderDetailRepository;
import hu.uni.eku.tzs.dao.entity.OrderDetailEntity;
import hu.uni.eku.tzs.model.OrderDetail;
import hu.uni.eku.tzs.service.exceptions.OrderDetailAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.OrderDetailNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderDetailManagerImpl implements OrderDetailManager {

    private final OrderDetailRepository orderDetailRepository;

    private static OrderDetail convertOrderDetailEntity2Model(OrderDetailEntity orderDetailEntity) {
        return new OrderDetail(
            orderDetailEntity.getOrderNumber(),
                orderDetailEntity.getProductCode(),
                orderDetailEntity.getQuantityOrdered(),
                orderDetailEntity.getPriceEach(),
                orderDetailEntity.getOrderLineNumber()
        );
    }

    private static OrderDetailEntity convertOrderDetailModel2Entity(OrderDetail orderDetail) {
        return new OrderDetailEntity(
                orderDetail.getOrderNumber(),
                orderDetail.getProductCode(),
                orderDetail.getQuantityOrdered(),
                orderDetail.getPriceEach(),
                orderDetail.getOrderLineNumber()
        );
    }

    @Override
    public OrderDetail record(OrderDetail orderDetail) throws OrderDetailAlreadyExistsException {
        if (orderDetailRepository.findById(orderDetail.getOrderNumber()).isPresent()) {
            throw new OrderDetailAlreadyExistsException();
        }
        OrderDetailEntity orderDetailEntity = orderDetailRepository.save(
                OrderDetailEntity.builder()
                        .orderNumber(orderDetail.getOrderNumber())
                        .orderLineNumber(orderDetail.getOrderLineNumber())
                        .productCode(orderDetail.getProductCode())
                        .quantityOrdered(orderDetail.getQuantityOrdered())
                        .priceEach(orderDetail.getPriceEach())
                        .build()
        );
        return convertOrderDetailEntity2Model(orderDetailEntity);
    }

    @Override
    public OrderDetail readByOrderNumber(Integer orderNumber) throws OrderDetailNotFoundException {
        Optional<OrderDetailEntity> entity = orderDetailRepository.findById(orderNumber);
        if (entity.isEmpty()) {
            throw new OrderDetailNotFoundException(
                    String.format("Cannot find order detail with order number %s", orderNumber));
        }

        return convertOrderDetailEntity2Model(entity.get());
    }

    @Override
    public Collection<OrderDetail> readAll() {
        return orderDetailRepository.findAll().stream().map(OrderDetailManagerImpl::convertOrderDetailEntity2Model)
            .collect(Collectors.toList());
    }

    @Override
    public OrderDetail modify(OrderDetail orderDetail) {
        OrderDetailEntity entity = convertOrderDetailModel2Entity(orderDetail);
        return convertOrderDetailEntity2Model(orderDetailRepository.save(entity));
    }

    @Override
    public void delete(OrderDetail orderDetail) {
        orderDetailRepository.delete(convertOrderDetailModel2Entity(orderDetail));

    }

}
