package hu.uni.eku.tzs.service.exceptions;

public class PaymentNotFoundException extends Exception {

    public PaymentNotFoundException() {

    }

    public PaymentNotFoundException(String message) {

        super(message);
    }
}
