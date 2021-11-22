package hu.uni.eku.tzs.controller;

import hu.uni.eku.tzs.controller.dto.PaymentDto;
import hu.uni.eku.tzs.controller.dto.PaymentMapper;
import hu.uni.eku.tzs.model.Payment;
import hu.uni.eku.tzs.service.PaymentManager;
import hu.uni.eku.tzs.service.exceptions.PaymentAlreadyExistsException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.Collection;
import java.util.stream.Collectors;

@Api(tags = "Payment")
@RequestMapping("/payment")
@RestController
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentManager paymentManager;

    private final PaymentMapper paymentMapper;

    @ApiOperation("Read All")
    @GetMapping(value = {"/", ""})
    public Collection<PaymentDto> readAllPayments() {
        return paymentManager.readAll()
                .stream()
                .map(paymentMapper::payment2paymentDto)
                .collect(Collectors.toList());
    }

    @ApiOperation("Record")
    @PostMapping(value = {"", "/"})
    public PaymentDto create(@Valid @RequestBody PaymentDto recordRequestDto) {
        Payment payment = paymentMapper.paymentDto2payment(recordRequestDto);
        try {
            Payment recordedPayment = paymentManager.record(payment);
            return paymentMapper.payment2paymentDto(recordedPayment);
        } catch (PaymentAlreadyExistsException e) {
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage());
        }
    }

    @ApiOperation("Update")
    @PutMapping(value = {"", "/"})
    public PaymentDto update(@Valid @RequestBody PaymentDto updateRequestDto) {
        Payment payment = paymentMapper.paymentDto2payment(updateRequestDto);
        Payment updatedPayment = paymentManager.modify(payment);
        return paymentMapper.payment2paymentDto(updatedPayment);
    }

    //nem tudom kell-e még más: delete? read all by....?
}
