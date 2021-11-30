package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.dao.entity.OrderDetailId;
import hu.uni.eku.tzs.model.OrderDetail;
import hu.uni.eku.tzs.service.exceptions.OrderDetailAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.OrderDetailNotFoundException;

import java.util.Collection;

public interface OrderDetailManager {

    OrderDetail record(OrderDetail orderDetail) throws OrderDetailAlreadyExistsException;

    OrderDetail readByOrderDetailId(OrderDetailId orderDetailId) throws OrderDetailNotFoundException;

    Collection<OrderDetail> readAll();

    OrderDetail modify(OrderDetail orderDetail);

    void delete(OrderDetail orderDetail) throws OrderDetailNotFoundException;

}
