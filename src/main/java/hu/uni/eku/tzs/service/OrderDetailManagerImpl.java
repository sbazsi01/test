package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.OrderDetailRepository;
import hu.uni.eku.tzs.dao.entity.OrderDetailEntity;
import hu.uni.eku.tzs.dao.entity.OrderDetailId;
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
                OrderManagerImpl.co
                //OrderManagerImpl.convertOrderEntity2Model(orderDetailEntity.getOrderNumber()),
                ProductManagerImpl.convertProductEntity2Model(orderDetailEntity.getProductCode()),
                orderDetailEntity.getQuantityOrdered(),
                orderDetailEntity.getPriceEach(),
                orderDetailEntity.getOrderLineNumber()
        );
    }

    private static OrderDetailEntity convertOrderDetailModel2Entity(OrderDetail orderDetail) {
        return OrderDetailEntity.builder()
                .orderNumber(OrderManagerImpl.convertOrderModel2Entity(orderDetail.getOrderNumber()))
                .productCode(ProductManagerImpl.convertProductModel2Entity(orderDetail.getProductCode()))
                .orderLineNumber(orderDetail.getOrderLineNumber())
                .quantityOrdered(orderDetail.getQuantityOrdered())
                .priceEach(orderDetail.getPriceEach())
                .build();
    }

    @Override
    public OrderDetail record(OrderDetail orderDetail) throws OrderDetailAlreadyExistsException {

        /* OrderDetailId orderDetailId = new OrderDetailId(orderDetail.getOrderNumber().getOrderNumber(),
            orderDetail.getProductCode().getProductCode());
        if (orderDetailRepository.findById(orderDetailId).isPresent()) {
            throw new OrderDetailAlreadyExistsException();
        }*/

        OrderDetailEntity orderDetailEntity = orderDetailRepository.save(
                OrderDetailEntity.builder()
                        .orderNumber(OrderManagerImpl.convertOrderModel2Entity(orderDetail.getOrderNumber()))
                        .productCode(ProductManagerImpl.convertProductModel2Entity(orderDetail.getProductCode()))
                        .orderLineNumber(orderDetail.getOrderLineNumber())
                        .quantityOrdered(orderDetail.getQuantityOrdered())
                        .priceEach(orderDetail.getPriceEach())
                        .build()
        );
        return convertOrderDetailEntity2Model(orderDetailEntity);
    }

    @Override
    public OrderDetail readByOrderDetailId(OrderDetailId orderDetailId) throws OrderDetailNotFoundException {
        Optional<OrderDetailEntity> entity = orderDetailRepository.findById(orderDetailId);
        if (entity.isEmpty()) {
            throw new OrderDetailNotFoundException(
                    String.format("Cannot find order detail with order number %s and product code %s",
                            orderDetailId.orderNumber, orderDetailId.productCode));
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