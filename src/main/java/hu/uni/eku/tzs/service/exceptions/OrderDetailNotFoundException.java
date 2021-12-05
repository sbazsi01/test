package hu.uni.eku.tzs.service.exceptions;

public class OrderDetailNotFoundException extends Exception {

    public OrderDetailNotFoundException() {
    }

    public OrderDetailNotFoundException(String message) {
        super(message);
    }

    public OrderDetailNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public OrderDetailNotFoundException(Throwable cause) {
        super(cause);
    }

    public OrderDetailNotFoundException(String message, Throwable cause, boolean enableSuppression,
                                        boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}