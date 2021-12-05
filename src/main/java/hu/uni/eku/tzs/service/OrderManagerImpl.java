package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.OrderRepository;
import hu.uni.eku.tzs.dao.entity.OrderEntity;
import hu.uni.eku.tzs.model.Order;
import hu.uni.eku.tzs.service.exceptions.OrderAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.OrderNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderManagerImpl implements OrderManager {

    private final OrderRepository orderRepository;

    public static Order convertOrderEntity2Model(OrderEntity orderEntity) {
        return new Order(
                orderEntity.getOrderNumber(),
                orderEntity.getOrderDate(),
                orderEntity.getRequiredDate(),
                orderEntity.getShippedDate(),
                orderEntity.getStatus(),
                orderEntity.getComments(),
                CustomerManagerImpl.convertCustomerEntity2Model(orderEntity.getCustomer())
        );
    }

    public static OrderEntity convertOrderModel2Entity(Order order) {
        return OrderEntity.builder()
                .orderNumber(order.getOrderNumber())
                .orderDate(order.getOrderDate())
                .requiredDate(order.getRequiredDate())
                .shippedDate(order.getShippedDate())
                .status(order.getStatus())
                .comments(order.getComments())
                .customer(CustomerManagerImpl.convertCustomerModel2Entity(order.getCustomerNumber()))
                .build();
    }

    @Override
    public Order record(Order order) throws OrderAlreadyExistsException {
        if (orderRepository.findById(order.getOrderNumber()).isPresent()) {
            throw new OrderAlreadyExistsException();
        }
        OrderEntity orderEntity = orderRepository.save(
                OrderEntity.builder()
                        .orderNumber(order.getOrderNumber())
                        .orderDate(order.getOrderDate())
                        .requiredDate(order.getRequiredDate())
                        .shippedDate(order.getShippedDate())
                        .status(order.getStatus())
                        .comments(order.getComments())
                        //.customer(order.getCustomerNumber())
                        .build()
        );
        return convertOrderEntity2Model(orderEntity);
    }

    @Override
    public Order readByOrderNumber(Integer orderNumber) throws OrderNotFoundException {
        Optional<OrderEntity> entity = orderRepository.findById(orderNumber);
        if (entity.isEmpty()) {
            throw new OrderNotFoundException(String.format("Cannot find order with order number: %s", orderNumber));
        }

        return convertOrderEntity2Model(entity.get());
    }

    @Override
    public Collection<Order> readAll() {
        return orderRepository.findAll().stream().map(OrderManagerImpl::convertOrderEntity2Model)
                .collect(Collectors.toList());
    }

    @Override
    public Order modify(Order order) {
        OrderEntity entity = convertOrderModel2Entity(order);
        return convertOrderEntity2Model(orderRepository.save(entity));
    }

    @Override
    public void delete(Order order) {
        orderRepository.delete(convertOrderModel2Entity(order));

    }

}