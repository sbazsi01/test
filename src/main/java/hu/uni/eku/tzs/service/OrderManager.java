package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Order;
import hu.uni.eku.tzs.service.exceptions.OrderAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.OrderNotFoundException;

import java.util.Collection;

public interface OrderManager {

    Order record(Order order) throws OrderAlreadyExistsException;

    Order readByOrderNumber(Integer orderNumber) throws OrderNotFoundException;

    Collection<Order> readAll();

    Order modify(Order order);

    void delete(Order order) throws OrderNotFoundException;

}
