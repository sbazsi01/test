package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.OrderDto;
import hu.uni.eku.tzs.controller.dto.OrderMapper;
import hu.uni.eku.tzs.model.Order;
import hu.uni.eku.tzs.service.OrderManager;
import hu.uni.eku.tzs.service.exceptions.OrderAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.OrderNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@Api(tags = "Orders")
@RequestMapping("/orders")
@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderManager orderManager;

    private final OrderMapper orderMapper;

    @ApiOperation("Read All")
    @GetMapping(value = {"/", ""})
    public Collection<OrderDto> readAllOrders() {
        return orderManager.readAll()
                .stream()
                .map(orderMapper::order2orderDto)
                .collect(Collectors.toList());
    }

    @ApiOperation("Record")
    @PostMapping(value = {"", "/"})
    public OrderDto create(@Valid @RequestBody OrderDto recordRequestDto) {
        Order order = orderMapper.orderDto2Order(recordRequestDto);
        try {
            Order recordedOrder = orderManager.record(order);
            return orderMapper.order2orderDto(recordedOrder);
        } catch (OrderAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Update")
    @PutMapping(value = {"", "/"})
    public OrderDto update(@Valid @RequestBody OrderDto updateRequestDto) {
        Order order = orderMapper.orderDto2Order(updateRequestDto);
        Order updatedOrder = orderManager.modify(order);
        return orderMapper.order2orderDto(updatedOrder);
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {"", "/"})
    public void delete(@RequestParam int orderNumber) {
        try {
            orderManager.delete(orderManager.readByOrderNumber(orderNumber));
        } catch (OrderNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {"/{orderNumber}"})
    public void deleteBasedOnPath(@PathVariable int orderNumber) {
        this.delete(orderNumber);
    }
}