package hu.uni.eku.tzs.service.exceptions;

public class CustomerNotFoundException extends Exception {

    public CustomerNotFoundException() {

    }

    public CustomerNotFoundException(String message) {
        super(message);
    }
}
