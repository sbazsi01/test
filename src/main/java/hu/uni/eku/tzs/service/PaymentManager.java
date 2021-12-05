package hu.uni.eku.tzs.service;

import hu.uni.eku.tzs.model.Payment;
import hu.uni.eku.tzs.service.exceptions.PaymentAlreadyExistsException;
import hu.uni.eku.tzs.service.exceptions.PaymentNotFoundException;

import java.util.Collection;

public interface PaymentManager {
    Payment record(Payment office) throws PaymentAlreadyExistsException;

    Payment readByCheckNumber(String checkNumber) throws PaymentNotFoundException;

    Collection<Payment> readAll();

    Payment modify(Payment payment);

    void delete(Payment payment) throws PaymentNotFoundException;
}
