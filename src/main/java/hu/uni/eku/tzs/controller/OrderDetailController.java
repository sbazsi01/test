package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.OrderDetailDto;
import hu.uni.eku.tzs.controller.dto.OrderDetailMapper;
import hu.uni.eku.tzs.model.OrderDetail;
import hu.uni.eku.tzs.service.OrderDetailManager;
import hu.uni.eku.tzs.service.exceptions.OrderDetailAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.OrderDetailNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
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

@Api(tags = "OrderDetails")
@RequestMapping("/orderDetails")
@RestController
@RequiredArgsConstructor
public class OrderDetailController {

    private final OrderDetailManager orderDetailManager;

    private final OrderDetailMapper orderDetailMapper;

    @ApiOperation("Read All")
    @GetMapping(value = {"/", ""})
    public Collection<OrderDetailDto> readAllOrderDetails() {
        return orderDetailManager.readAll()
            .stream()
            .map(orderDetailMapper::orderDetail2orderDetailDto)
            .collect(Collectors.toList());

    }

    @ApiOperation("Record")
    @PostMapping(value = {"", "/"})
    public OrderDetailDto create(@Valid @RequestBody OrderDetailDto recordRequestDto) {
        OrderDetail orderDetail = orderDetailMapper.orderDetailDto2orderDetail(recordRequestDto);
        try {

            OrderDetail recorded = orderDetailManager.record(orderDetail, orderDetail.getOrderNumber().getOrderNumber(),
                orderDetail.getProductCode().getProductCode());
            return orderDetailMapper.orderDetail2orderDetailDto(recorded);
        } catch (OrderDetailAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Update")
    @PutMapping(value = {"", "/"})
    public OrderDetailDto update(@Valid @RequestBody OrderDetailDto updateRequestDto) {
        OrderDetail orderDetail = orderDetailMapper.orderDetailDto2orderDetail(updateRequestDto);
        OrderDetail updatedOrderDetail = orderDetailManager.modify(orderDetail);
        return orderDetailMapper.orderDetail2orderDetailDto(updatedOrderDetail);
    }

    @ApiOperation("Delete")
    @DeleteMapping(value = {"", "/"})
    public void delete(@RequestParam Integer orderNumber, @RequestParam String productCode) {
        try {
            orderDetailManager.delete(
                orderDetailManager.readByOrderDetailId(orderNumber, productCode));
        } catch (OrderDetailNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }
}
