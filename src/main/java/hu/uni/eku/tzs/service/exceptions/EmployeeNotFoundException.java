package hu.uni.eku.tzs.service.exceptions;

public class EmployeeNotFoundException extends Exception {

    public EmployeeNotFoundException() {

    }

    public EmployeeNotFoundException(String message) {
        super(message);
    }
}
