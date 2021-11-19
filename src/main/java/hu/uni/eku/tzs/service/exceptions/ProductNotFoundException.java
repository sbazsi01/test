package hu.uni.eku.tzs.service.exceptions;

public class ProductNotFoundException extends Exception {

    public ProductNotFoundException() {

    }

    public ProductNotFoundException(String message) {
        super(message);
    }
}
