package se.iths.messages;

public class ErrorMessage {

    private String errorMessage;
    private final String exception;
    private int errorCode;

    public ErrorMessage(String errorMessage, String exception, int errorCode) {
        this.errorMessage = errorMessage;
        this.exception = exception;
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getException() {
        return exception;
    }
}
