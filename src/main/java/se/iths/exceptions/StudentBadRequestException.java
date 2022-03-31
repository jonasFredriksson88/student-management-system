package se.iths.exceptions;

public class StudentBadRequestException extends RuntimeException {

    public StudentBadRequestException(String message) {
        super(message);
    }

}
