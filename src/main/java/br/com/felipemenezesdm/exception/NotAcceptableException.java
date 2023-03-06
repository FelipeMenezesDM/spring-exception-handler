package br.com.felipemenezesdm.exception;

import org.springframework.http.HttpStatus;

public class NotAcceptableException extends RuntimeException {
    public NotAcceptableException() {
        super();
    }

    public NotAcceptableException(String message) {
        super(message);
    }

    public NotAcceptableException(String message, Throwable cause) {
        super(message, cause);
    }

    public NotAcceptableException(Throwable cause) {
        super(cause);
    }

    public HttpStatus getStatusCode() {
        return HttpStatus.NOT_ACCEPTABLE;
    }
}
